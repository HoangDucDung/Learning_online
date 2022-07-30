package controller.management;

import dao.DimensionDAO;
import dao.DimensionTypeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Dimension;

@WebServlet(name = "DimensionViewController", urlPatterns = {"/management/dimension-view"})
public class DimensionViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String courseID = request.getParameter("courseID");
        int dimensionID = Integer.parseInt(request.getParameter("dimensionID"));
        DimensionDAO dimensionDAO = new DimensionDAO();
        Dimension dimension = dimensionDAO.getDimensionByID(dimensionID);

        request.setAttribute("dimension", dimension);
        request.setAttribute("courseID", courseID);
        request.getRequestDispatcher("/view/dimension-view.jsp").forward(request, response);
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
