package controller.management;

import dao.SettingDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Setting;

@WebServlet(name = "SettingController", urlPatterns = {"/management/setting"})
public class SettingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDAO settingDAO = new SettingDAO();
        String type = request.getParameter("cid");
        String cid = "-1";
        if (type != null) {
            cid = type;
        }
        String status_raw = request.getParameter("status");
        String status = "-1";
        if (status_raw != null && status_raw.equalsIgnoreCase("true")) {
            status = "true";
        } else if (status_raw != null && status_raw.equalsIgnoreCase("false")) {
            status = "false";
        } else {
            status = "-1";
        }

        String value = request.getParameter("search");
        if (value == "") {
            value = null;
        }

        ArrayList<Setting> allSetting_Type = new ArrayList<>();
        if (value == null && status.equalsIgnoreCase("-1") && cid.equalsIgnoreCase("-1")) {
            ArrayList<Setting> allSettings = settingDAO.getAllSettings();
            allSetting_Type = settingDAO.getAllSettings_DistinctType();
            request.setAttribute("listSetting", allSettings);
        }
        if (value != null) {
            ArrayList<Setting> searchby_value = settingDAO.searchby_value(value);
            allSetting_Type = settingDAO.getAllSettings_DistinctType();
            request.setAttribute("listSetting", searchby_value);
        }
        if (!status.equalsIgnoreCase("-1") || !cid.equalsIgnoreCase("-1")) {
            ArrayList<Setting> filterResult = settingDAO.searchBy_Filter(cid, status);
            allSetting_Type = settingDAO.getAllSettings_DistinctType();
            request.setAttribute("listSetting", filterResult);
        }
        request.setAttribute("allSetting_Type", allSetting_Type);
        request.getRequestDispatcher("../view/setting.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
