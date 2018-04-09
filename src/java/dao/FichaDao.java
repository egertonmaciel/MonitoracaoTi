package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import model.Ficha;
import util.Conexao;
import util.Registro;

public class FichaDao {

    public ArrayList<Ficha> listar(int id, String dataInicial, String dataFinal) {
        ArrayList<Ficha> fichas = new ArrayList<>();
        ArrayList<Object> parametros = new ArrayList<>();
        String sql;

        if (id > 0) {
            sql = "select * from ficha where id = ?";
            parametros.add(id);
        } else if (dataInicial.equals("0") && dataFinal.equals("0")) {//não selecionou nenhuma data
            sql = "select * from ficha";
        } else if (dataInicial.equals("0")) {// selecionou apenas a data final
            sql = "select * from ficha where dt_cadastro < ?";
            parametros.add(dataFinal);
        } else if (dataFinal.equals("0")) {//selecionou apenas a data inicial
            sql = "select * from ficha where dt_cadastro > ?";
            parametros.add(dataInicial);
        } else {//selecionou todas as datas
            sql = "select * from ficha where dt_cadastro between ? and ?";
            parametros.add(dataInicial);
            parametros.add(dataFinal);
        }

        ArrayList<Registro> retorno = Conexao.select(sql, parametros);
        for (Registro r : retorno) {
            Ficha ficha = new Ficha();
            ficha.setId(Integer.valueOf(r.getValores().get(0)));
            ficha.setDataRegistro(r.getValores().get(1));
            ficha.setStatus(r.getValores().get(2).equals("1"));
            ficha.setObservacao(r.getValores().get(3));
            ficha.setAnimais(AnimalDao.getAnimaisPorFicha(ficha));
            fichas.add(ficha);
        }
        return fichas;
    }

    public void salvar(Ficha ficha) {
        String sql = "update ficha\n"
                + "set\n"
                + "dt_cadastro = ?,\n"
                + "status = ?,\n"
                + "observacao = ?\n"
                + "where id = ?";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(ficha.getDataRegistro());
        parametros.add(ficha.getStatus());
        parametros.add(ficha.getObservacao());
        parametros.add(ficha.getId());
        Conexao.update(sql, parametros);
        AnimalDao.setAnimaisPorFicha(ficha);
    }

    public void inserir(Ficha ficha) {
        String sql = "insert into ficha values (null,now(),?,?)";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(ficha.getStatus() == true ? 1 : 0);
        parametros.add(ficha.getObservacao());
        ArrayList<Integer> id = Conexao.update(sql, parametros);
        for (Integer i : id) {
            ficha.setId(i);
        }
        AnimalDao.setAnimaisPorFicha(ficha);
    }

    public void excluir(int id) {
        String sql = "delete from ficha where id = ?";
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(id);
        Conexao.update(sql, parametros);

        sql = "delete from animal_ficha where id_ficha = ?";
        Conexao.update(sql, parametros);
    }
}
