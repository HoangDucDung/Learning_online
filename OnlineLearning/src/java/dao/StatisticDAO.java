package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import model.Subject;

public class StatisticDAO extends DBContext {

    private static final Logger LOG = Logger.getLogger(StatisticDAO.class.getName());

    private class CourseSubjectArgs {

        private final String subject;
        private final int amount;

        public CourseSubjectArgs(String subjectName, int amontCourse) {
            this.subject = subjectName;
            this.amount = amontCourse;
        }
    }

    public class CourseEnrollArgs {

        private int courseId;
        private String courseName;
        private int numberOfEnroll;

        public CourseEnrollArgs(int courseId, String courseName, int numberOfEnroll) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.numberOfEnroll = numberOfEnroll;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getNumberOfEnroll() {
            return numberOfEnroll;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public void setNumberOfEnroll(int numberOfEnroll) {
            this.numberOfEnroll = numberOfEnroll;
        }

    }

    public List countCourseInAllSubject() throws SQLException {
        List<CourseSubjectArgs> ls = new ArrayList<>();
        String sql = "select s.Name [Subject], count(*) [Total]\n"
                + "from SubjectCourse sc inner join Subject s\n"
                + "on sc.SubjectID = s.SubjectID\n"
                + "group by s.Name";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ls.add(new CourseSubjectArgs(rs.getString("Subject"), rs.getInt("Total")));
            }
        }
        return ls;
    }

    public int countRegistedAccount(LocalDate from, LocalDate to) {
        String sql = "select count(AccountID) from Account where CreatedTime between ? and ?";
        int count = 0;
        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setDate(1, java.sql.Date.valueOf(from));
            s.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = s.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            count = -1;
        }

        return count;
    }

    @Deprecated
    public List<CourseEnrollArgs> countNumberEnrollInAllCourse() {
        List<CourseEnrollArgs> ls = new ArrayList<>();
        String sql = "select c.CourseID, c.Name [CourseName], count(*) [NumberOfEnroll]\n"
                + "from Course c left join TransactionHistory ac \n"
                + "	on c.CourseID = ac.CourseID\n"
                + "group by c.CourseID, c.Name\n"
                + "order by NumberOfEnroll desc";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ls.add(new CourseEnrollArgs(rs.getInt("CourseID"), rs.getString("CourseName"), rs.getInt("NumberOfEnroll")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ls;
    }

    /**
     * Calculate income up to now.
     *
     * @return income up to now
     */
    public double getAllEarning() {
        String sql = "select sum(Amount) from TransactionHistory";
        double income = 0;
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            income = rs.next() ? rs.getDouble(1) : 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return income;
    }

    /**
     * Only used for json serialize.
     */
    private class RevenueArgs {

        private int month;
        private int year;
        private double revenue;

        public RevenueArgs(int month, int year, double revenue) {
            this.month = month;
            this.year = year;
            this.revenue = revenue;
        }
    }

    private class RevenueWrapper {

        private LocalDate date;
        private double revenue;

        public RevenueWrapper(LocalDate d, double r) {
            date = d;
            revenue = r;
        }
    }

    public List calculateRevenues(LocalDate from, LocalDate to) {
        long dayDiff = ChronoUnit.DAYS.between(from, to);

        List<RevenueWrapper> ls = new ArrayList<>();
        for (long i = 0; i <= dayDiff; i++) {
            ls.add(new RevenueWrapper(from.plusDays(i), 0));
        }

        String sql = "select sum(Amount) from TransactionHistory where TrasactionTime = ?";

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            for (RevenueWrapper r : ls) {
                s.setDate(1, java.sql.Date.valueOf(r.date));
                ResultSet rs = s.executeQuery();
                if (rs.next()) {
                    r.revenue = rs.getDouble(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ls;
    }

    @Deprecated
    public List calculateRevenues(int m1, int y1, int m2, int y2) throws SQLException {
        List<RevenueArgs> ls = new ArrayList<>();
        String sql = "select\n"
                + "	MONTH(TrasactionTime) [Month],\n"
                + "     YEAR(TrasactionTime) [Year],\n"
                + "	SUM(Amount) [Revenue]\n"
                + "from TransactionHistory\n"
                + "where \n"
                + "	Year(TrasactionTime) between ? and ?\n"
                + "group by MONTH(TrasactionTime), YEAR(TrasactionTime)\n"
                + "order by [Year], [Month]";

        for (int year = y1; year <= y2; year++) {
            int start = 1;
            int end = 12;
            if (year == y1) {
                start = m1;
            }
            if (year == y2) {
                end = m2;
            }
            for (int m = start; m <= end; m++) {
                RevenueArgs r = new RevenueArgs(m, year, 0);
                ls.add(r);
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, y1);
            stmt.setInt(2, y2);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int m = rs.getInt("Month");
                int y = rs.getInt("Year");
                double revenue = rs.getDouble("Revenue");

                for (RevenueArgs r : ls) {
                    if (r.month == m && r.year == y) {
                        r.revenue = revenue;
                        break;
                    }
                }
            }
        }

        return ls;
    }

    public double calculateRevenue(LocalDate from, LocalDate to) {
        String sql = "select SUM(Amount) from TransactionHistory where TrasactionTime between ? and ?";
        double revenue = 0;
        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setDate(1, java.sql.Date.valueOf(from));
            s.setDate(2, java.sql.Date.valueOf(to));

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                revenue = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            revenue = -1;
        }
        return revenue;
    }

    @Deprecated
    public double calculateRevenueInYear(int year) {
        double revenue = 0;
        String sql = "select sum(Amount) [Amount]\n"
                + "from TransactionHistory\n"
                + "where YEAR(TrasactionTime) = ? \n"
                + "group by YEAR(TrasactionTime)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                revenue = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            LOG.warning(ex.getMessage());
        }
        return revenue;
    }

    private class BlogViewCounterArgs {

        private final int blogCategoryId;
        private final String blogCategoryName;
        private final int numberOfView;

        public BlogViewCounterArgs(int blogCategoryId, String blogCategoryName, int numberOfView) {
            this.blogCategoryId = blogCategoryId;
            this.blogCategoryName = blogCategoryName;
            this.numberOfView = numberOfView;
        }
    }

    @Deprecated
    public List countNumberViewOfAllBlogCategory() throws SQLException {
        String sql = "select a.BlogCategoryID, a.Name [BlogCategoryName],\n"
                + "		(select sum(b.NumberOfView)\n"
                + "	from BlogCategoryBlog bc inner join Blog b\n"
                + "		on bc.BlogID = b.BlogID\n"
                + "	where bc.BlogCategoryID = a.BlogCategoryID\n"
                + "	group by bc.BlogCategoryID) [NumberOfView]\n"
                + "from BlogCategory as a\n"
                + "order by NumberOfView";
        List<BlogViewCounterArgs> ls = new ArrayList();
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ls.add(new BlogViewCounterArgs(rs.getInt("BlogCategoryID"), rs.getString("BlogCategoryName"), rs.getInt("NumberOfView")));
            }
        }
        return ls;
    }

    /**
     * Returns a map has two key.
     * <ul>
     * <li>1. MonthInYear: MM_YYYY date format</li>
     * <li>2. NumberNewAccount: number of accounts were created in above
     * time.</li>
     * <ul>
     *
     * @param MM_YYYY date format MM/yyyy
     * @return
     * @throws SQLException
     */
    @Deprecated
    public Map<String, Integer> getNumberOfNewAccount(String MM_YYYY) {
        String sql = "select \n"
                + "	MonthInYear = FORMAT(CreatedTime, 'MM/yyyy') ,\n"
                + "	count(*) [NumberNewAccount]\n"
                + "from Account\n"
                + "where FORMAT(CreatedTime, 'MM/yyyy') = ?\n"
                + "group by FORMAT(CreatedTime, 'MM/yyyy')";
        Map map = new HashMap();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, MM_YYYY);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                map.put("MonthInYear", rs.getString("MonthInYear"));
                map.put("NumberNewAccount", rs.getInt("NumberNewAccount"));
            } else {
                map.put("MonthInYear", MM_YYYY);
                map.put("NumberNewAccount", 0);
            }
        } catch (Exception ex) {
            LOG.warning(ex.getMessage());
        }

        return map;
    }

    @Deprecated
    public int countNewAccount(int month, int year) {
        String sql = "select count(AccountID) from Account "
                + "where MONTH(CreatedTime) = ? and YEAR(CreatedTime) = ?";
        int n = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            n = rs.next() ? rs.getInt(1) : 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public int getNumberVisitPage(LocalDate from, LocalDate to) {
        int counter = 0;
        String sql = "select sum(AmountAccessPage) from PageViewCounter where Date between ? and ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(from));
            stmt.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                counter = rs.getInt(1);
            }
        } catch (Exception ex) {
            counter = -1;
        }
        return counter;
    }

    public void plusNumberVisitPage(LocalDate date, int n) {
        String sql = "select * from PageViewCounter where [date] = ?";
        try (PreparedStatement stmt
                = connection.prepareCall(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int newNumberVisitPage = rs.getInt(2) + n;
                rs.updateInt(2, newNumberVisitPage);
                rs.updateRow();
            } else {
                rs.moveToInsertRow();
                rs.updateDate(1, java.sql.Date.valueOf(date));
                rs.updateInt(2, n);
                rs.insertRow();
            }
        } catch (SQLException ex) {
            LOG.warning(ex.getMessage());
        }
    }

    /**
     * Only for json serializable
     */
    public class RegistrationAmountArgs {

        private LocalDate date;
        private int amount;

        public RegistrationAmountArgs(LocalDate date, int amount) {
            this.date = date;
            this.amount = amount;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

    }

    public List<RegistrationAmountArgs> countRegistration(LocalDate from, LocalDate to) {
        List<RegistrationAmountArgs> ls = new ArrayList<>();

        long dateDiff = ChronoUnit.DAYS.between(from, to) + 1;

        for (int i = 0; i < dateDiff; i++) {
            ls.add(new RegistrationAmountArgs(from.plusDays(i), 0));
        }

        String sql = "select CONVERT(Date,TrasactionTime ) [date], count(TransactionHistoryID) [amount] \n"
                + "from TransactionHistory\n"
                + "group by CONVERT(Date, TrasactionTime) \n"
                + "having CONVERT(Date, TrasactionTime) between ? and ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(from));
            stmt.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate(1).toLocalDate();
                for (RegistrationAmountArgs r : ls) {
                    if (r.getDate().equals(date)) {
                        r.setAmount(rs.getInt(2));
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ls;
    }

    public class AmountAccountEnrollInSubjectArgs {

        private int subjectId;
        private String subjectName;
        private int amountEnrolled;
        private int totalAccount;

        public AmountAccountEnrollInSubjectArgs(int subjectId, String subjectName, int amountEnrolled, int totalAccount) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
            this.amountEnrolled = amountEnrolled;
            this.totalAccount = totalAccount;
        }
    }

    public List<AmountAccountEnrollInSubjectArgs> getAmountEnrollInAllSubject() {
        String sqlCountTotalAccount = "select count(AccountID) from Account";
        int totalAccount = 0;
        List<AmountAccountEnrollInSubjectArgs> ls = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlCountTotalAccount)) {
            if (rs.next()) {
                totalAccount = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (totalAccount == 0) {
            return ls;
        }

        String sqlSelectSubject = "select SubjectID, Name from Subject";

        String sqlCountAccountOfSubject = "SELECT COUNT(DISTINCT(AccountID)) from TransactionHistory\n"
                + "where CourseID IN (SELECT sc.CourseID\n"
                + "FROM SubjectCourse sc INNER JOIN Course c ON sc.CourseID = c.CourseID\n"
                + "where sc.SubjectID = ?)";

        try (PreparedStatement countAccountStmt = connection.prepareStatement(sqlCountAccountOfSubject);
                Statement selectSubjectStmt = connection.createStatement();
                ResultSet subjectTable = selectSubjectStmt.executeQuery(sqlSelectSubject)) {
            while (subjectTable.next()) {
                int subjectId = subjectTable.getInt(1);
                String subjectName = subjectTable.getString(2);

                countAccountStmt.setInt(1, subjectId);
                ResultSet rs = countAccountStmt.executeQuery();
                int amountAccountEnroll = (rs.next()) ? rs.getInt(1) : 0;
                ls.add(new AmountAccountEnrollInSubjectArgs(subjectId, subjectName, amountAccountEnroll, totalAccount));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ls;
    }

    public class SubjectRevenueArgs {

        private Subject subject;
        private double revenue;

        public SubjectRevenueArgs(Subject subject, double revenue) {
            this.subject = subject;
            this.revenue = revenue;
        }

        public Subject getSubject() {
            return subject;
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public double getRevenue() {
            return revenue;
        }

        public void setRevenue(double revenue) {
            this.revenue = revenue;
        }
    }

    public List<SubjectRevenueArgs> getRevenueOfAllSubject(LocalDate from, LocalDate to) {
        String sql = "SELECT SUM(Amount) FROM TransactionHistory\n"
                + "WHERE (CourseID in (SELECT sc.CourseID\n"
                + "FROM SubjectCourse sc INNER JOIN Course c ON sc.CourseID = c.CourseID\n"
                + "where sc.SubjectID = ?)) and (TrasactionTime between ? and ?)";

        List<SubjectRevenueArgs> ls = new ArrayList<>();
        List<Subject> subjects = new SubjectDAO().getAllSubjects();

        for (Subject s : subjects) {
            ls.add(new SubjectRevenueArgs(s, 0));
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(2, java.sql.Date.valueOf(from));
            stmt.setDate(3, java.sql.Date.valueOf(to));

            for (SubjectRevenueArgs s : ls) {
                stmt.setInt(1, s.getSubject().getSubjectId());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    s.setRevenue(rs.getDouble(1));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ls;
    }
}
