package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Animal;
import model.Ficha;
import util.Conexao;

public class AnimalDao {

    public ArrayList<Animal> listar() {
//        ArrayList<Animal> animais = new ArrayList<>();
//        try {
//            Connection conexao = Conexao.getConexao();
//            PreparedStatement ps = conexao.prepareStatement("select * from animal");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Animal animal = new Animal();
//                animal.setId(rs.getInt("id"));
//                animal.setNome(rs.getString("nome"));
//                animais.add(animal);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            //FabricaConexao.fecharConexao();
//        }
//        return animais;
        
        ArrayList<Animal> animais = Conexao.select("select * from animal", 2);
        return animais;
    }

    public void salvar(Animal animal) {
        String sql = "update animal\n"
                + "set\n"
                + "nome = ?\n"
                + "where id = ?";
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, animal.getNome());
            ps.setInt(2, animal.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }
    }

    public void inserir(Animal animal) {
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("insert into animal values (null,?)");
            ps.setString(1, animal.getNome());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }
    }

    public void excluir(Animal animal) {
        String sql = "delete from animal\n"
                + "where id = ?";
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, animal.getNome());
            ps.setInt(1, animal.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }
    }

    public static ArrayList<Integer> getAnimaisPorFicha(Ficha ficha) {
        ArrayList<Integer> animais = new ArrayList<>();

        String sql = "select distinct a.*\n"
                + "from animal a\n"
                + "join animal_ficha af on af.id_animal = a.id\n"
                + "where af.id_ficha = ?;";

        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, ficha.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer animal = 0;
                animal = rs.getInt("id");
                animais.add(animal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }
        return animais;
    }

    public static void setAnimaisPorFicha(Ficha ficha) {
        // delete
        String sqlFicha = "delete from animal_ficha where id_ficha = ?";
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sqlFicha);
            ps.setInt(1, ficha.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }

        //insert
        ArrayList<Integer> animais = ficha.getAnimais();
        for (Integer animal : animais) {
            sqlFicha = "insert into animal_ficha values (?,?)";
            try {
                Connection conexao = Conexao.getConexao();
                PreparedStatement ps = conexao.prepareStatement(sqlFicha);
                ps.setInt(1, animal);
                ps.setInt(2, ficha.getId());
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                //FabricaConexao.fecharConexao();
            }
        }
    }

}
