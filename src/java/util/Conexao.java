package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Egerton Maciel
 */

public class Conexao {

    private static final String URL_CONEXAO = "jdbc:mysql://localhost/crud_fichas";
    private static final String USUARIO = "root";
    private static final String SENHA = "55555";

    public static ArrayList<Integer> update(String sql) {
        return update(sql, null, null);
    }
    
    public static ArrayList<Integer> update(String sql, ArrayList<Object> parametros) {
        return update(sql, parametros, null);
    }
    
    public static ArrayList<Integer> update(String sql, Object[] parametros2) {
        return update(sql, null, parametros2);
    }


    public static ArrayList<Integer> update(String sql, ArrayList<Object> parametros, Object[] parametros2) {

//    public static ArrayList<Integer> update(String sql, ArrayList<Object> parametros) {

        ArrayList<Integer> resultado = new ArrayList<>();
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            ps = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             if (parametros != null) {
                ps = setParametros(ps, parametros, null);
            } else if (parametros2 != null) {
                ps = setParametros(ps, null, parametros2);
            }
            ps.execute();
            rs = ps.getGeneratedKeys();
            while (rs.next()) {
                resultado.add(rs.getInt(1));
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Conexao.update:\n" + e);
        }
        return resultado;
    }

    public static ArrayList<Registro> select(String sql) {
        return select(sql, null, null);
    }

    public static ArrayList<Registro> select(String sql, ArrayList<Object> parametros) {
        return select(sql, parametros, null);
    }

    public static ArrayList<Registro> select(String sql, Object[] parametros) {
        return select(sql, null, parametros);
    }

    private static ArrayList<Registro> select(String sql, ArrayList<Object> parametros, Object[] parametros2) {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Registro> array = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            ps = (PreparedStatement) con.prepareStatement(sql);
            if (parametros != null) {
                ps = setParametros(ps, parametros, null);
            } else if (parametros2 != null) {
                ps = setParametros(ps, null, parametros2);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
//<<<<<<< HEAD
                Registro registro = new Registro();
//=======
//                arraySecundario = new ArrayList<>();
//>>>>>>> origin/master
                for (int y = 1; y <= rs.getMetaData().getColumnCount(); y++) {
                    registro.addItem(rs.getString(y), rs.getMetaData().getColumnLabel(y));
                }
                array.add(registro);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Conexao.select:\n" + e);
        }
        return array;
    }

    private static PreparedStatement setParametros(PreparedStatement ps, ArrayList<Object> parametros, Object[] parametros2) throws SQLException {
        if (parametros == null && parametros2 == null) {
            return ps;
        }
        
        ArrayList<Object> par = new ArrayList<>();
        if (parametros != null) {
            par = parametros;
        } else if (parametros2 != null) {

            for (Object o : parametros2) {
                par.add(o);
            }
        }

        int i = 1;
        for (Object p : par) {
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
}
