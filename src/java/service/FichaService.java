package service;

import com.google.gson.Gson;
import dao.FichaDao;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import model.Ficha;

@Path("ficha")
public class FichaService {

    @Context
    private UriInfo context;

    public FichaService() {
    }

    @GET
    @Produces("application/json")
    @Path("listar/{id}/{dataInicial}/{dataFinal}")
    public String getJsonFichas(@PathParam("id") int id, @PathParam("dataInicial") String dataInicial, @PathParam("dataFinal") String dataFinal) {
        Gson g = new Gson();
        FichaDao fichas = new FichaDao();
        return g.toJson(fichas.listar(id, dataInicial, dataFinal));
    }

    @POST
    @Consumes("application/json")
    @Path("salvar/")
    public void postJsonFichaSalvar(String ficha) {
        Gson g = new Gson();
        FichaDao fichaDao = new FichaDao();
        Ficha f = g.fromJson(ficha, Ficha.class);

        if (f.getId() == 0) {
            fichaDao.inserir(f);
        } else {
            fichaDao.salvar(f);
        }
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("excluir/{id}")
    public void putJsonFicha(@PathParam("id") int id) {
        FichaDao ficha = new FichaDao();
        ficha.excluir(id);
    }
    
}
