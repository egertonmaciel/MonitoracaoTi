package dao;

import java.util.ArrayList;
import model.Animal;
import model.Ficha;
import util.Conexao;
import util.Registro;

public class AnimalDao {

    public ArrayList<Animal> listar() {
        ArrayList<Animal> animais = new ArrayList<>();
        ArrayList<Registro> retorno = Conexao.select("select * from animal");
        for (Registro a : retorno) {
            Animal animal = new Animal();
            animal.setId(Integer.valueOf(a.getValores().get(0)));
            animal.setNome(a.getValores().get(1));
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
        Conexao.update("insert into animal values (null,?)", new Object[]{animal.getNome()});
    }

    public void excluir(Animal animal) {
        String sql = "delete from animal\n"
                + "where id = ?";
        Conexao.update(sql, new Object[]{animal.getId()});
    }

    public static ArrayList<Integer> getAnimaisPorFicha(Ficha ficha) {
        ArrayList<Integer> animais = new ArrayList<>();
        String sql = "select distinct a.id\n"
                + "from animal a\n"
                + "join animal_ficha af on af.id_animal = a.id\n"
                + "where af.id_ficha = ?;";
        ArrayList<Registro> retorno = Conexao.select(sql, new Object[]{ficha.getId()});
        for (Registro r : retorno) {
            animais.add(Integer.valueOf(r.getValores().get(0)));
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
