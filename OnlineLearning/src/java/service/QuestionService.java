package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.QuestionDAO;
import dao.QuizSessionDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import model.Question;
import model.Account;
import model.Answer;
import model.QuizLesson;
import model.QuizSession;
import util.SessionUtil;

@Path("/question")
public class QuestionService {

    private final QuestionDAO questionDAO;
    private final Gson gson;

    @Context
    private HttpServletRequest req;

    public QuestionService() {
        questionDAO = new QuestionDAO();
        gson = new Gson();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(gson.toJson(questionDAO.findByLessonId(4))).build();
    }

    @POST
    @Path("/test2")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response test2(String json) {
        System.out.println(">>>>");
        Question question = gson.fromJson(json, Question.class);

        for (Answer a : question.getAnswers()) {
            System.out.println(a);
        }
        return Response.ok().build();
    }

    /**
     * Get question by id: GET /api/question/{id}
     *
     * @param id question
     * @return Question in json format
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionById(@PathParam("id") int id) {
        // Gson restrictGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Question question = questionDAO.findById(id);
        return Response.ok(gson.toJson(question)).build();
    }

    @GET
    @Path("/session/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRemainTime(@PathParam("id") int id) {
        Account account = SessionUtil.getAccount(req);
        QuizSession quizSession = new dao.QuizSessionDAO().find(account.getId(), id);

        Map<String, String> time = new HashMap<>();
        time.put("startTime", quizSession.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
        time.put("expiredTime", quizSession.getExpiredTime().format(DateTimeFormatter.ISO_DATE_TIME));

        return Response.ok().entity(gson.toJson(time)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByLessonId(@QueryParam("lesson") int id) {
        Gson restrictGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<Question> questions = questionDAO.findByLessonId(id);
        return Response.ok().entity(restrictGson.toJson(questions)).build();
    }

    @POST
    @Path("/submit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitAnswer(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        JsonObject jsonObject = (JsonObject) jsonElement;
        int quizId = jsonObject.get("quizId").getAsInt();

        QuizSessionDAO quizSessionDAO = new QuizSessionDAO();
        LocalDateTime current = LocalDateTime.now();
        Account account = SessionUtil.getAccount(req);

        if (account == null) {
            return Response.status(401).build();
        }

        QuizSession quizSession = quizSessionDAO.find(account.getId(), quizId);

        if (quizSession == null || quizSession.getExpiredTime() == null) {
            return Response.status(403)
                    .entity("{\"message\": \"There is no session\"}")
                    .build();
        }

        if (current.isAfter(quizSession.getExpiredTime())) {
            return Response.status(403)
                    .entity("{\"message\": \"Expired session\"}")
                    .build();
        }

        try {
            quizSessionDAO.finishQuiz(SessionUtil.getAccount(req).getId(), quizId, current);
            questionDAO.saveAnswer(SessionUtil.getAccount(req).getId(), jsonElement);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
        System.out.println(json);
        return Response.ok()
                .entity("{\"message\": \"Submit successfuly\"}")
                .build();
    }
}
