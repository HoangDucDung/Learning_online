package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import model.Role;

public class RoleDAO extends DBContext {

    private void mapping(Role role, ResultSet rs) throws SQLException {
        role.setId(rs.getInt("RoleID"));
        role.setName(rs.getString("Name"));
    }

    public ArrayList<Role> findAll() throws SQLException {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "select [RoleID], [Name] from Role";

        try ( Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Role role = new Role();
                mapping(role, rs);
                roles.add(role);
            }
        }

        return roles;
    }

    public HashMap<Integer, Role> getRoleTable() throws SQLException {
        ArrayList<Role> ls = findAll();
        HashMap<Integer, Role> hm = new HashMap<>();
        for (Role r : ls) {
            hm.put(r.getId(), r);
        }
        return hm;
    }

    public Role find(int id) throws SQLException {
        String sql = "select [RoleID], [Name] from Role where [RoleID] = ?";
        Role role = null;
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = new Role(rs.getInt("RoleID"), rs.getString("Name"));
            }
        }
        return role;
    }

    public Role findByName(String name) {
        String sql = "select [RoleID], [Name] from Role where [Name] = ?";
        Role role = null;
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = new Role(rs.getInt("RoleID"), rs.getString("Name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return role;
    }
}
