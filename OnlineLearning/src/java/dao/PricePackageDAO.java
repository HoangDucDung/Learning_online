package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.CoursePricePackage;

public class PricePackageDAO extends DBContext {
    
    public ArrayList<CoursePricePackage> getAllPricePackages(int courseID) {
        ArrayList<CoursePricePackage> pricePackages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [CoursePricePackage] WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CoursePricePackage pricePackage = new CoursePricePackage();
                pricePackage.setPriceId(rs.getInt("PriceID"));
                pricePackage.setName(rs.getString("Name"));
                if (rs.getObject("AccessDuration") != null) {
                    pricePackage.setAccessDuration(rs.getInt("AccessDuration"));
                } else {
                    pricePackage.setAccessDuration(-1);
                }
                pricePackage.setStatus(rs.getBoolean("Status"));
                pricePackage.setListPrice(rs.getBigDecimal("ListPrice"));
                pricePackage.setSalePrice(rs.getBigDecimal("SalePrice"));
                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                pricePackage.setCourseId(course);
                pricePackages.add(pricePackage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pricePackages;
    }
    
    public CoursePricePackage getPricePackageByID(int id) {
        try {
            String sql = "SELECT * FROM [CoursePricePackage] WHERE PriceID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                CoursePricePackage pricePackage = new CoursePricePackage();
                pricePackage.setPriceId(rs.getInt("PriceID"));
                pricePackage.setName(rs.getString("Name"));
                if (rs.getObject("AccessDuration") != null) {
                    pricePackage.setAccessDuration(rs.getInt("AccessDuration"));
                } else {
                    pricePackage.setAccessDuration(-1);
                }
                pricePackage.setStatus(rs.getBoolean("Status"));
                pricePackage.setListPrice(rs.getBigDecimal("ListPrice"));
                pricePackage.setSalePrice(rs.getBigDecimal("SalePrice"));
                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                pricePackage.setCourseId(course);
                return pricePackage;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updateStatusPricePackage(int id, boolean status) {
        try {
            String sql = "UPDATE [CoursePricePackage] SET [Status] = ? WHERE PriceID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, status);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePricePackage(int id) {
        try {
            String sql = "DELETE FROM [CoursePricePackage] WHERE PriceID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertPricePackage(CoursePricePackage pricePackage) {
        try {
            String sql = "INSERT INTO [CoursePricePackage]\n"
                    + "           ([Name]\n"
                    + "           ,[AccessDuration]\n"
                    + "           ,[Status]\n"
                    + "           ,[ListPrice]\n"
                    + "           ,[SalePrice]\n"
                    + "           ,[CourseID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, pricePackage.getName());
            if (pricePackage.getAccessDuration() == -1) {
                stm.setNull(2, Types.INTEGER);
            } else {
                stm.setInt(2, pricePackage.getAccessDuration());
            }
            stm.setBoolean(3, pricePackage.isStatus());
            stm.setBigDecimal(4, pricePackage.getListPrice());
            stm.setBigDecimal(5, pricePackage.getSalePrice());
            stm.setInt(6, pricePackage.getCourseId().getCourseId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updatePricePackage(CoursePricePackage pricePackage) {
        try {
            String sql = "UPDATE [dbo].[CoursePricePackage]\n"
                    + "   SET [Name] = ?\n"
                    + "      ,[AccessDuration] = ?\n"
                    + "      ,[Status] = ?\n"
                    + "      ,[ListPrice] = ?\n"
                    + "      ,[SalePrice] = ?\n"
                    + "      ,[CourseID] = ?\n"
                    + " WHERE PriceID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, pricePackage.getName());
            if (pricePackage.getAccessDuration() == -1) {
                stm.setNull(2, Types.INTEGER);
            } else {
                stm.setInt(2, pricePackage.getAccessDuration());
            }
            stm.setBoolean(3, pricePackage.isStatus());
            stm.setBigDecimal(4, pricePackage.getListPrice());
            stm.setBigDecimal(5, pricePackage.getSalePrice());
            stm.setInt(6, pricePackage.getCourseId().getCourseId());
            stm.setInt(7, pricePackage.getPriceId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<CoursePricePackage> getListPricePackageOfCourse(int courseId) {
        ArrayList<CoursePricePackage> listPriceOfCourse = new ArrayList<>();
        try {
            String sql = "select * from coursepricepackage where courseid = ?";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CoursePricePackage coursePricePackage = new CoursePricePackage();
                coursePricePackage.setPriceId(rs.getInt("PriceID"));
                coursePricePackage.setName(rs.getString("Name"));
                if (rs.getObject("AccessDuration") != null) {
                    coursePricePackage.setAccessDuration(rs.getInt("AccessDuration"));
                } else {
                    coursePricePackage.setAccessDuration(-1);
                }
                coursePricePackage.setStatus(rs.getBoolean("Status"));
                coursePricePackage.setListPrice(rs.getBigDecimal("ListPrice"));
                coursePricePackage.setSalePrice(rs.getBigDecimal("SalePrice"));
                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                coursePricePackage.setCourseId(course);
                
                listPriceOfCourse.add(coursePricePackage);
            }
            return listPriceOfCourse;
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public CoursePricePackage getCoursePricePackageByID(int id) {
        try {
            String sql = "SELECT * FROM [CoursePricePackage] WHERE PriceID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                CoursePricePackage pricePackage = new CoursePricePackage();
                pricePackage.setPriceId(rs.getInt("PriceID"));
                pricePackage.setName(rs.getString("Name"));
                if (rs.getObject("AccessDuration") != null) {
                    pricePackage.setAccessDuration(rs.getInt("AccessDuration"));
                } else {
                    pricePackage.setAccessDuration(-1);
                }
                pricePackage.setStatus(rs.getBoolean("Status"));
                pricePackage.setListPrice(rs.getBigDecimal("ListPrice"));
                pricePackage.setSalePrice(rs.getBigDecimal("SalePrice"));
                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                pricePackage.setCourseId(course);
                return pricePackage;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PricePackageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
