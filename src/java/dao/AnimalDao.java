package dao;

import java.util.ArrayList;
import model.Animal;
import model.Ficha;
import util.Conexao;

public class AnimalDao {

    public ArrayList<Animal> listar() {
        ArrayList<Animal> animais = new ArrayList<>();
        ArrayList<ArrayList<String>> retorno = Conexao.select("select * from animal", 2);
        for (ArrayList<String> a : retorno) {
            Animal animal = new Animal();
            animal.setId(Integer.valueOf(a.get(0)));
            animal.setNome(a.get(1));
            animais.add(animal);
        }
        return animais;
    }

    public void salvar(Animal animal) {
        String sql = "update animal\n"
                + "set\n"
                + "nome = ?\n"
                + "where id = ?";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(animal.getNome());
        parametros.add(animal.getId());
        Conexao.update(sql, parametros);
    }

    public void inserir(Animal animal) {
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(animal.getNome());
        Conexao.update("insert into animal values (null,?)", parametros);
    }

    public void excluir(Animal animal) {
        String sql = "delete from animal\n"
                + "where id = ?";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(animal.getId());
        Conexao.update(sql, parametros);
    }

    public static ArrayList<Integer> getAnimaisPorFicha(Ficha ficha) {
        ArrayList<Integer> animais = new ArrayList<>();
        String sql = "select distinct a.id\n"
                + "from animal a\n"
                + "join animal_ficha af on af.id_animal = a.id\n"
                + "where af.id_ficha = ?;";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(ficha.getId());
        ArrayList<ArrayList<String>> retorno = Conexao.select(sql, 1, parametros);
        for (ArrayList<String> r : retorno) {
            animais.add(Integer.valueOf(r.get(0)));
        }
        return animais;
    }

    public static void setAnimaisPorFicha(Ficha ficha) {
        // delete
        String sql = "delete from animal_ficha where id_ficha = ?";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(ficha.getId());
        Conexao.update(sql, parametros);

        //insert
        ArrayList<Integer> animais = ficha.getAnimais();
        for (Integer animal : animais) {
            sql = "insert into animal_ficha values (?,?)";
            parametros = new ArrayList<>();
            parametros.add(animal);
            parametros.add(ficha.getId());
            Conexao.update(sql, parametros);
        }
    }

}
