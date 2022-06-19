package br.com.how.gerenciamentodecolecoes.itens_colecao;

public class ItensColecao implements java.io.Serializable {
    private int id;
    private String nome;
    private String descricao;
    private int id_colecao;

    public ItensColecao() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getId_colecao() {
        return id_colecao;
    }

    public void setId_colecao(int id_colecao) {
        this.id_colecao = id_colecao;
    }
}
