package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Subject;
import model.SubjectCategory;

public class SubjectDAO extends DBContext {

    public Subject mappingData(ResultSet rs) throws SQLException {
        SubjectCategory sc = new SubjectCategory();
        sc.setCategoryID(rs.getInt("CategoryID"));

        Subject subject = new Subject();
        subject.setSubjectId(rs.getInt("SubjectID"));
        subject.setName(rs.getString("Name"));
        subject.setCategoryID(sc);
        subject.setOrder(rs.getInt("Order"));
        subject.setType(rs.getString("type"));

        return subject;
    }

    public ArrayList<Subject> getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Subject";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("SubjectID"));
                subject.setName(rs.getString("Name"));

                SubjectCategory category = new SubjectCategory();
                category.setCategoryID(rs.getInt("CategoryID"));
                subject.setCategoryID(category);

                subject.setOrder(rs.getInt("Order"));
                subject.setType(rs.getString("type"));
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

//    public ArrayList<Subject> getSubjectsByCategoryAndStatus(String[] categoryID, String[] mainCategoryID, String status) {
//        ArrayList<Subject> subjects = new ArrayList<>();
//        try {
//            String sql = "SELECT Subject.*, Account.FirstName, Account.LastName, SubjectCategory.Name CategoryName, SubjectMainCategory.Name MainCategoryName\n"
//                    + "FROM [Subject] JOIN Account ON Subject.OwnerID = Account.AccountID\n"
//                    + "LEFT JOIN SubjectCategory ON Subject.CategoryID = SubjectCategory.CategoryID\n"
//                    + "LEFT JOIN SubjectMainCategory ON Subject.MainCategoryID = SubjectMainCategory.MainCategoryID\n";
//            if ((categoryID != null || mainCategoryID != null) && status == null) {
//                sql += "WHERE ";
//                if (categoryID != null) {
//                    for (int i = 0; i < categoryID.length; i++) {
//                        if (mainCategoryID == null) {
//                            if (i == categoryID.length - 1) {
//                                sql += "Subject.CategoryID = ? ";
//                            } else {
//                                sql += "Subject.CategoryID = ? OR ";
//                            }
//                        } else {
//                            sql += "Subject.CategoryID = ? OR ";
//                        }
//                    }
//                }
//                if (mainCategoryID != null) {
//                    for (int i = 0; i < mainCategoryID.length; i++) {
//                        if (i == mainCategoryID.length - 1) {
//                            sql += "Subject.MainCategoryID = ? ";
//                        } else {
//                            sql += "Subject.MainCategoryID = ? OR ";
//                        }
//                    }
//                }
//            } else if ((categoryID != null || mainCategoryID != null) && status != null) {
//                sql += "WHERE (";
//                if (categoryID != null) {
//                    for (int i = 0; i < categoryID.length; i++) {
//                        if (mainCategoryID == null) {
//                            if (i == categoryID.length - 1) {
//                                sql += "Subject.CategoryID = ? ";
//                            } else {
//                                sql += "Subject.CategoryID = ? OR ";
//                            }
//                        } else {
//                            sql += "Subject.CategoryID = ? OR ";
//                        }
//                    }
//                }
//                if (mainCategoryID != null) {
//                    for (int i = 0; i < mainCategoryID.length; i++) {
//                        if (i == mainCategoryID.length - 1) {
//                            sql += "Subject.MainCategoryID = ? ";
//                        } else {
//                            sql += "Subject.MainCategoryID = ? OR ";
//                        }
//                    }
//                }
//                sql += ") AND Subject.Status = ?\n";
//            } else if (categoryID == null && mainCategoryID == null && status != null) {
//                sql += "WHERE Subject.Status = ?\n";
//            }
//            sql += "ORDER BY SubjectID DESC";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            if ((categoryID != null || mainCategoryID != null) && status == null) {
//                int index = 1;
//                if (categoryID != null) {
//                    for (int i = 0; i < categoryID.length; i++) {
//                        stm.setInt(index, Integer.parseInt(categoryID[i]));
//                        index++;
//                    }
//                }
//                if (mainCategoryID != null) {
//                    for (int i = 0; i < mainCategoryID.length; i++) {
//                        stm.setInt(index, Integer.parseInt(mainCategoryID[i]));
//                        index++;
//                    }
//                }
//            } else if ((categoryID != null || mainCategoryID != null) && status != null) {
//                int index = 1;
//                if (categoryID != null) {
//                    for (int i = 0; i < categoryID.length; i++) {
//                        stm.setInt(index, Integer.parseInt(categoryID[i]));
//                        index++;
//                    }
//                }
//                if (mainCategoryID != null) {
//                    for (int i = 0; i < mainCategoryID.length; i++) {
//                        stm.setInt(index, Integer.parseInt(mainCategoryID[i]));
//                        index++;
//                    }
//                }
//                stm.setBoolean(index, Boolean.parseBoolean(status));
//            } else if (categoryID == null && mainCategoryID == null && status != null) {
//                stm.setBoolean(1, Boolean.parseBoolean(status));
//            }
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Subject subject = new Subject();
//                subject.setSubjectId(rs.getInt("SubjectID"));
//                subject.setName(rs.getString("Name"));
//
//                SubjectCategory category = new SubjectCategory();
//                category.setCategoryID(rs.getInt("CategoryID"));
//                category.setName(rs.getString("CategoryName"));
//
//                SubjectMainCategory mainCategory = new SubjectMainCategory();
//                mainCategory.setMainCategoryID(rs.getInt("MainCategoryID"));
//                mainCategory.setName(rs.getString("MainCategoryName"));
//
//                subject.setMainCategoryID(mainCategory);
//                subject.setCategoryID(category);
//                subject.setFeatured(rs.getBoolean("Featured"));
//                subject.setStatus(rs.getBoolean("Status"));
//                subject.setImage(rs.getString("Image"));
//                subject.setDescription(rs.getString("Description"));
//
//                Account account = new Account();
//                account.setAccountID(rs.getInt("OwnerID"));
//                account.setFirstName(rs.getString("FirstName"));
//                account.setLastName(rs.getString("LastName"));
//                subject.setOwnerID(account);
//
//                subject.setOrder(rs.getInt("Order"));
//                subject.setType(rs.getString("type"));
//                subjects.add(subject);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return subjects;
//    }
//    public ArrayList<Integer> countCourseForAllSubject(ArrayList<Subject> subjects) {
//        ArrayList<Integer> numberCourse = new ArrayList<>();
//        try {
//            String sql = "SELECT Subject.SubjectID, COUNT(CourseID) NumberCourse "
//                    + "FROM [Subject] LEFT JOIN SubjectCourse ON Subject.SubjectID = SubjectCourse.SubjectID "
//                    + "GROUP BY Subject.SubjectID ";
//            if (subjects.size() != 0) {
//                sql += "HAVING ";
//            }
//            for (int i = 0; i < subjects.size(); i++) {
//                if (i == subjects.size() - 1) {
//                    sql += "Subject.SubjectID = ? ";
//                } else {
//                    sql += "Subject.SubjectID = ? OR ";
//                }
//            }
//            sql += "ORDER BY Subject.SubjectID DESC";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            for (int i = 0; i < subjects.size();) {
//                Subject get = subjects.get(i);
//                stm.setInt(i + 1, get.getSubjectId());
//                i++;
//            }
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                numberCourse.add(rs.getInt("NumberCourse"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return numberCourse;
//    }
    public void insertSubject(Subject s) {
        try {
            String sql = "INSERT INTO [Subject]\n"
                    + "           ([Name]\n"
                    + "           ,[CategoryID]\n"
                    + "           ,[Order]\n"
                    + "           ,[type]\n"
                    + "           ,[Status])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)\n";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, s.getName());
            stm.setInt(2, s.getCategoryID().getCategoryID());
            stm.setInt(3, s.getOrder());
            stm.setString(4, "SUBJECT");
            stm.setBoolean(5, s.isStatus());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSubject(Subject subject) {
        try {
            String sql = "UPDATE [Subject]\n"
                    + "   SET [Name] = ?\n"
                    + "      ,[Order] = ?\n"
                    + "      ,[type] = ?\n"
                    + "      ,[Status] = ?\n"
                    + " WHERE [SubjectID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, subject.getName());
            stm.setInt(2, subject.getOrder());
            stm.setString(3, "SUBJECT");
            stm.setBoolean(4, subject.isStatus());
            stm.setInt(5, subject.getSubjectId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSubject(int id) {
        try {
            String sql = "DELETE Subject WHERE SubjectID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Subject getSubjectLast() {
        try {
            String sql = "SELECT * FROM Subject WHERE SubjectID = (SELECT MAX(SubjectID) FROM Subject)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Subject subject = mappingData(rs);
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Subject getSubjectByID(int id) {
        try {
            String sql = "SELECT Subject.*, SubjectCategory.Name CategoryName "
                    + "FROM Subject JOIN SubjectCategory ON Subject.CategoryID = SubjectCategory.CategoryID "
                    + "WHERE Subject.SubjectID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            if(id != 0) stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("SubjectID"));
                subject.setName(rs.getString("Name"));

                SubjectCategory category = new SubjectCategory();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setName(rs.getString("CategoryName"));
                subject.setCategoryID(category);
                
                subject.setOrder(rs.getInt("Order"));
                subject.setType(rs.getString("type"));
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Subject> getSubjectsByCourseID(int courseID) {
        ArrayList<Subject> listSubject = new ArrayList<>();
        try {
            String sql = "select * from [subject] s join SubjectCourse sc\n"
                    + "on sc.SubjectID = s.SubjectID where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = mappingData(rs);
                listSubject.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSubject;
    }

    public Subject getSubjectNamebyID(int subjectID) {
        try {
            String sql = "select Subject.Name as SubjectName\n"
                    + "from Subject where Subject.SubjectID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setName(rs.getString("SubjectName"));
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Subject> getAllSubjectName() {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            String sql = "select SubjectID, Subject.Name as SubjectName\n"
                    + "from Subject ";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("SubjectID"));
                subject.setName(rs.getString("SubjectName"));
                subjects.add(subject);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }
}
