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

//    public ArrayList<Ficha> pesquisar(int id) {
//        ArrayList<Ficha> fichas = new ArrayList<>();
//        try {
//            Connection conexao = FabricaConexao.getConexao();
//            PreparedStatement ps = conexao.prepareStatement("select * from ficha where id = ?");
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Ficha ficha = new Ficha();
//                ficha.setId(rs.getInt("id"));
//                ficha.setDataRegistro(rs.getString("dt_cadastro"));
//                ficha.setStatus(rs.getInt("status") == 1);
//                ficha.setObservacao(rs.getString("observacao"));
//                ficha.setAnimais(AnimalDao.getAnimaisPorFicha(ficha));
//                fichas.add(ficha);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            //FabricaConexao.fecharConexao();
//        }
//        return fichas;
//    }

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
            //FabricaConexao.fecharConexao();
        }
        AnimalDao.setAnimaisPorFicha(ficha);
    }

//    public ArrayList<Integer> getIdAnimais(ArrayList<String> nomes) {
//        ArrayList<Integer> retorno = new ArrayList<>();
//        try {
//            Connection conexao3 = FabricaConexao.getConexao();
//            PreparedStatement ps3 = conexao3.prepareStatement("select id from animal where nome like ?");
//            for (String x : nomes) {
//                ps3.setString(1, x);
//                ResultSet rs3 = ps3.executeQuery();
//                while (rs3.next()) {
//                    retorno.add(rs3.getInt("id"));
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            //FabricaConexao.fecharConexao();
//        }
//        return retorno;
//    }
//    public ArrayList<String> getNomeAnimais(int id) {
//        ArrayList<String> r = new ArrayList<>();
//
//        Connection conexao2 = FabricaConexao.getConexao();
//        try {
//            PreparedStatement ps2 = conexao2.prepareStatement("select nome from animal where id in (select id_animal from animal_ficha where id_ficha = ?);");
//            ps2.setInt(1, id);
//            ResultSet rs2 = ps2.executeQuery();
//            while (rs2.next()) {
//                r.add(rs2.getString("nome"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AnimalDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
////            //FabricaConexao.fecharConexao();
//        }
//        return r;
//    }
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
            //FabricaConexao.fecharConexao();
        }
//        atualizaAnimais(ficha);
        AnimalDao.setAnimaisPorFicha(ficha);

    }

//    private void atualizaAnimais(Ficha ficha) {
//        System.out.println("ATUALIZA ANIMAIS");
//        System.out.println(ficha.getId());
//        String sqlFicha = "delete from animal_ficha where id_ficha = ?";
//        try {
//            Connection conexao = FabricaConexao.getConexao();
//            PreparedStatement ps = conexao.prepareStatement(sqlFicha);
//            ps.setInt(1, ficha.getId());
//            ps.execute();
//        } catch (SQLException ex) {
//            Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            //FabricaConexao.fecharConexao();
//        }
//
//        //insert
//        ArrayList<Integer> idsAnimais = getIdAnimais(ficha.getAnimais());
//        for (Integer i : idsAnimais) {
//            sqlFicha = "insert into animal_ficha values (?,?)";
//            try {
//                Connection conexao = FabricaConexao.getConexao();
//                PreparedStatement ps = conexao.prepareStatement(sqlFicha);
//                ps.setInt(1, i);
//                ps.setInt(2, ficha.getId());
//                ps.execute();
//            } catch (SQLException ex) {
//                Logger.getLogger(FichaDao.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                //FabricaConexao.fecharConexao();
//            }
//        }
//    }
}
