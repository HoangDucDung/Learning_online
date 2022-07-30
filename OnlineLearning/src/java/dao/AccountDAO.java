package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Gender;
import model.Role;

public class AccountDAO extends DBContext {

    private void mappingData(Account account, ResultSet rs) throws SQLException {
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
    }

    public ArrayList<Account> findAll() throws Exception {
        String sql = "SELECT \n"
                + "	Account.AccountID, FirstName, LastName, Email, ProfilePictureUrl,\n"
                + "	RoleID, Balance, CreatedTime, ModifiedTime,	Phone, Address,	Gender,	PasswordHash\n"
                + "FROM Account INNER JOIN Password \n"
                + "	ON (Account.AccountID = Password.AccountID)";

        ArrayList<Account> accounts = new ArrayList<>();
        HashMap<Integer, Role> roleTable = new dao.RoleDAO().getRoleTable();

        try ( Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Account account = new Account();
                int roleID = rs.getInt("RoleID");
                account.setRole(roleTable.get(roleID));
                mappingData(account, rs);
                accounts.add(account);
            }
        }

        return accounts;
    }

    public Account find(String email, String password) throws SQLException {
        Account account = null;
        String sql = "SELECT a.*, p.PasswordHash FROM [Account] a JOIN [Password] p \n"
                + "ON a.AccountID = p.AccountID\n"
                + "WHERE a.[Email] = ? AND p.[PasswordHash] = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account();
                Role role = new RoleDAO().find(rs.getInt("RoleID"));
                System.out.println(">>> " + role);
                mappingData(account, rs);
                account.setRole(role);
            }
        }

        return account;
    }

    @Deprecated
    public Account getAccount(String email, String password) {
        try {
            String sql = "SELECT a.*, p.PasswordHash, Name"
                    + " FROM [Account] a JOIN [Password] p \n"
                    + " ON a.AccountID = p.AccountID\n"
                    + " JOIN [Role] r\n"
                    + " ON a.RoleID = r.RoleID\n"
                    + " WHERE a.[Email] = ? AND p.[PasswordHash] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                Role role = new Role();

                role.setId(rs.getInt("RoleID"));
                role.setName(rs.getString("Name"));
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setEmail(rs.getString("Email"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
                account.setRole(role);
                account.setCreatedTime(rs.getTimestamp("CreatedTime"));
                account.setModifiedTime(rs.getTimestamp("ModifiedTime"));
                account.setPhone(rs.getString("Phone"));
                account.setAddress(rs.getString("Address"));
                account.setGender(Gender.of(rs.getBoolean("Gender")));
                account.setPassword(rs.getString("PasswordHash"));
                account.setBalance(rs.getBigDecimal("Balance"));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getAccountIdByEmail(String email) {
        try {
            String sql = "SELECT [AccountId] FROM [Account]\n"
                    + "WHERE [Email] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("AccountId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean isExistAccount(String email) {
        try {
            String sql = "SELECT * FROM [Account] WHERE Email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insertAccount(String email, String fname, String lname, boolean gender, String phone) {

        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        PreparedStatement stm = null;
        try {
            String sqlAccount = "INSERT INTO [Account]([FirstName],[LastName],[Email],"
                    + "[RoleID],[CreatedTime],[Phone],[Gender],[Balance],[ProfilePictureUrl])\n"
                    + "VALUES(?,?,?,2,?,?,?,10000,'default-account-profile-picture-7.jpg')";
            stm = connection.prepareStatement(sqlAccount);
            stm.setString(1, fname);
            stm.setString(2, lname);
            stm.setString(3, email);
            stm.setTimestamp(4, createdTime);
            stm.setString(5, phone);
            stm.setBoolean(6, gender);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDateModify(String email) {
        PreparedStatement stm = null;
        Timestamp timeModified = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "UPDATE [Account] SET [ModifiedTime] = ? WHERE [Email] = ?";
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, timeModified);
            stm.setString(2, email);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Timestamp getTimeModify(String email) {
        try {
            String sql = "SELECT [ModifiedTime] FROM [Account] WHERE Email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("ModifiedTime");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editProfile(Account acc) {
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE [Account]\n"
                + "   SET [FirstName] = ?\n"
                + "      ,[LastName] = ?\n"
                + "      ,[ProfilePictureUrl] = ?\n"
                + "      ,[ModifiedTime] = ?\n"
                + "      ,[Phone] = ?\n"
                + "      ,[Address] = ?\n"
                + "      ,[Gender] = ?\n"
                + " WHERE AccountID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, acc.getFirstName());
            stm.setString(2, acc.getLastName());
            stm.setString(3, acc.getProfilePictureUrl());
            stm.setTimestamp(4, createdTime);
            stm.setString(5, acc.getPhone());
            stm.setString(6, acc.getAddress());
            stm.setBoolean(7, acc.getGender().asBoolean());
            stm.setInt(8, acc.getAccountID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
