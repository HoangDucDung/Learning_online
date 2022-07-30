package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Dimension;
import model.DimensionType;
import model.Subject;

public class DimensionDAO extends DBContext {

    public ArrayList<Dimension> getDimensionsByCourseID(int id) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "SELECT DimensionID, Dimension.Name, Description, Dimension.TypeID, DimensionType.Name Type, CourseID\n"
                    + "FROM dbo.Dimension JOIN dbo.DimensionType ON DimensionType.TypeID = Dimension.TypeID\n"
                    + "WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setDimensionID(rs.getInt("DimensionID"));
                dimension.setName(rs.getString("Name"));
                dimension.setDescription(rs.getString("Description"));

                DimensionType dimensionType = new DimensionType();
                dimensionType.setTypeID(rs.getInt("TypeID"));
                dimensionType.setName(rs.getString("Type"));

                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));

                dimension.setTypeID(dimensionType);
                dimension.setCourseID(course);
                dimensions.add(dimension);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensions;
    }

    public Dimension getDimensionByID(int id) {
        try {
            String sql = "SELECT DimensionID, Dimension.Name, Description, Dimension.TypeID, DimensionType.Name Type, CourseID\n"
                    + "FROM dbo.Dimension JOIN dbo.DimensionType ON DimensionType.TypeID = Dimension.TypeID\n"
                    + "WHERE DimensionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setDimensionID(rs.getInt("DimensionID"));
                dimension.setName(rs.getString("Name"));
                dimension.setDescription(rs.getString("Description"));

                DimensionType dimensionType = new DimensionType();
                dimensionType.setTypeID(rs.getInt("TypeID"));
                dimensionType.setName(rs.getString("Type"));

                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));

                dimension.setTypeID(dimensionType);
                dimension.setCourseID(course);
                return dimension;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Dimension> getDimensionsByNameAndCourseID(String name, int id) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "SELECT DimensionID, Dimension.Name, Description, Dimension.TypeID, DimensionType.Name Type, CourseID\n"
                    + "FROM dbo.Dimension JOIN dbo.DimensionType ON DimensionType.TypeID = Dimension.TypeID\n"
                    + "WHERE CourseID = ? AND Dimension.Name LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setString(2, "%" + name + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setDimensionID(rs.getInt("DimensionID"));
                dimension.setName(rs.getString("Name"));
                dimension.setDescription(rs.getString("Description"));

                DimensionType dimensionType = new DimensionType();
                dimensionType.setTypeID(rs.getInt("TypeID"));
                dimensionType.setName(rs.getString("Type"));

                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));

                dimension.setTypeID(dimensionType);
                dimension.setCourseID(course);
                dimensions.add(dimension);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensions;
    }

    public void deleteDimension(int dimensionID) {
        try {
            String sql = "DELETE FROM [dbo].[Dimension] WHERE DimensionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, dimensionID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteDimensionByQuestion(int Qid) {
        try {
            String sql = "DELETE FROM [dbo].[Dimension] WHERE QuestionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, Qid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDimension(Dimension dimension) {
        try {
            String sql = "UPDATE [dbo].[Dimension]\n"
                    + "   SET [Name] = ?\n"
                    + "      ,[Description] = ?\n"
                    + "      ,[TypeID] = ?\n"
                    + " WHERE DimensionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, dimension.getName());
            stm.setString(2, dimension.getDescription());
            stm.setInt(3, dimension.getTypeID().getTypeID());
            stm.setInt(4, dimension.getDimensionID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertDimension(Dimension dimension) {
        try {
            String sql = "INSERT INTO [dbo].[Dimension]\n"
                    + "           ([Name]\n"
                    + "           ,[Description]\n"
                    + "           ,[TypeID]\n"
                    + "           ,[CourseID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, dimension.getName());
            stm.setString(2, dimension.getDescription());
            stm.setInt(3, dimension.getTypeID().getTypeID());
            stm.setInt(4, dimension.getCourseID().getCourseId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Dimension> getAllDimensions() {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "select d.Name, d.DimensionID, d.TypeID, d.QuestionID, dt.Name as NameType from Dimension d, DimensionType dt where d.TypeID = dt.TypeID";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setDimensionID(rs.getInt("DimensionID"));
                dimension.setName(rs.getString("Name"));
                dimension.setQuestionID(rs.getInt("QuestionID"));

                DimensionType dimensionType = new DimensionType();
                dimensionType.setTypeID(rs.getInt("TypeID"));
                dimensionType.setName(rs.getString("NameType"));
                
                dimension.setTypeID(dimensionType);
 
                dimensions.add(dimension);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensions;
    }
    public ArrayList<Dimension> getDimensions() {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            String sql = "  select distinct d.Name, d.TypeID from Dimension d, DimensionType dt where d.TypeID = dt.TypeID";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setName(rs.getString("Name"));

                DimensionType dimensionType = new DimensionType();
                dimensionType.setTypeID(rs.getInt("TypeID"));
                
                dimension.setTypeID(dimensionType);
 
                dimensions.add(dimension);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensions;
    }
}
