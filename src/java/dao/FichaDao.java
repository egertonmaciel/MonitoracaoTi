package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Animal;
import model.Ficha;
import util.FabricaConexao;

public class FichaDao {

    public ArrayList<Ficha> pesquisar(int id, String dataInicial, String dataFinal) {
        ArrayList<Ficha> fichas = new ArrayList<>();
        String sql;

        System.out.println("PESQUISAR: " + id + " | " + dataInicial + " | " + dataFinal);

        //valida filtros utilizados
        if (id > 0) {
            sql = "select * from ficha where id = ?";
        } else if (dataInicial.equals("0") && dataFinal.equals("0")) {//não selecionou nenhuma data
            sql = "select * from ficha";
        } else if (dataInicial.equals("0")) {// selecionou apenas a data final
            sql = "select * from ficha where dt_cadastro < ?";
        } else if (dataFinal.equals("0")) {//selecionou apenas a data inicial
            sql = "select * from ficha where dt_cadastro > ?";
        } else {//selecionou todas as datas
            sql = "select * from ficha where dt_cadastro between ? and ?";
        }
        System.out.println("SQL: " + sql);
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);

            //valida filtros utilizados
            if (id > 0) {
                ps.setInt(1, id);
            } else if (dataInicial.equals("0") && dataFinal.equals("0")) {//não selecionou nenhuma data
                // nao adiciona ps
            } else if (dataInicial.equals("0")) {// selecionou apenas a data final
                ps.setString(1, dataFinal);
            } else if (dataFinal.equals("0")) {//selecionou apenas a data inicial
                ps.setString(1, dataInicial);
            } else {//selecionou todas as datas
                ps.setString(1, dataInicial);
                ps.setString(2, dataFinal);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ficha ficha = new Ficha();
                ficha.setId(rs.getInt("id"));
                ficha.setDataRegistro(rs.getString("dt_cadastro"));
                ficha.setStatus(rs.getInt("status") == 1);
                ficha.setObservacao(rs.getString("observacao"));
//                ficha.setAnimais(getNomeAnimais(rs.getInt("id")));
                ficha.setAnimais(AnimalDao.getAnimaisPorFicha(ficha));
                System.out.println(ficha.getAnimais());
                fichas.add(ficha);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }
        return fichas;
    }

    public void excluir(int id) {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from ficha where id = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }

        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from animal_ficha where id_ficha = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //FabricaConexao.fecharConexao();
        }
    }

    public void inserir(Ficha ficha) {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("insert into ficha values (null,now(),?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ficha.getStatus() == true ? 1 : 0);
            ps.setString(2, ficha.getObservacao());
            ps.execute();
              
             
              
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                ficha.setId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FabricaConexao.fecharConexao();
        }
        AnimalDao.setAnimaisPorFicha(ficha);
    }

    public void salvar(Ficha ficha) {
        String sqlFicha = "update ficha\n"
                + "set\n"
                + "dt_cadastro = ?,\n"
                + "status = ?,\n"
                + "observacao = ?\n"
                + "where id = ?";
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sqlFicha);
            ps.setString(1, ficha.getDataRegistro());
            ps.setBoolean(2, ficha.getStatus());
            ps.setString(3, ficha.getObservacao());
            ps.setInt(4, ficha.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FabricaConexao.fecharConexao();
        }
//        atualizaAnimais(ficha);
        AnimalDao.setAnimaisPorFicha(ficha);
    }
}
