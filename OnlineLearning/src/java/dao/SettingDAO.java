package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Setting;

public class SettingDAO extends DBContext {

    public ArrayList<Setting> getAllSettings() {
        ArrayList<Setting> settings = new ArrayList<>();
        try {
            String sql = "select * from Setting ";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setName(rs.getString("Name"));
                setting.setOrder(rs.getInt("Order"));
                setting.setSettingID(rs.getInt("SettingID"));
                if (rs.getInt("Status") == 1) {
                    setting.setStatus(true);
                } else {
                    setting.setStatus(false);
                }
                setting.setType(rs.getString("Type"));

                settings.add(setting);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return settings;
    }

    public int count() {
        try {
            String sql = "SELECT count(*) as Total FROM Setting";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Setting> searchby_value(String value) {
        ArrayList<Setting> settings = new ArrayList<>();
        try {
            String sql = "select * from Setting \n"
                    + "where Name = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, value);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting setting = new Setting();
                setting.setSettingID(rs.getInt("SettingID"));
                setting.setId(rs.getInt("id"));
                setting.setName(rs.getString("Name"));
                setting.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    setting.setStatus(true);
                } else {
                    setting.setStatus(false);
                }
                setting.setType(rs.getString("Type"));

                settings.add(setting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return settings;
    }

    public Setting searchby_SubID(int id) {
        try {
            String sql = "select * from setting\n"
                    + "where settingID = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setName(rs.getString("Name"));
                setting.setOrder(rs.getInt("Order"));
                setting.setSettingID(rs.getInt("SettingID"));
                if (rs.getInt("Status") == 1) {
                    setting.setStatus(true);
                } else {
                    setting.setStatus(false);
                }
                setting.setType(rs.getString("Type"));

                return setting;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> getAllSettings_DistinctType() {
        ArrayList<Setting> settings = new ArrayList<>();
        try {
            String sql = "select distinct Type from Setting";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Setting setting = new Setting();
                setting.setType(rs.getString("Type"));
                settings.add(setting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return settings;
    }

    public ArrayList<Setting> searchBy_Filter(String cid, String status) {
        ArrayList<Setting> settings = new ArrayList<>();
        try {
            String sql = "select * from Setting ";
            if (!cid.equalsIgnoreCase("-1") && !status.equalsIgnoreCase("-1")) {
                sql += "where type = ? and status = ?";

            } else if (!cid.equalsIgnoreCase("-1")) {
                sql += "where type = ?";

            } else if (cid.equalsIgnoreCase("-1")) {
                sql += "where status = ?";

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
                Setting setting = new Setting();
                setting.setSettingID(rs.getInt("SettingID"));
                setting.setId(rs.getInt("id"));
                setting.setName(rs.getString("Name"));
                setting.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    setting.setStatus(true);
                } else {
                    setting.setStatus(false);
                }
                setting.setType(rs.getString("Type"));
                settings.add(setting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return settings;
    }

    public void insertSetting(Setting s) {
        String sql = "INSERT INTO [Setting]\n"
                + "           ([id]\n"
                + "           ,[Name]\n"
                + "           ,[Order]\n"
                + "           ,[Status]\n"
                + "           ,[Type])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, s.getId());
            stm.setString(2, s.getName());
            stm.setInt(3, s.getOrder());
            stm.setBoolean(4, s.getStatus());
            stm.setString(5, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateSetting(Setting s) {
        String sql = "UPDATE [Setting]\n"
                + "   SET \n"
                + "      [Name] = ?\n"
                + "      ,[Order] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[Type] = ?\n"
                + " WHERE SettingID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getSettingID());
            stm.setString(1, s.getName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.getStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteSetting(int id) {
        String sql = "DELETE Setting"
                + " WHERE SettingID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
