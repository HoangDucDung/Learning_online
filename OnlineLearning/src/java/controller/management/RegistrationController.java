package controller.management;

import dao.TransactionHistoryDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.TransactionHistory;

@WebServlet(name = "RegistrationController", urlPatterns = {"/management/registration"})
public class RegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ArrayList<TransactionHistory> listTrans = new TransactionHistoryDAO().getListTransaction();
        int totalTrans = listTrans.size();
        int numItem = 10;
        int totalpage = 0;
        if (totalTrans % numItem == 0) {
            totalpage = (int) totalTrans / numItem;
        } else {
            totalpage = (int) totalTrans / numItem + 1;
        }
        request.setAttribute("totalpage", totalpage);

        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
            if (page < 1 || page > totalpage) {
                page = 1;
            }
        }
        request.setAttribute("page", page);

        ArrayList<TransactionHistory> listTransCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listTrans.size(); i++) {
            if (i >= (page - 1) * numItem && i < page * numItem) {
                listTransCurrentInPage.add(listTrans.get(i));
            }
        }
        request.setAttribute("listTrans", listTransCurrentInPage);

        Date unlimited = new Date(-1);
        request.setAttribute("unlimited", unlimited);

        Date dateNow = Date.valueOf(LocalDate.now());
        request.setAttribute("now", dateNow);

        request.getRequestDispatcher("../view/registration-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        Account account = (Account) request.getSession().getAttribute("account");

        String valueSearch = request.getParameter("valueSearch");
        String validFrom = request.getParameter("validFrom");
        String validTo = request.getParameter("validTo");
        String status = request.getParameter("status");

        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        Date unlimited = new Date(-1);
        Date now = Date.valueOf(LocalDate.now());

        ArrayList<TransactionHistory> listTransByTitleOrEmail = new TransactionHistoryDAO().getListTransactionByTitleEmail(valueSearch);
        ArrayList<TransactionHistory> listTransByTransactionTime = new TransactionHistoryDAO().getListTransByTransactionTime(validFrom, validTo);

        ArrayList<TransactionHistory> listTransFinal = new ArrayList<>();
        for (TransactionHistory trans : listTransByTitleOrEmail) {
            for (TransactionHistory trans1 : listTransByTransactionTime) {
                if (trans.getId() == trans1.getId()) {
                    if (status.equalsIgnoreCase("all")) {
                        listTransFinal.add(trans);
                    } else if (status.equalsIgnoreCase("active")) {
                        if (trans.getValidTo().equals(unlimited) || now.equals(trans.getValidTo()) || now.before(trans.getValidTo())) {
                            listTransFinal.add(trans);
                        }
                    } else {
                        if (now.after(trans.getValidTo()) && !trans.getValidTo().equals(unlimited)) {
                            listTransFinal.add(trans);
                        }
                    }
                }
            }
        }

        int totalCourse = listTransFinal.size();
        int numItem = 10;
        int totalpage = 0;
        if (totalCourse % numItem == 0) {
            totalpage = (int) totalCourse / numItem;
        } else {
            totalpage = (int) totalCourse / numItem + 1;
        }

        ArrayList<TransactionHistory> listTransCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listTransFinal.size(); i++) {
            if (i >= (pageNum - 1) * numItem && i < pageNum * numItem) {
                listTransCurrentInPage.add(listTransFinal.get(i));
            }
        }
        response.getWriter().write("<table class=\"table table-striped\" id=\"table\">\n"
                + "                                    <thead>\n"
                + "                                        <tr>\n"
                + "                                            <th>ID <i class=\"fa-solid fa-sort\" onclick=\"sortTable(0)\"></i></th>\n"
                + "                                            <th>Email <i class=\"fa-solid fa-sort\" onclick=\"sortTable(1)\"></i></th>\n"
                + "                                            <th>Registration Time <i class=\"fa-solid fa-sort\" onclick=\"sortTable(2)\"></i></th>\n"
                + "                                            <th style=\"width: 150px\">Course <i class=\"fa-solid fa-sort\" onclick=\"sortTable(3)\"></i></th>\n"
                + "                                            <th>Package <i class=\"fa-solid fa-sort\" onclick=\"sortTable(4)\"></i></th>\n"
                + "                                            <th>Total cost <i class=\"fa-solid fa-sort\" onclick=\"sortTable(5)\"></i></th>\n"
                + "                                            <th>Status <i class=\"fa-solid fa-sort\" onclick=\"sortTable(6)\"></i></th>\n"
                + "                                            <th>Valid from <i class=\"fa-solid fa-sort\" onclick=\"sortTable(7)\"></i></th>\n"
                + "                                            <th>Valid to <i class=\"fa-solid fa-sort\" onclick=\"sortTable(8)\"></i></th>\n"
                + "                                            <th>Last updated by <i class=\"fa-solid fa-sort\" onclick=\"sortTable(9)\"></i></th>\n"
                + "                                            <th>Action</th>\n"
                + "                                        </tr>\n"
                + "                                    </thead>\n");
        if (!listTransCurrentInPage.isEmpty()) {
            response.getWriter().write("                                    <tbody id=\"myTable\">\n");
            for (TransactionHistory trans : listTransCurrentInPage) {
                response.getWriter().write("<tr>\n"
                        + "                                                <td>" + trans.getId() + "</td>\n"
                        + "                                                <td>" + trans.getAccountID().getEmail() + "</td>\n"
                        + "                                                <td>" + trans.getTransactionTime() + "</td>\n"
                        + "                                                <td>" + trans.getCourseID().getName() + "</td>\n"
                        + "                                                <td>" + trans.getCoursePackageID().getName() + "</td>\n"
                        + "                                                <td>" + trans.getAmount() + "</td>\n");
                if (trans.getValidTo().equals(unlimited) || now.before(trans.getValidTo()) || now.equals(trans.getValidTo())) {
                    response.getWriter().write("<td class='text-success'>Active</td>\n");
                } else {
                    response.getWriter().write("<td class='text-danger'>Expired</td>\n");
                }
                response.getWriter().write("                                                <td>" + trans.getValidFrom() + "</td>\n");
                if (trans.getValidTo().equals(unlimited)) {
                    response.getWriter().write("<td>Unlimited</td>\n");
                } else {
                    response.getWriter().write("<td>" + trans.getValidTo() + "</td>\n");
                }
                response.getWriter().write("<td>" + trans.getUpdatedBySaleID().getFirstName() + " " + trans.getUpdatedBySaleID().getLastName() + "</td>");
                response.getWriter().write("<td><a href=\"./registration-detail?id=" + trans.getId() + "\" class=\"text-primary\">View</a>");
                if (account.getAccountID() == trans.getSaleID().getAccountID()) {
                    response.getWriter().write("/ <span onclick=\"deleteRegistration(" + trans.getId() + ", this)\" class=\"text-danger link-delete\">Delete</span></td>\n");
                }
                response.getWriter().write("               </tr>");
            }
            response.getWriter().write("</tbody></table>");
            response.getWriter().write("<div class=\"pagination\">\n"
                    + "                <ul class=\"pagination-list\">\n"
                    + "                    <li>\n"
                    + "                        <button onclick=\"pagination(" + (pageNum - 1 == 0 ? 1 : pageNum - 1) + ")\" class=\"previous-btn\" >Previous</button>\n"
                    + "                    </li>\n");
            for (int i = 1; i <= totalpage; i++) {
                response.getWriter().write("<li>\n"
                        + "                            <button onclick=\"pagination(" + i + ")\" " + (i == pageNum ? ("class = \"paging-active page-num\"") : ("class=\"page-num\"")) + ">" + i + "</button>\n"
                        + "                        </li>");
            }

            response.getWriter().write("<li>\n"
                    + "                        <button onclick=\"pagination(" + (pageNum + 1 > totalpage ? totalpage : pageNum + 1) + ")\" class=\"next-btn\" >Next</button>\n"
                    + "                    </li>\n"
                    + "                </ul>\n"
                    + "                <input type=\"hidden\" id=\"page-num\" value=" + pageNum + ">\n"
                    + "            </div>");
        } else {
            response.getWriter().write("<input type=\"hidden\" id=\"page-num\" value=\"" + pageNum + "\">");

        }
        response.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new TransactionHistoryDAO().deleteTransaction(id);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
