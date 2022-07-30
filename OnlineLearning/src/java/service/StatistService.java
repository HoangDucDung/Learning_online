package service;

import com.google.gson.Gson;
import dao.StatisticDAO;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statistics")
public class StatistService {

    private final StatisticDAO statistDAO;
    private final Gson gson;

    public StatistService() {
        statistDAO = new StatisticDAO();
        gson = new Gson();
    }

    @GET
    @Path("/coursesubject")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAmountCourseOfAllSubject() {
        Response resp = null;
        try {
            List table = statistDAO.countCourseInAllSubject();
            String json = gson.toJson(table, List.class);
            resp = Response.ok(json).build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return resp;
    }

    @GET
    @Path("/enrollcourse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAmountEnrollInAllCourse() {
        Response resp = null;
        try {
            List table = statistDAO.countNumberEnrollInAllCourse();
            String json = gson.toJson(table, List.class);
            resp = Response.ok(json).build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return resp;
    }

    @GET
    @Path("/revenue/{startDate}/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchRevenue(@PathParam("startDate") String s, @PathParam("endDate") String e) {
        LocalDate startDate = LocalDate.parse(s);
        LocalDate endDate = LocalDate.parse(e);

        try {
            return Response.ok(gson.toJson(statistDAO.calculateRevenues(startDate, endDate))).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/blogcategorytrend")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchNumberOfViewInAllBlogCategory() {
        Response resp = null;
        try {
            List table = statistDAO.countNumberViewOfAllBlogCategory();
            String json = gson.toJson(table, List.class);
            resp = Response.ok(json).build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return resp;
    }

    @GET
    @Path("/amount_account_subject")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchNumberOfAccountInAllSubject() {
        Response resp = null;
        try {
            List table = statistDAO.getAmountEnrollInAllSubject();
            String json = gson.toJson(table, List.class);
            resp = Response.ok(json).build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return resp;
    }

    @GET
    @Path("/test")
    public Response test() {
        return Response.ok().build();
    }

    @GET
    @Path("/registration/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countRegistration(@PathParam("from") String f, @PathParam("to") String t) {
        LocalDate from = LocalDate.parse(f);
        LocalDate to = LocalDate.parse(t);

        return Response.ok(gson.toJson(statistDAO.countRegistration(from, to))).build();
    }

    @GET
    @Path("/revenue-subject/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRevenueOfAllSubject(@PathParam("from") String f, @PathParam("to") String t) {
        LocalDate startDate = LocalDate.parse(f);
        LocalDate endDate = LocalDate.parse(t);

        return Response.ok(gson.toJson(statistDAO.getRevenueOfAllSubject(startDate, endDate))).build();
    }
}
