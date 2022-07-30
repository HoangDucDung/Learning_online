package controller.home;

import dao.BlogDAO;
import dao.CourseAccountDAO;
import dao.SliderDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Blog;
import model.CourseAccount;
import model.Slider;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        SliderDAO sliderDB = new SliderDAO();
        ArrayList<Slider> sliders = sliderDB.getSlidersDisplay();
        CourseAccountDAO courseAccountDAO = new CourseAccountDAO();
        ArrayList<CourseAccount> bestRatedCourses = courseAccountDAO.getBestRatedCourses();
        BlogDAO blogDAO=new BlogDAO();
        ArrayList<Blog> mostViewedBlogs = blogDAO.getMostViewedBlogs();
        request.setAttribute("mostViewedBlogs", mostViewedBlogs);
        request.setAttribute("bestRatedCourses", bestRatedCourses);
        request.setAttribute("sliders", sliders);
        request.getRequestDispatcher("view/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
