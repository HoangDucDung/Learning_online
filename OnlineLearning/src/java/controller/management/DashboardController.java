package controller.management;

import dao.StatisticDAO;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DashboardController", urlPatterns = {"/management/dashboard"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/dashboard.jsp");
        StatisticDAO statistDAO = new StatisticDAO();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);

        try {
            endDate = LocalDate.parse(req.getParameter("endDate"));
            startDate = LocalDate.parse(req.getParameter("startDate"));
        } catch (Exception ex) {
            endDate = LocalDate.now();  // Default endDate is today
            startDate = endDate.minusDays(7); // Default startDate is previous 7 days from today
        }
        
        String timeLine = "in the last " + ChronoUnit.DAYS.between(startDate, endDate) + " days";

        req.setAttribute("timeLine", timeLine);
        req.setAttribute("startDate", startDate);
        req.setAttribute("endDate", endDate);
        req.setAttribute("mostEnrolledCourse", statistDAO.countNumberEnrollInAllCourse());
        req.setAttribute("amountAccount", statistDAO.countRegistedAccount(startDate, endDate));
        req.setAttribute("visitPage", statistDAO.getNumberVisitPage(startDate, endDate));
        req.setAttribute("totalEarning", statistDAO.getAllEarning());
        req.setAttribute("revenue", statistDAO.calculateRevenue(startDate, endDate));

        dispatcher.forward(req, resp);
    }
}
