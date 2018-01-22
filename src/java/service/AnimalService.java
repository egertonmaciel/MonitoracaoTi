package service;

import com.google.gson.Gson;
import dao.AnimalDao;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

    @POST
    @Consumes("application/json")
    @Path("insert/")
    public void insertAnimal(String animal) {
        Gson g = new Gson();
        AnimalDao animalDao = new AnimalDao();
        Animal a = g.fromJson(animal, Animal.class);
        animalDao.inserir(a);
    }

    @POST
    @Consumes("application/json")
    @Path("update/")
    public void updateAnimal(String animal) {
        Gson g = new Gson();
        AnimalDao animalDao = new AnimalDao();
        Animal a = g.fromJson(animal, Animal.class);
        animalDao.salvar(a);
    }

    @GET
    @Produces("application/json")
    @Path("getAll")
    public String getAnimais() {
        Gson g = new Gson();
        AnimalDao animais = new AnimalDao();
        return g.toJson(animais.listarTodos());
    }
    
    @POST
    @Consumes("application/json")
    @Path("delete/")
    public void deleteAnimal(String animal) {
        Gson g = new Gson();
        AnimalDao animalDao = new AnimalDao();
        Animal a = g.fromJson(animal, Animal.class);
        animalDao.deletar(a);
    }

}
