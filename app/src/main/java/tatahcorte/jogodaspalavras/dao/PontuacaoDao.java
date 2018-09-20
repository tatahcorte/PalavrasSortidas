package tatahcorte.jogodaspalavras.dao;

import tatahcorte.jogodaspalavras.contract.PontuacaoContract;
import tatahcorte.jogodaspalavras.database.DatabaseHelper;
import tatahcorte.jogodaspalavras.entidade.Pontuacao;
import tatahcorte.jogodaspalavras.servico.PontuacaoServico;
import tatahcorte.jogodaspalavras.servico.UsuarioServico;

public class PontuacaoDao extends AbstractDao<Pontuacao>{
    public PontuacaoDao() {
        super(new PontuacaoContract(), DatabaseHelper.getInstance());
    }

    @Override
    public Pontuacao insert(Pontuacao entity) {
        entity.setNome(UsuarioServico.getInstance().getUser());
        return super.insert(entity);
    }
}
