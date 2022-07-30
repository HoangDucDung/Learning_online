package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Lesson;
import model.LessonType;
import model.SubLesson;

public class LessonDAO extends DBContext {

    public ArrayList<Lesson> getListLessonById(int id) {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "select l.StartLearningTime, l.LessonID, l.Name as LessonName, l.CreatedTime, \n"
                    + "l.UpdatedTime, l. WideImageUrl, l.TinyImageUrl, l.[Order], \n"
                    + "l.VideoUrl, l.[Status], sl.SubLessonID, sl.Name as SubLessonName,\n"
                    + "lt.LessonTypeID, lt.Name as LessonTypeName, c.*\n"
                    + "from Lesson l join SubLesson sl\n"
                    + "on l.SubLessonID = sl.SubLessonID\n"
                    + "join LessonType lt \n"
                    + "on lt.LessonTypeID = l.LessonTypeID\n"
                    + "join Course c on c.CourseID = l.CourseID\n"
                    + "where l.CourseID = ? and c.[Status] = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                course.setName(rs.getString("Name"));
                course.setDescription(rs.getString("Description"));
                course.setTinyPictureUrl(rs.getString("TinyPictureUrl"));
                course.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                course.setCreatedDate(rs.getDate("CreatedDate"));
                course.setModifiedDate(rs.getDate("ModifiedDate"));
                course.setPrice(rs.getBigDecimal("Price"));
                course.setStatus(rs.getBoolean("Status"));

                LessonType type = new LessonType();
                type.setLessonTypeID(rs.getInt("LessonTypeID"));
                type.setName(rs.getString("LessonTypeName"));

                SubLesson sub = new SubLesson();
                sub.setSubLessonID(rs.getInt("SubLessonID"));
                sub.setCourseID(course);
                sub.setName(rs.getString("SubLessonName"));

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setCourseID(course);
                lesson.setLessonTypeID(type);
                lesson.setName(rs.getString("LessonName"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setSubLessonID(sub);
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                list.add(lesson);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Lesson> getListLessonByCId(int id) {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "select l.StartLearningTime, l.LessonID, l.Name as LessonName, l.CreatedTime, \n"
                    + "l.UpdatedTime, l. WideImageUrl, l.TinyImageUrl, l.[Order], \n"
                    + "l.VideoUrl, l.[Status], sl.SubLessonID, sl.Name as SubLessonName,\n"
                    + "lt.LessonTypeID, lt.Name as LessonTypeName, c.*\n"
                    + "from Lesson l join SubLesson sl\n"
                    + "on l.SubLessonID = sl.SubLessonID\n"
                    + "join LessonType lt \n"
                    + "on lt.LessonTypeID = l.LessonTypeID\n"
                    + "join Course c on c.CourseID = l.CourseID\n"
                    + "where l.CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                course.setName(rs.getString("Name"));
                course.setDescription(rs.getString("Description"));
                course.setTinyPictureUrl(rs.getString("TinyPictureUrl"));
                course.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                course.setCreatedDate(rs.getDate("CreatedDate"));
                course.setModifiedDate(rs.getDate("ModifiedDate"));
                course.setPrice(rs.getBigDecimal("Price"));
                course.setStatus(rs.getBoolean("Status"));

                LessonType type = new LessonType();
                type.setLessonTypeID(rs.getInt("LessonTypeID"));
                type.setName(rs.getString("LessonTypeName"));

                SubLesson sub = new SubLesson();
                sub.setSubLessonID(rs.getInt("SubLessonID"));
                sub.setCourseID(course);
                sub.setName(rs.getString("SubLessonName"));

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setCourseID(course);
                lesson.setLessonTypeID(type);
                lesson.setName(rs.getString("LessonName"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setSubLessonID(sub);
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                list.add(lesson);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    ArrayList<Lesson> getListLessonBySubLessonID(int subLesson) {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "select l.StartLearningTime, l.LessonID, l.Name as LessonName, l.CreatedTime, \n"
                    + "l.UpdatedTime, l. WideImageUrl, l.TinyImageUrl, l.[Order], \n"
                    + "l.VideoUrl, l.[Status], sl.SubLessonID, sl.Name as SubLessonName,\n"
                    + "lt.LessonTypeID, lt.Name as LessonTypeName, c.*\n"
                    + "from Lesson l join SubLesson sl\n"
                    + "on l.SubLessonID = sl.SubLessonID\n"
                    + "join LessonType lt \n"
                    + "on lt.LessonTypeID = l.LessonTypeID\n"
                    + "join Course c on c.CourseID = l.CourseID\n"
                    + "where c.[Status] = 1 and sl.SubLessonID = ? order by l.[order] ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, subLesson);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Course course = new Course();
                course.setCourseId(rs.getInt("CourseID"));
                course.setName(rs.getString("Name"));
                course.setDescription(rs.getString("Description"));
                course.setTinyPictureUrl(rs.getString("TinyPictureUrl"));
                course.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                course.setCreatedDate(rs.getDate("CreatedDate"));
                course.setModifiedDate(rs.getDate("ModifiedDate"));
                course.setPrice(rs.getBigDecimal("Price"));
                course.setStatus(rs.getBoolean("Status"));

                LessonType type = new LessonType();
                type.setLessonTypeID(rs.getInt("LessonTypeID"));
                type.setName(rs.getString("LessonTypeName"));

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setCourseID(course);
                lesson.setLessonTypeID(type);
                lesson.setName(rs.getString("LessonName"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                list.add(lesson);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Lesson getLessonPlay(int accountID, int courseID, int lessonID) {
        try {
            String sql = "select l.* from Lesson l join Course c\n"
                    + "on l.CourseID = c.CourseID join CourseAccount ca\n"
                    + "on ca.CourseID = c.CourseID join Account a\n"
                    + "on a.AccountID = ca.AccountID\n"
                    + "where a.AccountID = ? and ca.CourseID = ? and l.LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            stm.setInt(3, lessonID);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setName(rs.getString("Name"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                return lesson;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Lesson getLessonLearning(int accountID, int courseID) {
        try {
            String sql = "select top 1 l.* from lesson l join Course c\n"
                    + "on c.CourseID = l.CourseID join CourseAccount ca\n"
                    + "on ca.CourseID = c.CourseID join Account a\n"
                    + "on a.AccountID = ca.AccountID\n"
                    + "where a.AccountID = ? and c.CourseID = ? and LessonTypeID = 1 and LessonID not in (\n"
                    + "select l.lessonid from Lesson l join CompletedLesson cl\n"
                    + "on l.LessonID = cl.LessonID )\n"
                    + "order by l.[order] asc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setName(rs.getString("Name"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                return lesson;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertCompletedLesson(int accountID, int lessonID) {
        try {
            String sql = "INSERT INTO [CompletedLesson]([AccountID],\n"
                    + "[LessonID],[Score],[Status])\n"
                    + "VALUES(?,?,10,1)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, lessonID);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getNumberLessonInCourse(int accountID, int courseID) {
        try {
            String sql = "SELECT COUNT(*) AS TotalNumLesson FROM dbo.Lesson l JOIN dbo.Course c\n"
                    + "ON l.CourseID = c.CourseID JOIN dbo.CourseAccount ca\n"
                    + "ON ca.CourseID = c.CourseID \n"
                    + "WHERE ca.accountID = ? AND c.CourseID = ? AND l.LessonTypeID = 1 ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalNumLesson");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getNumberLessonLearningInCourse(int accountID, int courseID) {
        try {
            String sql = "SELECT COUNT(*) AS LearningNumLesson "
                    + "FROM dbo.CompletedLesson cl JOIN dbo.Lesson l\n"
                    + "ON l.LessonID = cl.LessonID \n"
                    + "WHERE cl.AccountID = ? AND l.CourseID = ? AND l.LessonTypeID = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("LearningNumLesson");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getLastLesson(int courseID) {
        try {
            String sql = "SELECT TOP 1 LessonID FROM dbo.Lesson \n"
                    + "WHERE CourseID = ? AND LessonTypeID = 1 \n"
                    + "ORDER BY [Order] DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("LessonID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public Lesson getLessonNext(int courseID, int lessonID) {
        try {
            String sql = "select top 1 * from lesson\n"
                    + "where lessonTypeID = 1 and courseid = ? and [order] > (\n"
                    + "	select [order] from lesson where lessonid = ?\n"
                    + ")\n"
                    + "order by [order] asc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            stm.setInt(2, lessonID);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setName(rs.getString("Name"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                return lesson;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Lesson getLessonFirstOfCourse(int courseID) {
        try {
            String sql = "select top 1 * from lesson \n"
                    + "where courseid = ? and LessonTypeID = 1 and [status] = 1\n"
                    + "order by [order] asc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setName(rs.getString("Name"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                return lesson;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Lesson getLessonByID(int id) {
        try {
            String sql = "select Name from Lesson "
                    + "where LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setName(rs.getString("Name"));
                return lesson;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getTotalLessonInCourse(int courseId) {
        try {
            String sql = "select Count(*) as num from lesson \n"
                    + "where LessonTypeID = 1 and CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("num"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int getTotalQuizInCourse(int courseId) {
        try {
            String sql = "select Count(*) as num from lesson \n"
                    + "where LessonTypeID = 2 and CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("num"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Lesson> getListLessonByCondition(int cid, ArrayList<Integer> ltid, ArrayList<Integer> lsid, boolean checklt, boolean checkls, int stt) {
        ArrayList<Lesson> list = new ArrayList<>();
        int indexQ = 2;
        System.out.println("stt: " + stt);
        try {
            String sql = "select l.LessonID,l.Name as [LessonName],l.VideoUrl, l.StartLearningTime, lt.Name as [LessonTypeName], lt.LessonTypeID, sl.Name as [SubLessonName],sl.SubLessonID, l.[Status] \n"
                    + "from Lesson l, SubLesson sl, LessonType lt, Course c \n"
                    + "where l.LessonTypeID = lt.LessonTypeID and l.SubLessonID = sl.SubLessonID \n"
                    + "and c.CourseID = l.CourseID and c.CourseID = ? ";
            if (stt != -1) {
                sql += " and l.[Status] = ? ";
            }
            if ((checkls == false || checklt == false) && (!lsid.isEmpty() || !ltid.isEmpty())) {
                for (int i = 0; i < lsid.size(); i++) {
                    if (i == 0) {
                        sql += "and (sl.SubLessonID = ? ";
                    } else {
                        sql += "or sl.SubLessonID = ? ";
                    }
                    if (i == lsid.size() - 1) {
                        sql += ") ";
                    }
                }
                for (int i = 0; i < ltid.size(); i++) {
                    if (i == 0) {
                        sql += "and (lt.LessonTypeID = ? ";
                    } else {
                        sql += "or lt.LessonTypeID = ? ";
                    }
                    if (i == ltid.size() - 1) {
                        sql += ") ";
                    }
                }
            }
            System.out.println("sql: " + sql);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            if (stt != -1) {
                stm.setInt(2, stt);
                indexQ = 3;
            }
            if ((checkls == false || checklt == false) && (!lsid.isEmpty() || !ltid.isEmpty())) {
                if (!lsid.isEmpty() && ltid.isEmpty()) {
                    for (int i = 0; i < lsid.size(); i++) {
                        stm.setInt((i + indexQ), lsid.get(i));
                    }
                }
                if (lsid.isEmpty() && !ltid.isEmpty()) {
                    for (int i = 0; i < ltid.size(); i++) {
                        stm.setInt((i + indexQ), ltid.get(i));
                    }
                }
                if (!lsid.isEmpty() && !ltid.isEmpty()) {
                    for (int i = 0; i < lsid.size(); i++) {
                        stm.setInt((i + indexQ), lsid.get(i));
                    }
                    for (int i = 0; i < ltid.size(); i++) {
                        stm.setInt((i + indexQ + lsid.size()), ltid.get(i));
                    }
                }
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                LessonType type = new LessonType();
                type.setLessonTypeID(rs.getInt("LessonTypeID"));
                type.setName(rs.getString("LessonTypeName"));

                SubLesson sub = new SubLesson();
                sub.setSubLessonID(rs.getInt("SubLessonID"));
                sub.setName(rs.getString("SubLessonName"));

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setLessonTypeID(type);
                lesson.setName(rs.getString("LessonName"));
                lesson.setSubLessonID(sub);
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));

                list.add(lesson);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Lesson getAllLessonByID(int id) {
        try {
            String sql = "select * from Lesson "
                    + "where LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                LessonType lt = new LessonType();
                lt.setLessonTypeID(rs.getInt("LessonTypeID"));

                SubLesson sl = new SubLesson();
                sl.setSubLessonID(rs.getInt("SubLessonID"));

                Course c = new Course();
                c.setCourseId(rs.getInt("CourseID"));

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("LessonID"));
                lesson.setCourseID(c);
                lesson.setName(rs.getString("Name"));
                lesson.setLessonTypeID(lt);
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));
                lesson.setStartLearningTime(rs.getTimestamp("StartLearningTime"));
                lesson.setSubLessonID(sl);
                return lesson;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int insertLesson(Lesson lesson, int type) {
        try {
            String sql = "insert into [Lesson] (CourseID, LessonTypeID,Name,CreatedTime,[Status],StartLearningTime, SubLessonID, [Order]) "
                    + "values (?,?,?,?,?,?,?,?)";
            if (type == 1) {
                sql = "insert into [Lesson] (CourseID, LessonTypeID,Name,CreatedTime,[Status],StartLearningTime, SubLessonID, [Order], VideoUrl) "
                        + "values (?,?,?,?,?,?,?,?,?)";
            }
            PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, lesson.getCourseID().getCourseId());
            stm.setInt(2, lesson.getLessonTypeID().getLessonTypeID());
            stm.setString(3, lesson.getName());
            stm.setTimestamp(4, lesson.getCreatedTime());
            stm.setInt(5, (lesson.isStatus() == true ? 1 : 0));
            stm.setTimestamp(6, lesson.getStartLearningTime());
            stm.setInt(7, lesson.getSubLessonID().getSubLessonID());
            stm.setInt(8, lesson.getOrder());
            if (type == 1) {
                stm.setString(9, lesson.getVideoUrl());
            }
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int productId = Integer.parseInt(rs.getString(1));
                return productId;
            }
        } catch (Exception ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void updateLesson(Lesson lesson, int type) {
        int index = 6;
        try {
            String sql = "update [Lesson] set Name = ?, UpdatedTime = ?, StartLearningTime = ?, "
                    + "[Status] = ?, SubLessonID = ? where LessonID = ?";
            if (type == 1) {
                sql = "update [Lesson] set Name = ?, UpdatedTime = ?, StartLearningTime = ?, "
                        + "[Status] = ?, SubLessonID = ?, VideoUrl = ? where LessonID = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, lesson.getName());
            stm.setTimestamp(2, lesson.getUpdatedTime());
            stm.setTimestamp(3, lesson.getStartLearningTime());
            stm.setInt(4, (lesson.isStatus() == true ? 1 : 0));
            stm.setInt(5, lesson.getSubLessonID().getSubLessonID());
            if (type == 1) {
                stm.setString(6, lesson.getVideoUrl());
                index++;
            }
            stm.setInt(index, lesson.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateOrder(int oldOrder, int newOrder, int course) {
        ArrayList<Lesson> list = new LessonDAO().getAllOrderByCourse(course);
        if (oldOrder < newOrder && oldOrder != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOrder() == oldOrder) {
                    list.get(i).setOrder(newOrder);
                    i++;
                }
                if (list.get(i).getOrder() > oldOrder && list.get(i).getOrder() <= newOrder) {
                    list.get(i).setOrder(list.get(i).getOrder() - 1);
                }
            }
        }
        if (oldOrder > newOrder) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOrder() == oldOrder) {
                    list.get(i).setOrder(newOrder);
                    i++;
                }
                if (list.get(i).getOrder() >= newOrder && list.get(i).getOrder() < oldOrder) {
                    list.get(i).setOrder(list.get(i).getOrder() + 1);
                }
            }
        }
        if (oldOrder == 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOrder() >= newOrder) {
                    list.get(i).setOrder(list.get(i).getOrder() + 1);
                }
            }
        }
        try {
            String sql = "";
            for (Lesson lesson : list) {
                sql = "update [Lesson] set [Order] = ? where LessonID = ?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, lesson.getOrder());
                stm.setInt(2, lesson.getId());
                stm.executeUpdate();
            }

        } catch (Exception ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public int getOrderByCourse(int courseId) {
        int count = 0;
        try {
            String sql = "select COUNT(*) as count from Lesson where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count + 1;
    }

    public ArrayList<Lesson> getAllOrderByCourse(int course) {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "select [LessonID], [Order] from Lesson where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setId(rs.getInt(1));
                l.setOrder(rs.getInt(2));

                list.add(l);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Lesson> getOrderBySublesson(int sublessonId) {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "select [LessonID], [Order] from Lesson where SubLessonID = ? order by [Order]";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sublessonId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setId(rs.getInt(1));
                l.setOrder(rs.getInt(2));

                list.add(l);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int getSizeOrderBySublesson(int courseId) {
        try {
            String sql = "select count(*) as ordersize from lesson where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return -1;
    }

    public void deleteLesson(int id) {
        try {
            String sql = "DELETE [Lesson] WHERE [LessonID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateOrderByDelete(int orderDelete, int course) {
        ArrayList<Lesson> list = new LessonDAO().getAllOrderByCourse(course);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getOrder() > orderDelete){
                list.get(i).setOrder(list.get(i).getOrder()-1);
            }
        }
        
        try {
            String sql = "";
            for (Lesson lesson : list) {
                sql = "update [Lesson] set [Order] = ? where LessonID = ?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, lesson.getOrder());
                stm.setInt(2, lesson.getId());
                stm.executeUpdate();
            }

        } catch (Exception ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Lesson> getLessonTypeQuiz() {
        ArrayList<Lesson> list = new ArrayList<>();
        try {
            String sql = "  select Name, LessonID, CourseID from Lesson where LessonTypeID = 2";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson l = new Lesson();
                l.setName(rs.getString(1));
                l.setId(rs.getInt(2));
                
                Course c = new Course();
                c.setCourseId(rs.getInt(3));

                l.setCourseID(c);
                list.add(l);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
