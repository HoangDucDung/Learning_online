package dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import exception.CrudException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Question;
import model.QuestionLevel;

public class QuestionDAO extends DBContext {

    static void mapping(Question q, ResultSet rs) throws SQLException {
        q.setId(rs.getInt("QuestionID"));
        q.setText(rs.getString("QuestionText"));
        q.setImageUrl(rs.getString("QuestionImageUrl"));
        q.setLessonId(rs.getInt("LessonID"));
        q.setQuestionLevelId(rs.getInt("QuestionLevelID"));
        q.setOrder(rs.getInt("Order"));
        q.setActive(rs.getBoolean("Status"));
    }

    public void update(Question q) {
        String sql = "UPDATE Question SET QuestionText = ?, QuestionImageUrl = ?, "
                + "LessonID = ?, QuestionLevelID = ?, [Order] = ? "
                + "WHERE QuestionID = ?";

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setString(1, q.getText());
            s.setString(2, q.getImageUrl());
            s.setInt(3, q.getLessonId());
            s.setInt(4, q.getQuestionLevelId());
            s.setInt(5, q.getOrder());
            s.setInt(6, q.getId());

            s.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int save(Question q) {
        String sql = "insert into Question values (?, ?, ?, ?, ?, ?)";
        int id = 0;
        try (PreparedStatement s = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, q.getText());
            s.setString(2, q.getImageUrl());
            s.setInt(3, q.getLessonId());
            s.setInt(4, q.getQuestionLevelId());
            s.setInt(5, q.getOrder());
            s.setBoolean(6, true);

            s.executeUpdate();

            ResultSet rs = s.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            throw new CrudException(ex.getMessage(), ex.getCause());
        }
        return id;
    }

    public List<Question> findAll() {
        List<Question> ls = new ArrayList<>();
        String sql = "SELECT q.QuestionID, q.QuestionText, q.QuestionImageUrl, q.LessonID, "
                + "q.QuestionLevelID, q.[Order], q.Status, l.LevelName "
                + "FROM Question AS q LEFT JOIN QuestionLevel AS l "
                + "ON q.QuestionLevelID = l.QuestionLevelID";

        AnswerDAO answerDAO = new AnswerDAO();
        QuestionLevelDAO questionLevelDAO = new QuestionLevelDAO();

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Question question = new Question();
                mapping(question, rs);

                question.setLevel(questionLevelDAO.findById(question.getQuestionLevelId()));
                question.setAnswers(answerDAO.findByQuestionId(question.getId()));

                ls.add(question);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ls;
    }

    public List<Question> findAll(Comparator<Question> sorter) {
        List<Question> ls = findAll();
        ls.sort(sorter);
        return ls;
    }

    public Question findById(int id) {
        String sql = "SELECT q.QuestionID, q.QuestionText, q.QuestionImageUrl, q.LessonID, q.QuestionLevelID, q.[Order], "
                + "q.Status, l.LevelName FROM Question AS q LEFT JOIN QuestionLevel AS l "
                + "ON q.QuestionLevelID = l.QuestionLevelID WHERE QuestionID = ?";
        Question question = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                question = new Question();
                mapping(question, rs);
                question.setLevel(new QuestionLevelDAO().findById(question.getQuestionLevelId()));
                question.setAnswers(new AnswerDAO().findByQuestionId(id));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return question;
    }

    public void setActive(int id, boolean active) throws SQLException {
        String sql = "update Question set Status = ? where QuestionID = ?";
        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setBoolean(1, active);
            s.setInt(2, id);
            s.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "delete Question where QuestionID = ?";
        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1, id);
            s.executeUpdate();
        }
    }

    /**
     * Include answers, QuestionLevel
     *
     * @param id
     * @return
     */
    public List<Question> findByLessonId(int id) {
        AnswerDAO answerDAO = new AnswerDAO();
        QuestionLevelDAO questionLevelDAO = new QuestionLevelDAO();

        List<Question> questions = new ArrayList<>();
        String sql = "select * from Question where LessonID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                mapping(q, rs);
                q.setLevel(questionLevelDAO.findById(q.getQuestionLevelId()));
                q.setAnswers(answerDAO.findByQuestionId(q.getId()));
                questions.add(q);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    public ArrayList<Question> listQuestions(int lessonId, int questionid) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select q.* from [Question] q where q.LessonID = ? and q.QuestionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            stm.setInt(2, questionid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();

                question.setId(rs.getInt("QuestionID"));
                question.setText(rs.getString("QuestionText"));
                question.setImageUrl(rs.getString("QuestionImageUrl"));
                question.setLessonId(rs.getInt("LessonID"));

                QuestionLevel questionLevel = new QuestionLevel(rs.getInt("QuestionLevelID"));

                question.setLevel(questionLevel);
                question.setOrder(rs.getInt("Order"));
                question.setActive(rs.getBoolean("Status"));
                list.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int countbyID(int lessonID) {
        try {
            String sql = "SELECT count(*) as Total FROM Question\n"
                    + "WHERE LessonID = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Question> listAllBlogByPage(int lessonId, int questionid) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select q.* from [Question] q where q.LessonID = ? and q.QuestionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            stm.setInt(2, questionid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();

                question.setId(rs.getInt("QuestionID"));
                question.setText(rs.getString("QuestionText"));
                question.setImageUrl(rs.getString("QuestionImageUrl"));
                question.setLessonId(rs.getInt("LessonID"));

                QuestionLevel questionLevel = new QuestionLevel(rs.getInt("QuestionLevelID"));

                question.setLevel(questionLevel);
                question.setOrder(rs.getInt("Order"));
                question.setActive(rs.getBoolean("Status"));
                list.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Question> total(int lessonId) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select q.* from [Question] q where q.LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();

                question.setId(rs.getInt("QuestionID"));
                question.setText(rs.getString("QuestionText"));
                question.setImageUrl(rs.getString("QuestionImageUrl"));
                question.setLessonId(rs.getInt("LessonID"));

                QuestionLevel questionLevel = new QuestionLevel(rs.getInt("QuestionLevelID"));

                question.setLevel(questionLevel);
                question.setOrder(rs.getInt("Order"));
                question.setActive(rs.getBoolean("Status"));
                list.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int count(int lessonID) {
        try {
            String sql = "SELECT count(*) as Total FROM Question where lessonID = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private List<Integer> getIdsOfTrueAnswerInQuizLesson(int quizId) {
        String sql = "SELECT a.AnswerID FROM Question AS q INNER JOIN Answer AS a ON a.QuestionID = q.QuestionID\n"
                + "WHERE a.Status = 1 AND q.LessonID = ?";
        List<Integer> ls = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ls.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ls;
    }

    private void clearOldAnswer(int accountId, int quizId) throws SQLException {
        String sql = "DELETE FROM CompletedQuestion WHERE AccountID = ? "
                + "AND QuestionID IN (SELECT QuestionID FROM Question WHERE LessonID = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            stmt.setInt(2, quizId);
            stmt.executeLargeUpdate();
        }
    }

    /**
     * Json format: { "quizId":"4", "answers":[
     * {"questionId":118,"answerIds":["475"]},
     * {"questionId":109,"answerIds":["440","441"]} ] }
     *
     * @param accountId
     * @param jsonElement
     */
    public void saveAnswer(int accountId, JsonElement jsonElement) throws SQLException {
        JsonObject json = (JsonObject) jsonElement;
        int quizId = json.get("quizId").getAsInt();
        System.out.println(">>>>>>>");
        PreparedStatement stmt = null;
        try {
            clearOldAnswer(accountId, quizId);
            List<Integer> trueAnswerIds = getIdsOfTrueAnswerInQuizLesson(quizId);
            stmt = connection.prepareStatement("insert into CompletedQuestion values (?, ?, ?, ?)");
            stmt.setInt(1, accountId);

            for (JsonElement e : json.get("answers").getAsJsonArray()) {
                JsonObject jAnswer = (JsonObject) e;
                int questionId = jAnswer.get("questionId").getAsInt();
                stmt.setInt(2, questionId);

                for (JsonElement j : jAnswer.get("answerIds").getAsJsonArray()) {
                    int id = j.getAsInt();
                    stmt.setInt(3, id);
                    stmt.setBoolean(4, trueAnswerIds.contains(id));
//                    System.out.format("%s %s %s %s\n", )
                    stmt.executeUpdate();
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public ArrayList<Question> QuestionByLessonId(int lessonId) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            String sql = "select q.*, ql.LevelName, l.Name from Question q, QuestionLevel ql, Lesson l\n"
                    + " where q.QuestionLevelID = ql.QuestionLevelID  and l.LessonID = q.LessonID";

            if (lessonId != 0) {
                sql += " and q.LessonID = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (lessonId != 0) {
                stm.setInt(1, lessonId);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();

                question.setId(rs.getInt("QuestionID"));
                question.setText(rs.getString("QuestionText"));
                question.setLessonId(rs.getInt("LessonID"));

                QuestionLevel questionLevel = new QuestionLevel();
                questionLevel.setId(rs.getInt("QuestionLevelID"));
                questionLevel.setLevelName(rs.getString("LevelName"));

                question.setLevel(questionLevel);
                question.setOrder(rs.getInt("Order"));
                question.setActive(rs.getBoolean("Status"));
                list.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Question> QuestionByFilter(ArrayList<Integer> arrl, ArrayList<String> arrD, int stt, int level) {
        ArrayList<Question> list = new ArrayList<>();
        int index = 1;
        try {
            String sql = "  select q.*, ql.LevelName, d.Name, dt.Name, l.Name from Question q, QuestionLevel ql, Dimension d, DimensionType dt, Lesson l\n"
                    + "  where q.QuestionLevelID = ql.QuestionLevelID and q.QuestionID = d.QuestionID and d.TypeID = dt.TypeID and l.LessonID = q.LessonID";

            for (int i = 0; i < arrl.size(); i++) {
                if (i == 0) {
                    sql += " and (q.LessonID = ?";
                } else {
                    sql += " or q.LessonID = ?";
                }
                if (i == arrl.size() - 1) {
                    sql += ")";
                }
            }

            for (int i = 0; i < arrD.size(); i++) {
                if (i == 0) {
                    sql += " and (d.Name = ?";
                } else {
                    sql += " or d.Name = ?";
                }
                if (i == arrD.size() - 1) {
                    sql += ")";
                }
            }

            if (stt != -1) {
                sql += " and q.Status = ?";
            }

            if (level != 0) {
                sql += " and q.QuestionLevelID = ?";
            }
            System.out.println("sql" + sql);
            PreparedStatement stm = connection.prepareStatement(sql);

            if (!arrl.isEmpty()) {
                for (int i = 0; i < arrl.size(); i++) {
                    stm.setInt(i + 1, arrl.get(i));
                    index++;
                }
                if (!arrD.isEmpty()) {
                    for (int i = 0; i < arrD.size(); i++) {
                        stm.setString(i + 1 + arrl.size(), arrD.get(i));
                        index++;
                    }
                }
            }
            System.out.println("index: " + index);
            if (arrl.isEmpty() && !arrD.isEmpty()) {
                for (int i = 0; i < arrD.size(); i++) {
                    stm.setString(i + 1, arrD.get(i));
                    index++;
                }
            }
            System.out.println("index: " + index);
            if (stt != -1) {
                stm.setInt(index, stt);
                index++;
            }

            if (level != 0) {
                stm.setInt(index, level);
            }
            System.out.println("index: " + index);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();

                question.setId(rs.getInt("QuestionID"));
                question.setText(rs.getString("QuestionText"));
                question.setLessonId(rs.getInt("LessonID"));

                QuestionLevel questionLevel = new QuestionLevel();
                questionLevel.setId(rs.getInt("QuestionLevelID"));
                questionLevel.setLevelName(rs.getString("LevelName"));

                question.setLevel(questionLevel);
                question.setOrder(rs.getInt("Order"));
                question.setActive(rs.getBoolean("Status"));
                list.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void deleteQuestion(int id) {
        try {
            String sql = "DELETE [Question] WHERE [QuestionID] = ?";

            new DimensionDAO().deleteDimensionByQuestion(id);

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
