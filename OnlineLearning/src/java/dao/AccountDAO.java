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

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
    
    public void insertAccount(String email, String fname, String lname, boolean gender, String phone, boolean status) {

        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        PreparedStatement stm = null;
        try {
            String sqlAccount = "INSERT INTO [Account]([FirstName],[LastName],[Email],"
                    + "[RoleID],[CreatedTime],[Phone],[Gender],[Balance],[ProfilePictureUrl], [Status])\n"
                    + "VALUES(?,?,?,2,?,?,?,10000,'default-account-profile-picture-7.jpg', 1)";
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

    public ArrayList<Account> getListExpert() {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account WHERE RoleID = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                Role role = new Role();

                role.setId(rs.getInt("RoleID"));
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
                account.setBalance(rs.getBigDecimal("Balance"));
                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public ArrayList<Account> getListAccountCanAccessCourse(int courseID) {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT CourseExpert.*, FirstName, LastName\n"
                    + "FROM CourseExpert JOIN Account ON CourseExpert.AccountID = Account.AccountID WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public void insertAccountCanAccessSubject(int courseID, int accountID) {
        try {
            String sql = "INSERT INTO [CourseExpert]\n"
                    + "           ([CourseID]\n"
                    + "           ,[AccountID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            stm.setInt(2, accountID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertListAccountCanAccessSubject(int courseID, ArrayList<Integer> accountIDs) {
        try {
            String sql = "INSERT INTO [CourseExpert]\n"
                    + "           ([CourseID]\n"
                    + "           ,[AccountID])\n"
                    + "     VALUES\n";
            for (int i = 0; i < accountIDs.size(); i++) {
                if (i == accountIDs.size() - 1) {
                    sql += "           (?\n"
                            + "           ,?)";
                } else {
                    sql += "           (?\n"
                            + "           ,?),";
                }
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            int index = 1;
            for (int i = 0; i < accountIDs.size(); i++) {
                Integer get = accountIDs.get(i);
                stm.setInt(index, courseID);
                index++;
                stm.setInt(index, get);
                index++;
            }
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAllAccountCanAccessSubject(int courseID) {
        try {
            String sql = "DELETE FROM [CourseExpert]\n"
                    + "      WHERE CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Account> getAllAccount() {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "select AccountID, FirstName, LastName, Email,Phone, Role.Name as RoleName, Account.RoleID, Account.Status \n"
                    + "                    from Account join Role on Account.RoleID = Role.RoleID";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                Role role = new Role();
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setEmail(rs.getString("Email"));
                account.setPhone(rs.getString("Phone"));

                role.setId(rs.getInt("RoleID"));
                role.setName(rs.getString("RoleName"));
                if (rs.getInt("Status") == 1) {
                    account.setStatus(true);
                } else {
                    account.setStatus(false);
                }
                account.setRole(role);
                accounts.add(account);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public ArrayList<Account> searchBy_Filter(String cid, String status) {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "select AccountID, FirstName, LastName, Email, Phone,\n"
                    + "Account.RoleID as RoleID,Role.Name as RoleName, Account.Status\n"
                    + "from Account join Role\n"
                    + "on Account.RoleID = Role.RoleID ";
            if (!cid.equalsIgnoreCase("-1") && !status.equalsIgnoreCase("-1")) {
                sql += "where Account.RoleID = ?\n"
                        + "and Account.Status = ?";

            } else if (!cid.equalsIgnoreCase("-1")) {
                sql += "where Account.RoleID = ?";

            } else if (cid.equalsIgnoreCase("-1")) {
                sql += "where Account.Status = ?";

            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (!cid.equalsIgnoreCase("-1") && !status.equalsIgnoreCase("-1")) {
                stm.setString(1, cid);
                stm.setString(2, status);

            } else if (!cid.equalsIgnoreCase("-1")) {
                stm.setString(1, cid);
            } else if (cid.equalsIgnoreCase("-1")) {
                stm.setString(1, status);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                Role role = new Role();
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setEmail(rs.getString("Email"));
                account.setPhone(rs.getString("Phone"));

                role.setId(rs.getInt("RoleID"));
                role.setName(rs.getString("RoleName"));
                if (rs.getInt("Status") == 1) {
                    account.setStatus(true);
                } else {
                    account.setStatus(false);
                }
                account.setRole(role);
                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public Account searchby_ID(int id) {
        try {
            String sql = "select AccountID,FirstName,LastName, Email,\n"
                    + "                    ProfilePictureUrl,Address,Phone,\n"
                    + "                    Gender,Account.RoleID,Name, Account.Status from Account join Role on Account.RoleID = Role.RoleID \n"
                    + "                    where AccountID = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setEmail(rs.getString("Email"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
                account.setAddress(rs.getString("Address"));
                account.setPhone(rs.getString("Phone"));
                account.setGender(Gender.of(rs.getBoolean("Gender")));
                Role role = new Role();
                role.setId(rs.getInt("RoleID"));
                role.setName(rs.getString("Name"));
                account.setStatus(rs.getBoolean("Status"));
                account.setRole(role);

                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editAccount(Account acc) {
        String sql = "UPDATE [Account]\n"
                + "   SET [FirstName] = ?\n"
                + "      ,[LastName] = ?\n"
                + "      ,[ProfilePictureUrl] = ?\n"
                + "      ,[RoleID] = ?\n"
                + "      ,[Phone] = ?\n"
                + "      ,[Address] = ?\n"
                + "      ,[Gender] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[ModifiedTime] = ?\n"
                + " WHERE [AccountID] = ?";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(10, acc.getAccountID());
            stm.setString(1, acc.getFirstName());
            stm.setString(2, acc.getLastName());
            stm.setString(3, acc.getProfilePictureUrl());
            stm.setInt(4, acc.getRole().getId());
            stm.setString(5, acc.getPhone());
            stm.setString(6, acc.getAddress());
            stm.setBoolean(7, acc.getGender().asBoolean());
            stm.setBoolean(8, acc.getStatus());
            stm.setTimestamp(9, acc.getModifiedTime());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addAccount(Account s) {
        int accountid = -1;
        String sql = "INSERT INTO [Account]\n"
                + "           ([FirstName]\n"
                + "           ,[LastName]\n"
                + "           ,[Email]\n"
                + "           ,[Balance]\n"
                + "           ,[ProfilePictureUrl]\n"
                + "           ,[RoleID]\n"
                + "           ,[CreatedTime]\n"
                + "           ,[Phone]\n"
                + "           ,[Address]\n"
                + "           ,[Gender]\n"
                + "           ,[Status])\n"
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
                + "           ,?)";

        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, s.getFirstName());
            stm.setString(2, s.getLastName());
            stm.setString(3, s.getEmail());
            stm.setBigDecimal(4, s.getBalance());
            stm.setString(5, s.getProfilePictureUrl());
            stm.setInt(6, s.getRole().getId());
            stm.setTimestamp(7, s.getCreatedTime());
            stm.setString(8, s.getPhone());
            stm.setString(9, s.getAddress());
            stm.setBoolean(10, s.getGender().asBoolean());
            stm.setBoolean(11, s.getStatus());

            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                accountid = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (accountid != -1) {
            String sql2 = "INSERT INTO [Password]([AccountID],[PasswordHash]) \n"
                    + "VALUES (?,?)";

            PreparedStatement stm2 = null;
            try {
                stm2 = connection.prepareStatement(sql2);
                stm2.setInt(1, accountid);
                stm2.setString(2, s.getPassword());

                stm2.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Account getAccountLast() {
        try {
            String sql = "SELECT * FROM Account WHERE AccountID = (SELECT MAX(AccountID) FROM Account)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AccountID"));

                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setStatusAccount(int id, boolean status) {
        try {
            String sql = "UPDATE [dbo].[Account] SET [Status] = ? WHERE AccountID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, status);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void minusBalance(Account account) {
        try {
            String sql = "UPDATE [Account] SET [Balance] = ? WHERE AccountID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBigDecimal(1, account.getBalance());
            stm.setInt(2, account.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Account getAccountForLogin(String email, String password) {
        try {
            String sql = "SELECT a.*, p.PasswordHash, Name"
                    + " FROM [Account] a JOIN [Password] p \n"
                    + " ON a.AccountID = p.AccountID\n"
                    + " JOIN [Role] r\n"
                    + " ON a.RoleID = r.RoleID\n"
                    + " WHERE a.[Email] = ? AND p.[PasswordHash] = ? AND A.Status = 1";
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

    public void deleteAccountById(int id) {
        try {
            String sql = "Delete from [Account] WHERE AccountID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
