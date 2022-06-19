package br.com.how.gerenciamentodecolecoes.colecao;

public class Colecao implements java.io.Serializable {
    private int id;
    private String nome;
    private String descricao;
    private String data_inicio;
    private boolean completa;
    private int id_categoria;

    public Colecao() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getData_inicio() { return data_inicio; }

    public void setData_inicio(String data_inicio) { this.data_inicio = data_inicio; }

    public boolean getCompleta() { return completa; }

    public void setCompleta(boolean completa) { this.completa = completa; }

    public int getId_categoria() { return id_categoria; }

    public void setId_categoria(int id_categoria) { this.id_categoria = id_categoria; }
}
