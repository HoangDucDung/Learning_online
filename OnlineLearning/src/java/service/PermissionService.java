package service;

import com.google.gson.Gson;
import dao.PermissionDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Permission;

@Path("/permission")
public class PermissionService {

    private PermissionDAO permissionDAO;

    public PermissionService() {
        permissionDAO = new PermissionDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPermission() {
        List ls = permissionDAO.findAll();
        Gson gson = new Gson();
        return Response.ok().entity(gson.toJson(ls)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String json) {
        Gson gson = new Gson();
        Permission permission = gson.fromJson(json, Permission.class);
        permissionDAO.save(permission);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        permissionDAO.deleteById(id);
        return Response.ok().build();
    }
}
