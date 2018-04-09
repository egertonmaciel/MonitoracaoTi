/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;

/**
 *
 * @author egertonmaciel
 */
public class Registro {

    private ArrayList<String> valores;
    private ArrayList<String> rotulos;

    public Registro() {
        valores = new ArrayList<>();
        rotulos = new ArrayList<>();
    }

    
    
    public ArrayList<String> getValores() {
        return valores;
    }
    
    public ArrayList<String> getRotulos() {
        return rotulos;
    }

    public void addItem(String valor, String rotulo) {
        this.valores.add(valor);
        this.rotulos.add(rotulo);
    }
}
