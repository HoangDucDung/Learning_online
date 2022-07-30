package service;

import com.google.gson.Gson;
import dao.AnswerDAO;
import javax.ws.rs.Consumes;

public class AnswerService {
    private AnswerDAO answerDAO;
    private Gson gson;
    
    public AnswerService() {
        answerDAO = new AnswerDAO();
        gson = new Gson();
    }
    
}
