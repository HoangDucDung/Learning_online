package controller.management;

import dao.PermissionDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Permission;
import model.Role;
import util.RequestParamUtil;

@WebServlet(name = "RolePermissionController", urlPatterns = {"/management/rolepermission"})
public class RolePermissionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RoleDAO roleDAO = new RoleDAO();
            PermissionDAO permissionDAO = new PermissionDAO();
            List<Role> roles = roleDAO.findAll();
            List<Permission> permissions = permissionDAO.findAll();
            Role selectedRole = null;

            String roleName = req.getParameter("role");

            if (roleName != null) {
                selectedRole = roleDAO.findByName(roleName);
            }

            if (selectedRole == null) {
                selectedRole = roleDAO.findByName("ADMIN");
            }

            List<Permission> selectedPermission = permissionDAO.findPermissionOfRole(selectedRole.getId());

            req.setAttribute("selectedPermission", selectedPermission);
            req.setAttribute("selectedRole", selectedRole);
            req.setAttribute("roles", roles);
            req.setAttribute("permissions", permissions);
            req.getRequestDispatcher("../view/role-permission.jsp").forward(req, resp);
        } catch (Exception ex) {
            resp.sendError(500);
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roleId = Integer.parseInt(req.getParameter("roleId"));
        String roleName = req.getParameter("role");
        int[] permissionIds = RequestParamUtil.getParamsInt(req, roleName);

        PermissionDAO permissionDAO = new PermissionDAO();

        permissionDAO.deletePermissionOfRole(roleId);

        if (permissionIds != null) {
            for (int permissionId : permissionIds) {
                permissionDAO.assignPermissionToRole(permissionId, roleId);
            }
        }
        String queryString = req.getQueryString();
        queryString = (queryString == null || queryString.isEmpty()) ? "" : "?" + queryString;
        resp.sendRedirect(req.getRequestURL().toString() + queryString);
    }
}
