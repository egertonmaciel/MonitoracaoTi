package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Animal;
import util.FabricaConexao;

public class AnimalDao {

    public ArrayList<Animal> listarTodos() {
        ArrayList<Animal> animais = new ArrayList<>();
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from animal");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId(rs.getInt("id"));
                animal.setNome(rs.getString("nome"));
                animais.add(animal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FabricaConexao.fecharConexao();
        }
        return animais;
    }

    public void inserir(Animal animal) {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("insert into animal values (null,?)");
            ps.setString(1, animal.getNome());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FabricaConexao.fecharConexao();
        }
    }

    public void salvar(Animal animal) {
        String sql = "update animal\n"
                + "set\n"
                + "nome = ?\n"
                + "where id = ?";
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, animal.getNome());
            ps.setInt(2, animal.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FabricaConexao.fecharConexao();
        }
    }

    public void deletar(Animal animal) {
        String sql = "delete from animal\n"
                + "where id = ?";
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, animal.getNome());
            ps.setInt(1, animal.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FabricaConexao.fecharConexao();
        }
    }
}
