package controller.management;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import dao.PermissionDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        RoleDAO roleDAO = new RoleDAO();
        PermissionDAO permissionDAO = new PermissionDAO();
        List<Role> roles = roleDAO.findAll();
        List<Permission> permissions = permissionDAO.findAll();

        int roleId = RequestParamUtil.parseInt(req, "roleId");
        Optional<Role> optional = roles.stream().filter(r -> r.getId() == roleId).findAny();

        Role selectedRole = roles.isEmpty() ? null
                : (optional.isPresent() ? optional.get() : roles.get(0));

        List<Permission> selectedPermission = permissionDAO.findPermissionOfRole(selectedRole.getId());

        req.setAttribute("selectedPermission", selectedPermission);
        req.setAttribute("selectedRole", selectedRole);
        req.setAttribute("roles", roles);
        req.setAttribute("permissions", permissions);
        req.getRequestDispatcher("../view/role-permission.jsp").forward(req, resp);
    }

    /**
     * Create a new Role.
     * <p>
     * Json format:<code>{ id: 0, name: "admin" }</code>
     * </p>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        try (Reader reader = new InputStreamReader(req.getInputStream())) {
            JsonReader jsonReader = new JsonReader(reader);
            Role role = gson.fromJson(jsonReader, Role.class);
            int roleId = new RoleDAO().save(role);
            role.setId(roleId);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.write(gson.toJson(role));
            resp.setStatus(201);
        }
    }

    /**
     * Update a role's permissions.
     * <p>
     * Json format:
     * <code>{ "roleId: 1, "permissionIds": [1, 2, 3 , 4, 5] }</code>
     * </p>
     *
     * @param req
     * @param resp
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(req.getInputStream(), "UTF-8"))) {
            List<Integer> permissionIds = null;
            int roleId = 0;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                if (name.equals("roleId")) {
                    roleId = reader.nextInt();
                } else if (name.equals("permissionIds") && reader.peek() != JsonToken.NULL) {
                    permissionIds = parseJsonIntegerArray(reader);
                }
            }
            reader.endObject();

            PermissionDAO permissionDAO = new PermissionDAO();
            permissionDAO.deletePermissionOfRole(roleId);
            if (permissionIds != null) {
                for (int permissionId : permissionIds) {
                    permissionDAO.assignPermissionToRole(permissionId, roleId);
                }
            }
        }
    }

    /**
     * Parse json integer array. Example a json array [1, 2, 3, 4, 5] will be
     * pasre <code>List<Integer></code>
     *
     * @param reader point start of int array
     * @return list integer from json int array
     * @throws IOException if parse error
     */
    private List<Integer> parseJsonIntegerArray(JsonReader reader) throws IOException {
        List<Integer> ints = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            ints.add(reader.nextInt());
        }
        reader.endArray();
        return ints;
    }

    /**
     * Delete a role by role is passed in query string.
     * <p>
     * Request: DELETE /management/rolepermission?roleId=id
     * </p>
     *
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roleId = RequestParamUtil.parseInt(req, "roleId");
        try {
            RoleDAO roleDAO = new RoleDAO();
            roleDAO.delete(new Role(roleId, ""));
            resp.setStatus(200);
        } catch (Exception ex) {
            resp.setStatus(404);
            ex.printStackTrace();
        }
    }
}
