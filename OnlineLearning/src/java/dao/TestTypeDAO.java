/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TestType;

/**
 *
 * @author duc21
 */
public class TestTypeDAO extends DBContext{
    public ArrayList<TestType> getAllTestType() {
        ArrayList<TestType> TestTypes = new ArrayList<>();
        try {
            String sql = "SELECT [TestTypeID]\n" +
                        "      ,[TestTypeName]\n" +
                        "      ,[Order]\n" +
                        "      ,[Status]\n" +
                        "      ,[type]\n" +
                        "  FROM [TestType]";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                TestType TestType = new TestType();
                TestType.setTestTypeID(rs.getInt("TestTypeID"));
                TestType.setTestTypeName(rs.getString("TestTypeName"));
                TestType.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    TestType.setStatus(true);
                } else {
                    TestType.setStatus(false);
                }
                TestType.setType(rs.getString("type"));
                TestTypes.add(TestType);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return TestTypes;
    }

    public void insertTestType(TestType s) {
        String sql = "INSERT INTO [TestType]\n" +
                    "           ([TestTypeName]\n" +
                    "           ,[Order]\n" +
                    "           ,[Status]\n" +
                    "           ,[type])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, s.getTestTypeName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateTestType(TestType s) {
        String sql = "UPDATE [TestType]\n" +
                    "   SET [TestTypeName] = ?\n" +
                    "      ,[Order] = ?\n" +
                    "      ,[Status] = ?\n" +
                    "      ,[type] = ?\n" +
                    " WHERE [TestTypeID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getTestTypeID());
            stm.setString(1, s.getTestTypeName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteLessonType(int id) {
        String sql = "DELETE FROM [TestType]\n" +
                    "      WHERE [TestTypeID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public TestType getTestTypeLast() {
        try {
            String sql = "SELECT * FROM TestType WHERE TestTypeID = (SELECT MAX(TestTypeID) FROM TestType)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                TestType TestType = new TestType();
                TestType.setTestTypeID(rs.getInt("TestTypeID"));
                TestType.setOrder(rs.getInt("Order"));
                TestType.setStatus(rs.getBoolean("Status"));
                TestType.setTestTypeName(rs.getString("TestTypeName"));
                TestType.setType(rs.getString("type"));

                return TestType;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<TestType> getAllTestTypes() {
        ArrayList<TestType> TestTypes = new ArrayList<>();
        try {
            String sql = "select distinct TestTypeName, TestTypeID from TestType";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                TestType TestType = new TestType();
                TestType.setTestTypeID(rs.getInt("TestTypeID"));
                TestType.setTestTypeName(rs.getString("TestTypeName"));
                TestTypes.add(TestType);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return TestTypes;
    }
    
     public TestType getTestTypeByID(int id) {
        try {
            String sql = "select * from TestType where TestTypeID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                TestType TestType = new TestType();
                TestType.setTestTypeID(rs.getInt("TestTypeID"));
                TestType.setOrder(rs.getInt("Order"));
                TestType.setStatus(rs.getBoolean("Status"));
                TestType.setTestTypeName(rs.getString("TestTypeName"));
                TestType.setType(rs.getString("type"));

                return TestType;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
