package tatahcorte.jogodaspalavras.entidade;

import java.util.Date;

public class Pontuacao {
    private Integer id;
    private Date data;
    private long pontuacao;
    private String nome;

    public Pontuacao() {
    }

    public Pontuacao(Date data, long pontuacao) {
        this.data = data;
        this.pontuacao = pontuacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }
}