package dao;

import exception.CrudException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;

public class RoleDAO extends DBContext {

    /**
     * Map a row in ResultSet to Role property.
     *
     * <pre>
     * | Role's Setter     | Column Name |
     * |-------------------|-------------|
     * | setId(int)        | RoleID      |
     * | setName(String)   | Name        |
     * | setOrder(int)     | Order       |
     * | setStatus(boolean)| Status      |
     * </pre>
     *
     * @param role the filled Role
     * @param rs role table
     * @throws SQLException when missing column
     */
    private void mapping(Role role, ResultSet rs) throws SQLException {
        role.setId(rs.getInt("RoleID"));
        role.setName(rs.getString("Name"));
        role.setOrder(rs.getInt("Order"));
        role.setStatus(rs.getBoolean("Status"));
    }

    public ArrayList<Role> findAll() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "select [RoleID], [Name], [Order], Status from Role";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Role role = new Role();
                mapping(role, rs);
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new CrudException("Get All Role Fail", ex);
        }

        return roles;
    }

    public HashMap<Integer, Role> getRoleTable() {
        ArrayList<Role> ls = findAll();
        HashMap<Integer, Role> table = new HashMap<>();
        for (Role r : ls) {
            table.put(r.getId(), r);
        }
        return table;
    }

    /**
     * @deprecated use findById
     */
    @Deprecated
    public Role find(int id) throws SQLException {
        String sql = "select [RoleID], [Name], [Order], Status from Role where [RoleID] = ?";
        Role role = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role.setId(rs.getInt("RoleID"));
                role.setName(rs.getString("Name"));
                role.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    role.setStatus(true);
                } else {
                    role.setStatus(false);
                }
            }
        }
        return role;
    }

    /**
     * Only map id, name property in Role.
     *
     * @param id
     * @return
     */
    public Role findById(int id) {
        String sql = "select [RoleID], [Name] from Role where [RoleID] = ?";
        Role role = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = new Role();
                    rs.getInt("RoleID");
                    role.setName(rs.getString("Name"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return role;
    }

    @Deprecated
    public Role findByName(String name) {
        String sql = "select [RoleID], [Name] from Role where [Name] = ?";
        Role role = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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

    public int save(Role r) {
        String sql = "insert Role values(?, ?, ?, ?);";
        int id = 0;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, r.getName().trim().toUpperCase());
            stmt.setInt(2, r.getOrder());
            stmt.setBoolean(3, true);
            stmt.setString(4, r.getType());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            id = rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException ex) {
            throw new CrudException("Save Role Fail", ex);
        }
        return id;
    }

    /**
     * @deprecated use save()
     */
    @Deprecated
    public void insertRole(Role s) {
        String sql = "INSERT INTO [Role]\n"
                + "           ([Name]\n"
                + "           ,[Order]\n"
                + "           ,[Status]\n"
                + "           ,[type])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, s.getName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Deprecated
    public void updateRole(Role s) {
        String sql = "UPDATE [Role]\n"
                + "   SET [Name] = ?\n"
                + "      ,[Order] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[type] = ?\n"
                + " WHERE [RoleID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getId());
            stm.setString(1, s.getName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void delete(Role r) {
        String sql = "DELETE Role WHERE RoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, r.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new CrudException("Delete Role Fail", ex);
        }
    }

    /**
     * Use delete(
     *
     * @param id
     * @deprecated
     */
    @Deprecated
    public void deleteRole(int id) {
        String sql = "DELETE Role"
                + " WHERE RoleID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @Deprecated
    public Role getRoleLast() {
        try {
            String sql = "SELECT * FROM Role WHERE RoleID = (SELECT MAX(RoleID) FROM Role)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("RoleID"));
                role.setOrder(rs.getInt("Order"));
                role.setStatus(rs.getBoolean("Status"));
                role.setName(rs.getString("Name"));
                role.setType(rs.getString("type"));

                return role;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<Role> allRole_Name() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "select Name, RoleId\n" +
                    "from Role";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("RoleId"));
                role.setName(rs.getString("Name"));
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new CrudException("Get All Role Fail", ex);
        }
        return roles;
    }
}
