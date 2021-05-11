package br.com.tchars.rmembr.models;

public class Memoria {
    private Integer id;
    private String urlImagem;
    private String titulo;

    public Memoria() {
    }

    public Memoria(Integer id, String urlImagem, String titulo) {
        this.id = id;
        this.urlImagem = urlImagem;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
