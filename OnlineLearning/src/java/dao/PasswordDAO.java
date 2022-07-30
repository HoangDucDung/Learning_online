package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordDAO extends DBContext {

    public void changePassword(int accountID, String newPassword) {
        String sql = "UPDATE [dbo].[Password] SET [PasswordHash] = ? WHERE AccountID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setInt(2, accountID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PasswordDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertPassword(int accountId, String password) {
        PreparedStatement stm = null;
        try {
            String sqlPassword = "INSERT INTO [Password]([AccountID],[PasswordHash]) \n" 
                    + "VALUES (?,?)";
            stm = connection.prepareStatement(sqlPassword);
            stm.setInt(1, accountId);
            stm.setString(2, password);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
