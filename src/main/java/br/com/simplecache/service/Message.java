
package br.com.simplecache.service;

public class Message {
    
    private Long idUsuario;
    private String titulo;
    private String texto;

    public Message(Long idUsuario, String titulo, String texto) {
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.texto = texto;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }
    
    
    
}
