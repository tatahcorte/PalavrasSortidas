package tatahcorte.jogodaspalavras.servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tatahcorte.jogodaspalavras.contract.PontuacaoContract;
import tatahcorte.jogodaspalavras.dao.PontuacaoDao;
import tatahcorte.jogodaspalavras.entidade.Pontuacao;

public class PontuacaoServico {

    private final PontuacaoDao pontuacaoDao = new PontuacaoDao();

    public List<Object> getListaPontuacaoAgrupadaPorData(){
        List<Pontuacao> pontuacaoList = pontuacaoDao.query(null
                , null
                , PontuacaoContract.DATA + " DESC, " + PontuacaoContract.PONTUACAO + " DESC ");
        Map<Long, List<Long>> agrupado = agruparPorData(pontuacaoList);
        return desagrupar(agrupado);
    }

    public String getRecorde(){
        Pontuacao recorde = pontuacaoDao.findOne(
            PontuacaoContract.PONTUACAO + " = (SELECT MAX(" + PontuacaoContract.PONTUACAO+ ") FROM " + PontuacaoContract.TABLE_NAME + ")"
        );
        return recorde != null ? recorde.getNome() +  " : " + recorde.getPontuacao() : "0";
    }

    public void limparTabela(){
        pontuacaoDao.clear();
    }

    private Map<Long, List<Long>> agruparPorData(List<Pontuacao> pontuacaoList){
        Map<Long, List<Long>> resultado = new HashMap<>();
        for (Pontuacao pontuacao : pontuacaoList) {
            long tempo = pontuacao.getData().getTime();
            if(!resultado.containsKey(tempo)){
                resultado.put(tempo, new ArrayList<Long>());
            }
            resultado.get(tempo).add(pontuacao.getPontuacao());
        }
        return resultado;
    }

    private List<Object> desagrupar(Map<Long, List<Long>> agrupado){
        List<Object> resultado = new ArrayList<>();
        for (Long chave : agrupado.keySet()) {
            resultado.add(new Date(chave));
            resultado.addAll(agrupado.get(chave));
        }
        return resultado;
    }
}
