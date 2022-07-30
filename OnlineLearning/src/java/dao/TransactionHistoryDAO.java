package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Course;
import model.CoursePricePackage;
import model.Gender;
import model.TransactionHistory;

public class TransactionHistoryDAO extends DBContext {

    public TransactionHistory mappingData(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountID(rs.getInt("AccountID"));
        account.setFirstName(rs.getString("FirstName"));
        account.setLastName(rs.getString("LastName"));
        account.setEmail(rs.getString("Email"));
        account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
        account.setCreatedTime(rs.getTimestamp("CreatedTime"));
        account.setModifiedTime(rs.getTimestamp("ModifiedTime"));
        account.setPhone(rs.getString("Phone"));
        account.setAddress(rs.getString("Address"));
        account.setGender(Gender.of(rs.getBoolean("Gender")));
        account.setBalance(rs.getBigDecimal("Balance"));

        Course course = new Course();
        course.setCourseId(rs.getInt("courseID"));
        course.setName(rs.getString("Name"));
        course.setDescription(rs.getString("Description"));
        course.setTinyPictureUrl(rs.getString("TinyPictureUrl"));
        course.setThumbnailUrl(rs.getString("ThumbnailUrl"));
        course.setCreatedDate(rs.getDate("CreatedDate"));
        course.setModifiedDate(rs.getDate("ModifiedDate"));
        course.setPrice(rs.getBigDecimal("Price"));

        CoursePricePackage coursePackage = new CoursePricePackage();
        coursePackage.setPriceId(rs.getInt("PriceID"));
        coursePackage.setName(rs.getString("CoursePriceName"));
        if (rs.getObject("AccessDuration") != null) {
            coursePackage.setAccessDuration(rs.getInt("AccessDuration"));
        } else {
            coursePackage.setAccessDuration(-1);
        }
        coursePackage.setStatus(rs.getBoolean("CoursePriceStatus"));
        coursePackage.setListPrice(rs.getBigDecimal("ListPrice"));
        coursePackage.setSalePrice(rs.getBigDecimal("SalePrice"));
        coursePackage.setCourseId(course);

        Account sale = new Account();
        sale.setAccountID(rs.getInt("SaleID"));
        sale.setFirstName(rs.getString("SaleFirstName"));
        sale.setLastName(rs.getString("SaleLastName"));

        Account saleUpdated = new Account();
        saleUpdated.setAccountID(rs.getInt("UpdatedBySaleID"));
        saleUpdated.setFirstName(rs.getString("SaleUpdatedFirstName"));
        saleUpdated.setLastName(rs.getString("SaleUpdatedLastName"));

        TransactionHistory th = new TransactionHistory();
        th.setId(rs.getInt("TransactionHistoryID"));
        th.setAccountID(account);
        th.setCourseID(course);
        th.setAmount(rs.getBigDecimal("Amount"));
        th.setTransactionTime(rs.getDate("trasactionTime"));
        th.setCoursePackageID(coursePackage);
        th.setSaleID(sale);
        th.setUpdatedBySaleID(saleUpdated);
        th.setNote(rs.getString("Note"));
        Date validFrom = new Date(th.getTransactionTime().getTime());
        th.setValidFrom(validFrom);
        Date validTo = new Date(-1);
        if (coursePackage.getAccessDuration() > 0) {
            validTo = new Date(th.getTransactionTime().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(validTo.getTime());
            cal.add(Calendar.MONTH, coursePackage.getAccessDuration());
            validTo = new Date(cal.getTime().getTime());
        }

        th.setValidTo(validTo);
        return th;
    }

    public ArrayList<TransactionHistory> getListTransaction() {
        ArrayList<TransactionHistory> listTrans = new ArrayList<>();
        try {
            String sql = "SELECT [TransactionHistoryID],[Amount],th.[TrasactionTime], [Note],\n"
                    + "th.SaleID, a2.FirstName as SaleFirstName, a2.LastName as SaleLastName,\n"
                    + "th.UpdatedBySaleID, a3.FirstName as SaleUpdatedFirstName, a3.LastName as SaleUpdatedLastName,\n"
                    + "[CoursePricePackageID], a.*, c.*,\n"
                    + "[PriceID] \n"
                    + ",cpp.[Name] as CoursePriceName\n"
                    + ",[AccessDuration]\n"
                    + ",cpp.[Status] as CoursePriceStatus\n"
                    + ",[ListPrice]\n"
                    + ",[SalePrice]\n"
                    + "from TransactionHistory th join Account a on th.AccountID = a.AccountID\n"
                    + "join CoursePricePackage cpp on cpp.PriceID = th.CoursePricePackageID\n"
                    + "join Course c on c.CourseID = cpp.CourseID\n"
                    + "join Account a2 on a2.AccountID = th.SaleID\n"
                    + "join Account a3 on a3.AccountID = th.UpdatedBySaleID";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TransactionHistory th = mappingData(rs);
                listTrans.add(th);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTrans;
    }

    public ArrayList<TransactionHistory> getListTransactionByTitleEmail(String valueSearch) {
        ArrayList<TransactionHistory> listTrans = new ArrayList<>();
        try {
            String sql = "SELECT [TransactionHistoryID],[Amount],th.[TrasactionTime], [Note],\n"
                    + "th.SaleID, a2.FirstName as SaleFirstName, a2.LastName as SaleLastName,\n"
                    + "th.UpdatedBySaleID, a3.FirstName as SaleUpdatedFirstName, a3.LastName as SaleUpdatedLastName,\n"
                    + "[CoursePricePackageID], a.*, c.*,\n"
                    + "		[PriceID]\n"
                    + "      ,cpp.[Name] as CoursePriceName\n"
                    + "      ,[AccessDuration]\n"
                    + "      ,cpp.[Status] as CoursePriceStatus\n"
                    + "      ,[ListPrice]\n"
                    + "      ,[SalePrice]\n"
                    + "from TransactionHistory th join Account a on th.AccountID = a.AccountID\n"
                    + "join CoursePricePackage cpp on cpp.PriceID = th.CoursePricePackageID\n"
                    + "join Course c on c.CourseID = cpp.CourseID\n"
                    + "join Account a2 on a2.AccountID = th.SaleID\n"
                    + "join Account a3 on a3.AccountID = th.UpdatedBySaleID\n"
                    + "where c.name like ? or a.Email Like ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + valueSearch + "%");
            stm.setString(2, "%" + valueSearch + "%");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TransactionHistory th = mappingData(rs);
                listTrans.add(th);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTrans;
    }

    public ArrayList<TransactionHistory> getListTransByTransactionTime(String validFrom, String validTo) {
        ArrayList<TransactionHistory> listTrans = new ArrayList<>();
        try {
            PreparedStatement stm = null;
            String sql = "SELECT [TransactionHistoryID],[Amount],th.[TrasactionTime], [Note],\n"
                    + "th.SaleID, a2.FirstName as SaleFirstName, a2.LastName as SaleLastName,\n"
                    + "th.UpdatedBySaleID, a3.FirstName as SaleUpdatedFirstName, a3.LastName as SaleUpdatedLastName,\n"
                    + "[CoursePricePackageID], a.*, c.*,\n"
                    + "		[PriceID]\n"
                    + "      ,cpp.[Name] as CoursePriceName\n"
                    + "      ,[AccessDuration]\n"
                    + "      ,cpp.[Status] as CoursePriceStatus\n"
                    + "      ,[ListPrice]\n"
                    + "      ,[SalePrice]\n"
                    + "from TransactionHistory th join Account a on th.AccountID = a.AccountID\n"
                    + "join CoursePricePackage cpp on cpp.PriceID = th.CoursePricePackageID\n"
                    + "join Account a2 on a2.AccountID = th.SaleID\n"
                    + "join Account a3 on a3.AccountID = th.UpdatedBySaleID\n"
                    + "join Course c on c.CourseID = cpp.CourseID\n";
            if (validFrom.isEmpty() && !validTo.isEmpty()) {
                sql += "where trasactionTime <= ?";

                LocalDate dateTo = LocalDate.parse(validTo);
                Date date = Date.valueOf(dateTo);

                stm = connection.prepareStatement(sql);
                stm.setDate(1, date);
            } else if (!validFrom.isEmpty() && validTo.isEmpty()) {
                sql += "where trasactionTime >= ?";

                LocalDate dateFrom = LocalDate.parse(validFrom);
                Date date = Date.valueOf(dateFrom);

                stm = connection.prepareStatement(sql);
                stm.setDate(1, date);
            } else if (!validFrom.isEmpty() && !validTo.isEmpty()) {
                sql += "where trasactionTime >= ? and trasactionTime <= ?";

                LocalDate dateFrom = LocalDate.parse(validFrom);
                Date date1 = Date.valueOf(dateFrom);
                LocalDate dateTo = LocalDate.parse(validTo);
                Date date2 = Date.valueOf(dateTo);

                stm = connection.prepareStatement(sql);
                stm.setDate(1, date1);
                stm.setDate(2, date2);
            } else {
                stm = connection.prepareStatement(sql);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TransactionHistory th = mappingData(rs);
                listTrans.add(th);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTrans;
    }

    public TransactionHistory getTransactionHistoryById(int id) {
        try {
            String sql = "SELECT [TransactionHistoryID],[Amount],th.[TrasactionTime], [Note],\n"
                    + "th.SaleID, a2.FirstName as SaleFirstName, a2.LastName as SaleLastName,\n"
                    + "th.UpdatedBySaleID, a3.FirstName as SaleUpdatedFirstName, a3.LastName as SaleUpdatedLastName,\n"
                    + "[CoursePricePackageID], a.*, c.*,\n"
                    + "		[PriceID]\n"
                    + "      ,cpp.[Name] as CoursePriceName\n"
                    + "      ,[AccessDuration]\n"
                    + "      ,cpp.[Status] as CoursePriceStatus\n"
                    + "      ,[ListPrice]\n"
                    + "      ,[SalePrice]\n"
                    + "from TransactionHistory th join Account a on th.AccountID = a.AccountID\n"
                    + "join CoursePricePackage cpp on cpp.PriceID = th.CoursePricePackageID\n"
                    + "join Course c on c.CourseID = cpp.CourseID\n"
                    + "join Account a2 on a2.AccountID = th.SaleID\n"
                    + "join Account a3 on a3.AccountID = th.UpdatedBySaleID\n"
                    + "where TransactionHistoryID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                TransactionHistory th = mappingData(rs);
                return th;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteTransaction(int id) {
        try {
            String sql = "delete from transactionHistory where transactionHistoryID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertTransaction(TransactionHistory th) {
        try {
            String sql = "INSERT INTO [TransactionHistory]\n"
                    + "           ([AccountID]\n"
                    + "           ,[CourseID]\n"
                    + "           ,[Amount]\n"
                    + "           ,[TrasactionTime]\n"
                    + "           ,[CoursePricePackageID]\n"
                    + "           ,[SaleID]\n"
                    + "           ,[UpdatedBySaleID]\n"
                    + "           ,[Note])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, th.getAccountID().getAccountID());
            stm.setInt(2, th.getCourseID().getCourseId());
            stm.setBigDecimal(3, th.getAmount());
            stm.setDate(4, th.getTransactionTime());
            stm.setInt(5, th.getCoursePackageID().getPriceId());
            stm.setInt(6, th.getSaleID().getAccountID());
            stm.setInt(7, th.getUpdatedBySaleID().getAccountID());
            stm.setString(8, th.getNote());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTransaction(int transId, TransactionHistory th) {
        try {
            String sql = "UPDATE [TransactionHistory]\n"
                    + "   SET [AccountID] = ?\n"
                    + "      ,[CourseID] = ?\n"
                    + "      ,[Amount] = ?\n"
                    + "      ,[CoursePricePackageID] = ?\n"
                    + "      ,[UpdatedBySaleID] = ?\n"
                    + "      ,[Note] = ?\n"
                    + " WHERE TransactionHistoryID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, th.getAccountID().getAccountID());
            stm.setInt(2, th.getCourseID().getCourseId());
            stm.setBigDecimal(3, th.getAmount());
            stm.setInt(4, th.getCoursePackageID().getPriceId());
            stm.setInt(5, th.getUpdatedBySaleID().getAccountID());
            stm.setString(6, th.getNote());
            stm.setInt(7, transId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
