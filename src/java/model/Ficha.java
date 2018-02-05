package model;

import java.util.ArrayList;

public class Ficha {

    Integer id;
    String dataRegistro;
    boolean status;
    String observacao;
//    ArrayList<Animal> animais; // não foi possível utilizar o objeto pois no angular o select não reconhece o objeto no ng-model para selecionar os valores já sejecionados quando edita uma ficha.
    ArrayList<Integer> animais;

    public Ficha() {
    }

    public ArrayList<Integer> getAnimais() {
        return animais;
    }

    public void setAnimais(ArrayList<Integer> animais) {
        this.animais = animais;
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

}
