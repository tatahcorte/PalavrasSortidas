package tatahcorte.jogodaspalavras.servico;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import tatahcorte.jogodaspalavras.dao.PontuacaoDao;
import tatahcorte.jogodaspalavras.dao.SinonimoDao;
import tatahcorte.jogodaspalavras.entidade.Partida;
import tatahcorte.jogodaspalavras.entidade.Pontuacao;

public class PalavrasSortidasServico {

    private Random random = new Random();
    private SinonimoDao sinonimoDao = new SinonimoDao();
    private PontuacaoDao pontuacaoDao = new PontuacaoDao();

    public Partida criarNovaPartida(Partida partidaExistente, long pontuacao){
        if(partidaExistente == null){
            partidaExistente = new Partida();
        }

        if(partidaExistente.getSinonimo() != null){
            partidaExistente.getSinonimosAnteriores().add(partidaExistente.getSinonimo().getId());
        }

        partidaExistente.setPontuacaoAcumulada(partidaExistente.getPontuacaoAcumulada() + pontuacao);
        partidaExistente.setSinonimo(sinonimoDao.findOneRandom(partidaExistente.getSinonimosAnteriores()));

        List<String> listaDeSinonimos = partidaExistente.getSinonimo().toList();
        int index = getNewRand(0, listaDeSinonimos.size()-1);
        partidaExistente.setSinonimoEscondido(listaDeSinonimos.get(index));

        listaDeSinonimos.remove(index);
        partidaExistente.setSinonimosDica(TextUtils.join(", ", listaDeSinonimos));

        partidaExistente.setPosicoesReveladas(new ArrayList<Integer>());

        partidaExistente.setCoracoes(5);
        return partidaExistente;
    }

    public int revelarPosicao(Partida partida){
        List<Integer> posicoes = listPosicoes(partida.getSinonimoEscondido());
        posicoes.removeAll(partida.getPosicoesReveladas());
        int posicao = posicoes.get(getNewRand(0, posicoes.size()-1));
        partida.getPosicoesReveladas().add(posicao);
        return posicao;
    }

    public long calculaPontos(Partida partida){
        long pontos = 10000;
        if(partida.getCoracoes() <= 4){
            pontos =  pontos / 2;
        }
        if(partida.getCoracoes() <= 3){
            pontos =  pontos / 2;
        }
        if(partida.getCoracoes() <= 2){
            pontos =  pontos / 2;
        }
        if(partida.getCoracoes() <= 1){
            pontos =  pontos / 2;
        }
        if(partida.getCoracoes() == 0){
            pontos = 0;
        }
        return pontos;
    }

    public String montarPalavraOculta(Partida partida){
        StringBuilder resultado = new StringBuilder();
        List<Integer> posicoesReveladas = partida.getPosicoesReveladas();
        int count = partida.getSinonimoEscondido().length();
        for(int x=0; x < count; x++){
            if(posicoesReveladas.contains(x)){
                resultado.append(partida.getSinonimoEscondido().charAt(x));
            } else {
                resultado.append("_");
            }
        }
        return resultado.toString();
    }

    private int getNewRand(int min, int max){
        return random.nextInt(max-min+1)+min;
    }

    private List<Integer> listPosicoes(String str){
        List<Integer> posicoes = new ArrayList<>();
        for(int x=0; x < str.length(); x++){
            posicoes.add(x);
        }
        return posicoes;
    }

    public void saveScore(long pontuacao) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        pontuacaoDao.insert(new Pontuacao(time.getTime(), pontuacao));
    }
}
