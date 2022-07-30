package controller.management;

import dao.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Slider;

@WebServlet(name = "SliderController", urlPatterns = {"/management/slide-list"})
public class SliderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        SliderDAO sliderDAO = new SliderDAO();
        String display = "-1";
        if (request.getParameter("display") != null) {
            display = request.getParameter("display");
        }
        ArrayList<Slider> allSliders;
        String search = null;
        if (!display.equals("-1")) {
            allSliders = sliderDAO.getSlidersByStatus(display);
        } else {
            allSliders = sliderDAO.getAllSliders();
        }
        if (search != null) {
            allSliders = sliderDAO.getSlidersByTitleOrBacklink(search);
        }
        request.setAttribute("display", display);
        request.setAttribute("allSliders", allSliders);
        request.getRequestDispatcher("/view/slide-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        if (request.getParameter("id-hide") != null) {
            sliderDAO.setStatusSlider(Integer.parseInt(request.getParameter("id-hide")), false);
        }
        if (request.getParameter("id-show") != null) {
            sliderDAO.setStatusSlider(Integer.parseInt(request.getParameter("id-show")), true);
        }
        response.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        if (request.getParameter("id") != null) {
            sliderDAO.deleteSlider(Integer.parseInt(request.getParameter("id")));
        }
        response.getWriter().flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
