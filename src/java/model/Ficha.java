package model;

import java.util.ArrayList;

public class Ficha {

    Integer id;
    String dataRegistro;
    boolean status;
    String observacao;
//    ArrayList<Animal> animais;
    ArrayList<String> animais;

    public Ficha() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ArrayList<String> getAnimais() {
        return animais;
    }

    public void setAnimais(ArrayList<String> animais) {
        this.animais = animais;
    }
}
