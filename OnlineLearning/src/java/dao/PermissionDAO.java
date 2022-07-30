package dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Permission;

public class PermissionDAO extends DBContext {

    public Serializable save(Permission permission) {
        String sql = "insert into PermissionRequest values (?, ?, ?)";

        try ( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, permission.getName());
            stmt.setString(2, permission.getRequestUrl());
            stmt.setString(3, permission.getMethod());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void mapping(Permission permission, ResultSet rs) throws SQLException {
        permission.setId(rs.getInt(1));
        permission.setName(rs.getString(2));
        permission.setRequestUrl(rs.getString(3));
        permission.setMethod(rs.getString(4));
    }

    public List<Permission> findAll() {
        String sql = "select * from PermissionRequest";

        ArrayList<Permission> ls = new ArrayList<>();
        try ( Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Permission p = new Permission();
                mapping(p, rs);
                ls.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ls;
    }

    public List<Permission> findPermissionOfRole(int roleId) {
        String sql = "SELECT a.PermissionRequestID, a.Name, a.RequestUrl, a.Method FROM dbo.PermissionRequest AS a\n"
                + "INNER JOIN dbo.RolePermissionRequest AS b ON a.PermissionRequestID = b.PermissionRequestID\n"
                + "WHERE b.RoleID = ?";

        ArrayList<Permission> ls = new ArrayList<>();
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Permission p = new Permission();
                mapping(p, rs);
                ls.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ls;
    }

    public boolean assignPermissionToRole(int permissionId, int roleId) {
        String sql = "insert into RolePermissionRequest values(? , ?);";
        boolean isSuccess = false;
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.setInt(2, permissionId);
            stmt.executeUpdate();
            isSuccess = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void deletePermissionOfRole(int roleId) {
        String sql = "delete from RolePermissionRequest where RoleId = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "delete from PermissionRequest where PermissionRequestID = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
