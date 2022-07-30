package controller.management;

import dao.SliderDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Slider;

@WebServlet(name = "SliderDetailController", urlPatterns = {"/management/slide-edit"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class SliderEditController extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        SliderDAO sliderDAO = new SliderDAO();
        String action = "";
        if (request.getParameter("id") == null) {
            action = "ADD";
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            Slider slider = sliderDAO.getSliderById(id);
            action = "EDIT";

            request.setAttribute("slider", slider);
            request.setAttribute("id", request.getParameter("id"));
        }

        request.setAttribute("action", action);
        request.getRequestDispatcher("/view/slide-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("ADD")) {
            addSlider(request, response);

        } else if (action.equalsIgnoreCase("EDIT")) {
            editSlider(request, response);
        }
    }

    private void editSlider(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Slider slider = new Slider();

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String description = request.getParameter("description");
        String img = null;
        if (uploadFile(request).equals("")) {
            img = new SliderDAO().getSliderById(id).getImageUrl();
        } else {
            img = uploadFile(request);
        }
        String backlink = request.getParameter("backlink");

        slider.setSliderID(id);
        slider.setTitle(title);
        slider.setSubTitle(subTitle);
        slider.setDescription(description);
        slider.setImageUrl(img);
        slider.setNavigationLink(backlink);

        SliderDAO sliderDAO = new SliderDAO();
        sliderDAO.updateSlider(slider);

        response.sendRedirect("slide-list");
    }

    private void addSlider(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Slider slider = new Slider();
        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String description = request.getParameter("description");
        String img = uploadFile(request);
        String backlink = request.getParameter("backlink");
        
        slider.setTitle(title);
        slider.setSubTitle(subTitle);
        slider.setDescription(description);
        slider.setImageUrl(img);
        slider.setNavigationLink(backlink);

        SliderDAO sliderDAO = new SliderDAO();
        sliderDAO.insertSlider(slider);

        response.sendRedirect("slide-list");
    }

    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("photo");
            fileName = (String) getFileName(filePart);
            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
