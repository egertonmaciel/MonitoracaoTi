package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    private static PreparedStatement setParametros(PreparedStatement ps, ArrayList<Object> parametros) throws SQLException {
        if (parametros == null) {
            return ps;
        }
        int i = 1;
        for (Object p : parametros) {
            if (p instanceof String) {
                ps.setString(i++, (String) p);
            } else if (p instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) p).getTime()));
            } else if (p instanceof Integer) {
                ps.setInt(i++, (Integer) p);
            } else if (p instanceof Long) {
                ps.setLong(i++, (Long) p);
            } else if (p instanceof Double) {
                ps.setDouble(i++, (Double) p);
            } else if (p instanceof Float) {
                ps.setFloat(i++, (Float) p);
            } else if (p instanceof Boolean) {
                ps.setBoolean(i++, (Boolean) p);
            }
        }
        return ps;
    }

    public static boolean update(String sql) {
        return update(sql, null);
    }

    public static boolean update(String sql, ArrayList<Object> parametros) {
        boolean sucesso = false;
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps = setParametros(ps, parametros);
            sucesso = ps.execute();
            ps.close();
            con.close();
        } catch (Exception e) {
        }
        return sucesso;
    }

    public static ArrayList<ArrayList<String>> select(String sql, int qtdColumns) {
        return select(sql, qtdColumns, null);
    }

    public static ArrayList<ArrayList<String>> select(String sql, int qtdColumns, ArrayList<Object> parametros) {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<ArrayList<String>> arrayPrincipal = new ArrayList<>();
        ArrayList<String> arraySecundario;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps = setParametros(ps, parametros);
            rs = ps.executeQuery();
            while (rs.next()) {
                rs.getString(qtdColumns);
                arraySecundario = new ArrayList<>();
                for (int y = 1; y <= qtdColumns; y++) {
                    arraySecundario.add(rs.getString(y));
                }
                arrayPrincipal.add(arraySecundario);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
        }
        return arrayPrincipal;
    }
}
