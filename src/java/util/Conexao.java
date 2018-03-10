package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {

    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost/crud_fichas";
    private static final String USUARIO = "root";
    private static final String SENHA = "55555";

    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conexao;
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexao = null;
        }
    }
    
     public static ArrayList select(String sql, int qtdColumns) {
        Connection con;
        PreparedStatement stm;
        ResultSet rs;
        ArrayList<ArrayList<String>> arrayPrincipal = new ArrayList<>();
        ArrayList<String> arraySecundario;
        try {
            con = (Connection) DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            stm = (PreparedStatement) con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                rs.getString(qtdColumns);
                arraySecundario = new ArrayList<>();
                for (int i = 1; i <= qtdColumns; i++) {
                    arraySecundario.add(rs.getString(i));
                }
                arrayPrincipal.add(arraySecundario);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (Exception e) {
        }
        return arrayPrincipal;
    }
    
}
