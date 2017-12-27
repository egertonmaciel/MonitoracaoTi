package resource;

import com.google.gson.Gson;
import dao.AnimalDao;
import dao.FichaDao;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import model.Animal;
import model.Ficha;

@Path("CrudFichas")
public class CrudFichasResource {

    @Context
    private UriInfo context;

    public CrudFichasResource() {
    }

    @GET
    @Produces("application/json")
    @Path("fichasPorData/{dataInicial}/{dataFinal}")
    public String getJsonFichas(@PathParam("dataInicial") String dataInicial, @PathParam("dataFinal") String dataFinal) {
        Gson g = new Gson();
        FichaDao fichas = new FichaDao();
        return g.toJson(fichas.pesquisar(dataInicial, dataFinal));
    }

    @GET
    @Produces("application/json")
    @Path("fichaPorId/{id}")
    public String getJsonFicha(@PathParam("id") int id) {
        Gson g = new Gson();
        FichaDao fichas = new FichaDao();
        return g.toJson(fichas.pesquisar(id));
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("excluirFicha/{id}")
    public void putJsonFicha(@PathParam("id") int id) {
        FichaDao ficha = new FichaDao();
        ficha.excluir(id);
    }

    @POST
    @Consumes("application/json")
    @Path("salvarFicha/")
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

    @POST
    @Consumes("application/json")
    @Path("salvarAnimal/")
    public void postJsonAnimalSalvar(String animal) {
        Gson g = new Gson();
        AnimalDao animalDao = new AnimalDao();
        Animal a = g.fromJson(animal, Animal.class);

        if (a.getId() == 0) {
            animalDao.inserir(a);
        } else {
            animalDao.salvar(a);
        }
    }

    @GET
    @Produces("application/json")
    @Path("animais")
    public String getJsonAnimais() {
        Gson g = new Gson();
        AnimalDao animais = new AnimalDao();
        return g.toJson(animais.listarTodos());
    }

    @PUT
    @Produces("application/json")
    public void putJson(String content) {
    }
}
