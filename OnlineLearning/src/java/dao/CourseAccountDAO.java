package dao;

import java.sql.Date;
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

    public CourseAccount mappingData(ResultSet rs) throws SQLException {
        Account user = new Account();
        user.setAccountID(rs.getInt("AccountID"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

        Account instructor = new Account();
        instructor.setAccountID(rs.getInt("AccountIDIns"));
        instructor.setFirstName(rs.getString("FirstNameIns"));
        instructor.setLastName(rs.getString("LastNameIns"));
        instructor.setProfilePictureUrl(rs.getString("ProfilePictureUrlIns"));

        Course course = new Course();
        course.setCourseId(rs.getInt("CourseID"));
        course.setName(rs.getString("Name"));
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
        courseAccount.setAccountId(user);
        courseAccount.setEnrollDate(rs.getDate("EnrollDate"));
        courseAccount.setRating(rs.getInt("Rating"));
        courseAccount.setProgress(rs.getInt("Progress"));
        return courseAccount;
    }

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
                    + "JOIN dbo.Account ON Account.AccountID = c.InstructorID WHERE c.[Status] = 1";
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

    public ArrayList<CourseAccount> getListCourseAccount(int accountID) {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();
        try {
            String sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CourseAccount courseAccount = mappingData(rs);
                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }

    public boolean isRegisterCourse(int accountID, int courseID) {
        try {
            String sql = "SELECT * FROM CourseAccount \n"
                    + "WHERE AccountID = ? AND CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void updateProgress(int accountID, int courseID, int progress) {
        try {
            String sql = "UPDATE [CourseAccount]\n"
                    + "   SET [Progress] = ?\n"
                    + " WHERE AccountID = ? AND CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, progress);
            stm.setInt(2, accountID);
            stm.setInt(3, courseID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<CourseAccount> getListCourseByProgress(int accountID, int progress) {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();

        String sql = "";
        if (progress == 0) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] < 100";
        } else {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] >= 100";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CourseAccount courseAccount = mappingData(rs);
                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }

    public ArrayList<CourseAccount> getListCourseAccountByDateAndProgress(int accountID, Date dateEnroll, int progress) {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();

        String sql = "";
        if (progress == -1) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.EnrollDate = ?";
        } else if (progress == 0) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] < 100 AND ca.EnrollDate = ?";
        } else {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] >= 100 AND ca.EnrollDate = ?";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setDate(2, dateEnroll);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CourseAccount courseAccount = mappingData(rs);
                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }

    public ArrayList<CourseAccount> getListCourseByTextAndProgress(int accountID, String txtSearch, int progress) {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();

        String sql = "";
        if (progress == -1) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + " AND c.Name LIKE ?";
        } else if (progress == 0) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] < 100 AND c.Name LIKE ?";
        } else {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] >= 100 AND c.Name LIKE ?";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setString(2, "%" + txtSearch + "%");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CourseAccount courseAccount = mappingData(rs);
                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }

    public ArrayList<CourseAccount> getListCourseAccountByDateAndProgressAndText(int accountID, Date dateEnroll, int progress, String txtSearch) {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();

        String sql = "";
        if (progress == -1) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + " AND c.Name LIKE ? AND ca.EnrollDate = ?";
        } else if (progress == 0) {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] < 100 AND c.Name LIKE ? AND ca.EnrollDate = ?";
        } else {
            sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                    + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                    + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                    + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                    + "from courseaccount ca join account a \n"
                    + "on ca.AccountID = a.AccountID join Course c\n"
                    + "on c.CourseID = ca.CourseID \n"
                    + "join account ins \n"
                    + "on ins.AccountID = c.InstructorID\n"
                    + "where a.AccountID = ? and c.Status = 1 \n"
                    + "and ca.[progress] >= 100 AND c.Name LIKE ? AND ca.EnrollDate = ?";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, accountID);
            stm.setString(2, "%" + txtSearch + "%");
            stm.setDate(3, dateEnroll);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CourseAccount courseAccount = mappingData(rs);
                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }

    public void unenrollCourse(int accountID, int courseId) {
        try {
            String sql = "Delete from CourseAccount "
                    + "Where accountid = ? and courseid = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ps.setInt(2, courseId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<CourseAccount> getListCourseAccountByCategory(int accountID, ArrayList<Integer> listSearchId) {
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();

        String sql = "select a.AccountID, a.FirstName, a.LastName, a.ProfilePictureUrl, \n"
                + "ins.FirstName as FirstNameIns, ins.LastName as LastNameIns, \n"
                + "ins.AccountID as AccountIDIns, ins.ProfilePictureUrl as ProfilePictureUrlIns, \n"
                + "c.*, ca.EnrollDate, ca.Rating, ca.Progress  \n"
                + "from courseaccount ca join account a \n"
                + "on ca.AccountID = a.AccountID join Course c\n"
                + "on c.CourseID = ca.CourseID \n"
                + "join account ins on ins.AccountID = c.InstructorID\n"
                + "where a.AccountID = ? and c.Status = 1 \n"
                + "and c.CourseID in (\n"
                + "	select c.CourseID from courseaccount ca join account a \n"
                + "	on ca.AccountID = a.AccountID join Course c\n"
                + "	on c.CourseID = ca.CourseID \n"
                + "	join account ins on ins.AccountID = c.InstructorID\n"
                + "	join SubjectCourse sc on sc.CourseID = c.CourseID\n"
                + "	join [Subject] s on s.SubjectID = sc.SubjectID\n"
                + "	join SubjectCategory sca on sca.CategoryID = s.CategoryID\n"
                + "	where a.AccountID = ? and c.Status = 1 \n";

        if (listSearchId.size() == 1) {
            sql += " AND s.SubjectID = ? ";
        } else if (listSearchId.size() >= 2) {
            for (int i = 0; i < listSearchId.size(); i++) {
                if (i == 0) {
                    sql += " AND ( s.SubjectID = ? ";
                } else if (i == listSearchId.size() - 1) {
                    sql += " OR s.SubjectID = ? )";
                } else {
                    sql += " OR s.SubjectID = ? ";
                }
            }
        }
        sql += "	group by c.CourseID) ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ps.setInt(2, accountID);

            for (int i = 0; i < listSearchId.size(); i++) {
                ps.setInt(i + 3, listSearchId.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseAccount courseAccount = mappingData(rs);

                listCourseAccount.add(courseAccount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourseAccount;
    }

    public void votedCourse(int accountID, int courseId, int star) {
        try {
            String sql = "UPDATE [CourseAccount]\n"
                    + "SET [Rating] = ?\n"
                    + "WHERE AccountID = ? AND CourseID = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, star);
            ps.setInt(2, accountID);
            ps.setInt(3, courseId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertCourseAccount(CourseAccount courseAccount) {
        try {
            String sql = "INSERT INTO [dbo].[CourseAccount]\n"
                    + "           ([AccountID]\n"
                    + "           ,[CourseID]\n"
                    + "           ,[EnrollDate]\n"
                    + "           ,[Progress])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,0)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseAccount.getAccountId().getAccountID());
            ps.setInt(2, courseAccount.getCourseId().getCourseId());
            ps.setDate(3, courseAccount.getEnrollDate());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
