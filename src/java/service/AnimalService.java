package service;

import com.google.gson.Gson;
import dao.AnimalDao;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import model.Animal;

@Path("animal")
public class AnimalService {

    @Context
    private UriInfo context;

    public AnimalService() {
    }
    
    @GET
    @Produces("application/json")
    @Path("listar")
    public String getJsonAnimais() {
        Gson g = new Gson();
        AnimalDao animais = new AnimalDao();
        return g.toJson(animais.listar());
    }

    @POST
    @Consumes("application/json")
    @Path("salvar/")
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

    @POST
    @Consumes("application/json")
    @Path("excluir/")
    public void postJsonAnimalDeletar(String animal) {
        Gson g = new Gson();
        AnimalDao animalDao = new AnimalDao();
        Animal a = g.fromJson(animal, Animal.class);
        animalDao.excluir(a);
    }
    
}
