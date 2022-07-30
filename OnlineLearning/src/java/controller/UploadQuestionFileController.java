package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.QuestionLevelDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response.Status;
import model.Answer;
import model.Question;
import model.QuestionLevel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.RequestParamUtil;

@WebServlet(name = "UploadQuestionFileController", urlPatterns = {"/upload-question-file"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UploadQuestionFileController extends HttpServlet {

    public static final String UPLOAD_DIR = "upload";
    private static final Logger LOG = Logger.getLogger(UploadQuestionFileController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = "";
        int quizId = RequestParamUtil.parseInt(req, "quizId");

        try {
            filePath = uploadFile(req);
        } catch (IllegalArgumentException ex) {
            String str = "File type must be .xlsx";
            resp.sendRedirect("/OnlineLearning/management/quiz-question?quizId=" + quizId + "&error=" + str);
            return;
        }

        if (filePath.isEmpty()) {
            resp.setStatus(Status.NOT_FOUND.getStatusCode());
            return;
        }

        File questionFile = new File(filePath);

        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);

        AnswerDAO answerDAO = new AnswerDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        QuestionLevel level = new QuestionLevelDAO().findAll().get(0);
        Gson gson = new Gson();

        for (Row row : sheet) {
            int qId = 0;
            Question question = new Question();
            question.setLessonId(quizId);
            question.setQuestionLevelId(level.getId());

            for (Cell cell : row) {
                // Text in cell in first column is question's text
                if (cell.getColumnIndex() == 0) {
                    question.setText(cell.toString());
                    qId = questionDAO.save(question);
                } else {
                    try {
                        Answer answer = gson.fromJson(cell.toString(), Answer.class);

                        if (qId != 0) {
                            answer.setQuestionId(qId);
                            System.out.println(gson.toJson(answer));
                            answerDAO.save(answer);
                        }
                    } catch (JsonSyntaxException ex) {
                        LOG.info("Wrong Answer json format: " + ex.getMessage());
                    }
                }
            }
        }

        try {
            if (questionFile.delete()) {
                LOG.info("Delete file " + filePath);
            } else {
                LOG.info("Can't delete file " + filePath);
            }
        } catch (Exception ex) {
            LOG.info("Delete Question File Exception " + ex.getMessage());
        }

        resp.sendRedirect("/OnlineLearning/management/quiz-question?quizId=" + quizId);
    }

    public String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        Part part = request.getPart("file");
        String fileName = part.getSubmittedFileName();
        String fileType = part.getContentType();

        // Check xlsx file type
        if (!fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            throw new IllegalArgumentException("Require application/vnd.openxmlformats-officedocument.spreadsheetml.sheet but found " + fileType);
        }

        String uploadFilePath = request.getServletContext().getRealPath("")
                + File.separator + UPLOAD_DIR + File.separator;
        File f = new File(uploadFilePath);

        String out = "";
        if (!f.exists()) {
            f.mkdir();
        }

        InputStream is = null;
        OutputStream outputStream = null;

        try {
            File outputFilePath = new File(uploadFilePath + fileName);
            is = part.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            out = outputFilePath.getPath();

        } catch (IOException e) {
            e.printStackTrace();
            out = "";
        } finally {
            if (is != null) {
                is.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return out;
    }
}
