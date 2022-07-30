package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Course;
import model.CoursePricePackage;
import model.Subject;

public class CourseDAO extends DBContext {

    public Course mappingData(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setFirstName(rs.getString("FirstName"));
        account.setLastName(rs.getString("LastName"));
        account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

        Course course = new Course();
        course.setCourseId(rs.getInt("CourseID"));
        course.setName(rs.getString("Name"));
        course.setDescription(rs.getString("Description"));
        course.setInstructorId(account);
        course.setTinyPictureUrl(rs.getString("TinyPictureUrl"));
        course.setThumbnailUrl(rs.getString("ThumbnailUrl"));
        course.setCreatedDate(rs.getDate("CreatedDate"));
        course.setModifiedDate(rs.getDate("ModifiedDate"));
        course.setPrice(rs.getBigDecimal("Price"));
        course.setFeatured(rs.getBoolean("Featured"));
        course.setStatus(rs.getBoolean("Status"));

        return course;
    }

    public int getNumberAllLessonInCourse(int accountID, int courseID) {
        int numLesson = -1;
        try {
            String sql = "SELECT COUNT(*) as NumAllLesson FROM Lesson l JOIN Course c\n"
                    + "ON c.CourseID = l.CourseID JOIN CourseAccount ca\n"
                    + "ON ca.CourseID = c.CourseID \n"
                    + "WHERE ca.AccountID = ? AND c.CourseID = ? AND l.LessonTypeID = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                numLesson = rs.getInt("NumAllLesson");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numLesson;
    }

    public int getNumberLesson(int courseID) {
        int numLesson = -1;
        try {
            String sql = "SELECT COUNT(*) as NumAllLesson FROM Lesson l JOIN Course c\n"
                    + "ON c.CourseID = l.CourseID WHERE c.CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                numLesson = rs.getInt("NumAllLesson");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numLesson;
    }

    public int getNumberLessonLearning(int accountID, int courseID) {
        int numCurrentLesson = 0;
        try {
            String sql = "SELECT COUNT(*) as NumCurrentLesson FROM CompletedLesson cl JOIN Lesson l\n"
                    + "ON l.LessonID = cl.LessonID JOIN Course c\n"
                    + "ON c.CourseID = l.CourseID \n"
                    + "WHERE cl.AccountID = ? AND c.CourseID = ? AND l.lessonTypeID = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                numCurrentLesson = rs.getInt("NumCurrentLesson");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numCurrentLesson;
    }

    public Course getCourseByLessonID(int lessonID) {
        try {
            String sql = "select c.Name from lesson l join sublesson sl\n"
                    + "on l.SubLessonID = sl.SubLessonID join course c\n"
                    + "on c.CourseID = sl.CourseID where l.LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setName(rs.getString("Name"));
                return course;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Course> getAllCourseBySubjectID(int subjectID, int status) {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = "select c.*,a.FirstName, a.LastName, a.ProfilePictureUrl, a.Email from Course c, SubjectCourse sc, Account a "
                    + "where c.CourseID = sc.CourseID and a.AccountID = c.InstructorID and sc.SubjectID = ?";
            if (status != -1) {
                sql += " and c.[Status] = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectID);
            if (status != -1) {
                stm.setInt(2, status);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();

                c.setCourseId(rs.getInt("CourseID"));
                c.setName(rs.getString("Name"));
                c.setPrice(rs.getBigDecimal("Price"));
                c.setStatus(rs.getBoolean("Status"));

                Account ac = new Account();
                ac.setFirstName(rs.getString("FirstName"));
                ac.setLastName(rs.getString("LastName"));
                ac.setEmail(rs.getString("Email"));
                ac.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                c.setInstructorId(ac);

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Course> getAllCourse(int status) {
        ArrayList<Course> list = new ArrayList<>();
        try {
            String sql = " select c.*,a.FirstName, a.LastName, a.ProfilePictureUrl, a.Email from Course c, Account a "
                    + "where a.AccountID = c.InstructorID";
            if (status != -1) {
                sql += " and c.[Status] = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (status != -1) {
                stm.setInt(1, status);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();

                c.setCourseId(rs.getInt("CourseID"));
                c.setName(rs.getString("Name"));
                c.setPrice(rs.getBigDecimal("Price"));
                c.setStatus(rs.getBoolean("Status"));

                Account ac = new Account();
                ac.setFirstName(rs.getString("FirstName"));
                ac.setLastName(rs.getString("LastName"));
                ac.setEmail(rs.getString("Email"));
                ac.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                c.setInstructorId(ac);

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Course> getCourseByFilter(ArrayList<Integer> category, ArrayList<Integer> price, int status) {
        ArrayList<Course> list = new ArrayList<>();
        int countIndex = 1;
        try {
            String sql = " select c.*,a.FirstName, a.LastName, a.ProfilePictureUrl, a.Email from Course c, Account a "
                    + "where a.AccountID = c.InstructorID";
            if (!category.isEmpty()) {
                sql = "select c.*,a.FirstName, a.LastName, a.ProfilePictureUrl, a.Email from Course c, SubjectCourse sc, Account a "
                        + "where c.CourseID = sc.CourseID and a.AccountID = c.InstructorID";
                for (int i = 0; i < category.size(); i++) {
                    if (i == 0) {
                        sql += " and ( sc.SubjectID = ?";
                    } else {
                        sql += " or sc.SubjectID = ?";
                    }
                    if (i == category.size() - 1) {
                        sql += ")";
                    }
                }
            }
            if (!price.isEmpty()) {
                if (price.contains(0) && !price.contains(1)) {
                    sql += " and c.Price = 0";
                }
                if (price.contains(1) && !price.contains(0)) {
                    sql += " and c.Price != 0";
                }
            }
            if (status != -1) {
                sql += " and c.[Status] = ?";
            }
            System.out.println("sql: " + sql);
            PreparedStatement stm = connection.prepareStatement(sql);
            if (!category.isEmpty()) {
                for (int i = 0; i < category.size(); i++) {
                    stm.setInt(i + 1, category.get(i));
                    countIndex++;
                }
//                if (!price.isEmpty()) {
//                    for (int i = 0; i < price.size(); i++) {
//                        stm.setInt(i + category.size() + 1, price.get(i));
//                        countIndex++;
//                    }
//                }
            }
//            if (!price.isEmpty() && category.isEmpty()) {
//                for (int i = 0; i < price.size(); i++) {
//                    stm.setInt(i + 1, price.get(i));
//                    countIndex++;
//                }
//            }
            if (status != -1) {
                stm.setInt(countIndex, status);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();

                c.setCourseId(rs.getInt("CourseID"));
                c.setName(rs.getString("Name"));
                c.setPrice(rs.getBigDecimal("Price"));
                c.setStatus(rs.getBoolean("Status"));

                Account ac = new Account();
                ac.setFirstName(rs.getString("FirstName"));
                ac.setLastName(rs.getString("LastName"));
                ac.setEmail(rs.getString("Email"));
                ac.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));

                c.setInstructorId(ac);

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Course> getAllCourseNoCondition() {
        ArrayList<Course> listCourse = new ArrayList<>();
        try {
            String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl \n"
                    + "from Course c join account a\n"
                    + "on a.AccountID = c.InstructorID\n";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                int numberLesson = new CourseDAO().getNumberLesson(course.getCourseId());
                course.setNumberLesson(numberLesson);
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public ArrayList<Course> getAllCourse() {
        ArrayList<Course> listCourse = new ArrayList<>();
        try {
            String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl \n"
                    + "from Course c join account a\n"
                    + "on a.AccountID = c.InstructorID\n"
                    + "where c.[status] = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                int numberLesson = new CourseDAO().getNumberLesson(course.getCourseId());
                course.setNumberLesson(numberLesson);
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public int getStarOfCourse(int courseId) {
        try {
            String sql = "select sum(rating) / count(*) as Star \n"
                    + "from CourseAccount ca where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Star");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getNumberPeopleLearningInCourse(int courseId) {
        try {
            String sql = "select count(*) as Number \n"
                    + "from CourseAccount ca where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Number");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Course> getListCourseBySubject(ArrayList<Integer> listSearchId) {
        ArrayList<Course> listCourse = new ArrayList<>();

        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on c.InstructorID = a.AccountID\n"
                + "where c.CourseID in (\n"
                + "	select c.CourseID from Course c join account a\n"
                + "	on a.AccountID = c.InstructorID join SubjectCourse sc\n"
                + "	on sc.CourseID = c.CourseID join [Subject] s\n"
                + "	on s.SubjectID = sc.SubjectID join SubjectCategory scate\n"
                + "	on scate.CategoryID = s.CategoryID\n"
                + "	where c.[Status] = 1 \n";

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
        sql += "group by c.courseID)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < listSearchId.size(); i++) {
                ps.setInt(i + 1, listSearchId.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public ArrayList<Course> getListCourseByText(String txtSearch) {
        ArrayList<Course> listCourse = new ArrayList<>();

        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on a.AccountID = c.InstructorID\n"
                + "where c.[status] = 1 and c.name like ? ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public ArrayList<Course> getListCourseByPrice(ArrayList<String> listPrice) {
        ArrayList<Course> listCourse = new ArrayList<>();

        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on a.AccountID = c.InstructorID\n"
                + "where c.[status] = 1 ";
        if (listPrice.size() != 2) {
            if (listPrice.get(0).equalsIgnoreCase("Free")) {
                sql += " AND Price = 0 ";
            } else {
                sql += " AND Price > 0 ";
            }
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPricePackage = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPricePackage);
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public Course getSubjectByCourseID(int id) {
        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on a.AccountID = c.InstructorID\n"
                + "where courseID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course course = mappingData(rs);
                course.setVideoIntroduce(rs.getString("VideoIntroduce"));
                ArrayList<String> objectives = getObjectives(course.getCourseId());
                course.setObjectives(objectives);
                course.setAboutCourse(rs.getString("AboutCourse"));
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                ArrayList<Account> accounts = new AccountDAO().getListAccountCanAccessCourse(id);
                course.setListExpertCanAccess(accounts);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                return course;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Course getCourseByCourseId(int id) {
        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on a.AccountID = c.InstructorID\n"
                + "where c.[status] = 1 and courseID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course course = mappingData(rs);
                course.setVideoIntroduce(rs.getString("VideoIntroduce"));
                ArrayList<String> objectives = getObjectives(course.getCourseId());
                course.setObjectives(objectives);
                course.setAboutCourse(rs.getString("AboutCourse"));
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                return course;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isRegister(int accountID, int id) {
        String sql = "select * from courseaccount\n"
                + "where accountid = ? and CourseID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<String> getObjectives(int courseId) {
        ArrayList<String> list = new ArrayList<>();
        String sql = "select * from Objective where CourseID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("ObjectiveName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void deleteCourse(int id) {
        try {
            String sql = "DELETE from [Course] WHERE [CourseID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getlCourseIdsublessonID(int subLessonId) {
        Course c = new Course();
        try {
            String sql = "  select s.CourseID from SubLesson s where s.SubLessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, subLessonId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                c.setCourseId(rs.getInt("CourseID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c.getCourseId();
    }

    public ArrayList<Course> getTopFeatureCourse(int courseId) {
        ArrayList<Course> listCourse = new ArrayList<>();
        try {
            String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl from course c join account a  on\n"
                    + "c.instructorid = a.accountid\n"
                    + "where courseid != ? and courseid in (\n"
                    + "select distinct c.courseid from Course c join account a\n"
                    + "on a.AccountID = c.InstructorID\n"
                    + "join subjectcourse sc\n"
                    + "on sc.courseid = c.courseid\n"
                    + "where c.[status] = 1 and sc.subjectid in (\n"
                    + "	select subjectid from course c join subjectcourse sc\n"
                    + "	on c.courseid = sc.courseid\n"
                    + "	where c.courseid = ?\n"
                    + "))";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            stm.setInt(2, courseId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                listCourse.add(course);
            }

            Collections.sort(listCourse, new Comparator<Course>() {
                @Override
                public int compare(Course t, Course t1) {
                    if (t.getNumberPeopleLearning() < t1.getNumberPeopleLearning()) {
                        return 1;
                    } else if (t.getNumberPeopleLearning() == t1.getNumberPeopleLearning()) {
                        if (t.getStar() < t1.getStar()) {
                            return 1;
                        }
                    } else {
                        return 0;
                    }
                    return -1;
                }

            });
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        int count = 0;
        ArrayList<Course> listCourseFeature = new ArrayList<>();
        for (Course course : listCourse) {
            if (count < 3) {
                listCourseFeature.add(course);
                count++;
            }
        }
        return listCourseFeature;
    }

    public ArrayList<Course> getAllTopFeatureCourse() {
        ArrayList<Course> listCourse = getAllCourse();
        Collections.sort(listCourse, new Comparator<Course>() {
            @Override
            public int compare(Course t, Course t1) {
                if (t.getNumberPeopleLearning() < t1.getNumberPeopleLearning()) {
                    return 1;
                } else if (t.getNumberPeopleLearning() == t1.getNumberPeopleLearning()) {
                    if (t.getStar() < t1.getStar()) {
                        return 1;
                    }
                } else {
                    return 0;
                }
                return -1;
            }

        });
        int count = 0;
        ArrayList<Course> listCourseFeature = new ArrayList<>();
        for (Course course : listCourse) {
            if (count < 3) {
                listCourseFeature.add(course);
                count++;
            }
        }
        return listCourseFeature;
    }

    public ArrayList<Course> getCoursesByTxt(String txtSearch) {
        ArrayList<Course> listCourse = new ArrayList<>();

        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on a.AccountID = c.InstructorID\n"
                + "where c.name like ? ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                course.setNumberLesson(new CourseDAO().getNumberLesson(course.getCourseId()));
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public ArrayList<Course> getCoursesByStatus(String status) {
        ArrayList<Course> listCourse = new ArrayList<>();

        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on a.AccountID = c.InstructorID\n"
                + "where c.status = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (status.equalsIgnoreCase("true")) {
                ps.setInt(1, 1);
            } else {
                ps.setInt(1, 0);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                course.setNumberLesson(new CourseDAO().getNumberLesson(course.getCourseId()));
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public ArrayList<Course> getCoursesBySubject(ArrayList<Integer> listSearchId) {
        ArrayList<Course> listCourse = new ArrayList<>();

        String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl\n"
                + "from Course c join account a\n"
                + "on c.InstructorID = a.AccountID\n"
                + "where c.CourseID in (\n"
                + "	select c.CourseID from Course c join account a\n"
                + "	on a.AccountID = c.InstructorID join SubjectCourse sc\n"
                + "	on sc.CourseID = c.CourseID join [Subject] s\n"
                + "	on s.SubjectID = sc.SubjectID join SubjectCategory scate\n"
                + "	on scate.CategoryID = s.CategoryID\n";

        if (listSearchId.size() == 1) {
            sql += " Where s.SubjectID = ? )";
        } else if (listSearchId.size() >= 2) {
            for (int i = 0; i < listSearchId.size(); i++) {
                if (i == 0) {
                    sql += " Where s.SubjectID = ? ";
                } else if (i == listSearchId.size() - 1) {
                    sql += " OR s.SubjectID = ? )";
                } else {
                    sql += " OR s.SubjectID = ? ";
                }
            }
        }
        //sql += "group by c.courseID)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < listSearchId.size(); i++) {
                ps.setInt(i + 1, listSearchId.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                course.setNumberLesson(new CourseDAO().getNumberLesson(course.getCourseId()));
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public ArrayList<Course> getAllCourseForManagement() {
        ArrayList<Course> listCourse = new ArrayList<>();
        try {
            String sql = "select c.*, a.FirstName, a.LastName, a.ProfilePictureUrl \n"
                    + "from Course c join account a\n"
                    + "on a.AccountID = c.InstructorID\n";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = mappingData(rs);
                ArrayList<Subject> listSubject = new SubjectDAO().getSubjectsByCourseID(course.getCourseId());
                course.setListSubject(listSubject);
                int star = getStarOfCourse(course.getCourseId());
                course.setStar(star);
                int people = getNumberPeopleLearningInCourse(course.getCourseId());
                course.setNumberPeopleLearning(people);
                ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(course.getCourseId());
                course.setListPrice(listPrice);
                int numberLesson = new CourseDAO().getNumberLesson(course.getCourseId());
                course.setNumberLesson(numberLesson);
                listCourse.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCourse;
    }

    public Course getNewestCourse() {
        int id = 0;
        try {
            String sql = "SELECT MAX(CourseID) CourseID FROM Course";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("CourseID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getCourseByCourseId(id);
    }

    public void insertCourse(Course course) {
        try {
            String sql = "INSERT INTO [dbo].[Course]\n"
                    + "           ([Name]\n"
                    + "           ,[Description]\n"
                    + "           ,[InstructorID]\n"
                    + "           ,[TinyPictureUrl]\n"
                    + "           ,[ThumbnailUrl]\n"
                    + "           ,[CreatedDate]\n"
                    + "           ,[ModifiedDate]\n"
                    + "           ,[Price]\n"
                    + "           ,[Featured]\n"
                    + "           ,[Status]\n"
                    + "           ,[VideoIntroduce]\n"
                    + "           ,[AboutCourse])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, course.getName());
            stm.setString(2, course.getDescription());
            stm.setInt(3, course.getInstructorId().getId());
            stm.setString(4, course.getThumbnailUrl());
            stm.setString(5, course.getThumbnailUrl());
            stm.setDate(6, course.getCreatedDate());
            stm.setDate(7, null);
            stm.setBigDecimal(8, BigDecimal.ZERO);
            stm.setBoolean(9, course.isFeatured());
            stm.setBoolean(10, course.isStatus());
            stm.setString(11, course.getVideoIntroduce());
            stm.setString(12, course.getAboutCourse());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertCourseToCategory(ArrayList<Integer> subjectID, int courseID) {
        try {
            String sql = "INSERT INTO [dbo].[SubjectCourse]\n"
                    + "           ([SubjectID]\n"
                    + "           ,[CourseID])\n"
                    + "     VALUES\n";
            for (int i = 0; i < subjectID.size(); i++) {
                if (i == subjectID.size() - 1) {
                    sql += "           (?\n"
                            + "           ,?)";
                } else {
                    sql += "           (?\n"
                            + "           ,?),";
                }
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            int index = 1;
            for (int i = 0; i < subjectID.size(); i++) {
                int get = subjectID.get(i);
                stm.setInt(index, get);
                index++;
                stm.setInt(index, courseID);
                index++;
            }
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCategoryOfCourse(int id) {
        try {
            String sql = "DELETE FROM [dbo].[SubjectCourse]\n"
                    + "      WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertObjectives(String[] objectives, int id) {
        try {
            String sql = "INSERT INTO [dbo].[Objective]\n"
                    + "           ([ObjectiveName]\n"
                    + "           ,[CourseID])\n"
                    + "     VALUES\n";
            for (int i = 0; i < objectives.length; i++) {
                if (i == objectives.length - 1) {
                    sql += "           (?\n"
                            + "           ,?)";
                } else {
                    sql += "           (?\n"
                            + "           ,?),";
                }
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            int index = 1;
            for (int i = 0; i < objectives.length; i++) {
                stm.setString(index, objectives[i]);
                index++;
                stm.setInt(index, id);
                index++;
            }
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteObjectives(int id) {
        try {
            String sql = "DELETE FROM [dbo].[Objective]\n"
                    + "      WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCourse(Course course) {
        try {
            String sql = "UPDATE [dbo].[Course]\n"
                    + "   SET [Name] = ?\n"
                    + "      ,[Description] = ?\n"
                    + "      ,[TinyPictureUrl] = ?\n"
                    + "      ,[ThumbnailUrl] = ?\n"
                    + "      ,[ModifiedDate] = ?\n"
                    + "      ,[Price] = ?\n"
                    + "      ,[Featured] = ?\n"
                    + "      ,[Status] = ?\n"
                    + "      ,[VideoIntroduce] = ?\n"
                    + "      ,[AboutCourse] = ?\n"
                    + " WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, course.getName());
            stm.setString(2, course.getDescription());
            stm.setString(3, course.getThumbnailUrl());
            stm.setString(4, course.getThumbnailUrl());
            stm.setDate(5, course.getModifiedDate());
            stm.setBigDecimal(6, BigDecimal.ZERO);
            stm.setBoolean(7, course.isFeatured());
            stm.setBoolean(8, course.isStatus());
            stm.setString(9, course.getVideoIntroduce());
            stm.setString(10, course.getAboutCourse());
            stm.setInt(11, course.getCourseId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Integer> getListCourseIdCanAccess(int expertID) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CourseExpert WHERE AccountID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, expertID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("CourseID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
