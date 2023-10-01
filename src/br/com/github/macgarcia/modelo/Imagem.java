package br.com.github.macgarcia.modelo;

import br.com.github.macgarcia.repository.EntityBase;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author macgarcia
 */
@Entity
@Table(name = "IMAGEM")
@SequenceGenerator(name = "imagem_seq", sequenceName = "imagem_seq", initialValue = 1, allocationSize = 1)
public class Imagem implements Serializable, EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagem_seq")
    private Long id;

    @Column(name = "NOME_FOTO")
    private String nomeFoto;

    @Column(name = "HISTOGRAMA", unique = true)
    private Long histograma;

    @Column(name = "IMG_BINARIA")
    @Lob
    private byte[] img;

    public Imagem() {

    }

    public Imagem(String nomeFoto, Long histograma, byte[] img) {
        this.nomeFoto = nomeFoto;
        this.histograma = histograma;
        this.img = img;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFoto() {
        return nomeFoto;
    }

    public void setNomeFoto(String nomeFoto) {
        this.nomeFoto = nomeFoto;
    }

    public Long getHistograma() {
        return histograma;
    }

    public void setHistograma(Long histograma) {
        this.histograma = histograma;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
    
}
