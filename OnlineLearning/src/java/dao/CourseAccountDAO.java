package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Course;
import model.CourseAccount;

public class CourseAccountDAO extends DBContext {

    public ArrayList<CourseAccount> getBestRatedCourses() {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();
        try {
            String sql = "SELECT AccountID, FirstName, LastName, ProfilePictureUrl,\n"
                    + "c.CourseID, c.Name CourseName, c.Description, c.TinyPictureUrl,\n"
                    + "c.ThumbnailUrl, c.CreatedDate, c.ModifiedDate, c.Price, c.Status, ca.[Total Rate] / People as Star\n"
                    + "FROM dbo.Course c JOIN (SELECT TOP 3 coac.[CourseID], \n"
                    + "SUM(Rating) [Total Rate], COUNT(AccountID) People FROM [CourseAccount] coac join Course co\n"
                    + "on coac.CourseID = co.CourseID where co.Status = 1\n"
                    + "GROUP BY coac.CourseID ORDER BY [total rate] DESC ) ca ON ca.CourseID = c.CourseID\n"
                    + "JOIN dbo.Account ON Account.AccountID = c.InstructorID WHERE [Status] = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account instructor = new Account();
                instructor.setAccountID(rs.getInt("AccountID"));
                instructor.setFirstName(rs.getString("FirstName"));
                instructor.setLastName(rs.getString("LastName"));
                instructor.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                course.setName(rs.getString("CourseName"));
                course.setDescription(rs.getString("Description"));
                course.setInstructorId(instructor);
                
                course.setTinyPictureUrl(rs.getString("TinyPictureUrl"));
                course.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                course.setCreatedDate(rs.getDate("CreatedDate"));
                course.setModifiedDate(rs.getDate("ModifiedDate"));
                course.setPrice(rs.getBigDecimal("Price"));
                course.setStatus(rs.getBoolean("Status"));

                CourseAccount courseAccount = new CourseAccount();
                courseAccount.setCourseId(course);
                courseAccount.setRating(rs.getInt("Star"));

                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }
}
