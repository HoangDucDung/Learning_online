package service;

import com.google.gson.Gson;
import dao.QuestionDAO;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Question;

@Path("/question")
public class QuestionService {

    private QuestionDAO questionDAO;

    public QuestionService() {
        questionDAO = new QuestionDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Gson gson = new Gson();
        List questions = questionDAO.findAll();
        return Response.ok().entity(gson.toJson(questions)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("id") int id) {
        Gson gson = new Gson();
        Question question = questionDAO.findById(id);
        return Response.ok().entity(gson.toJson(question)).build();
    }
}
