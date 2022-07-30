package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Slider;

public class SliderDAO extends DBContext {

    public ArrayList<Slider> getSlidersDisplay() {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dbo.Slider WHERE Status = 1 ORDER BY SliderID DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSliderID(rs.getInt("SliderID"));
                slider.setTitle(rs.getString("Title"));
                slider.setSubTitle(rs.getString("SubTitle"));
                slider.setDescription(rs.getString("Description"));
                slider.setNavigationLink(rs.getString("NavigationLink"));
                slider.setImageUrl(rs.getString("ImageUrl"));
                slider.setOrder(rs.getInt("Order"));
                slider.setStatus(rs.getBoolean("Status"));
                sliders.add(slider);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public ArrayList<Slider> getAllSliders() {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dbo.Slider ORDER BY SliderID DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSliderID(rs.getInt("SliderID"));
                slider.setTitle(rs.getString("Title"));
                slider.setSubTitle(rs.getString("SubTitle"));
                slider.setDescription(rs.getString("Description"));
                slider.setNavigationLink(rs.getString("NavigationLink"));
                slider.setImageUrl(rs.getString("ImageUrl"));
                slider.setOrder(rs.getInt("Order"));
                slider.setStatus(rs.getBoolean("Status"));
                sliders.add(slider);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public int countAllSliders() {
        try {
            String sql = "SELECT COUNT(*) SlideQuantity FROM dbo.Slider ";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("SlideQuantity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Slider getSliderById(int id) {
        Slider slider = new Slider();
        try {
            String sql = "SELECT * FROM dbo.Slider WHERE SliderID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                slider.setSliderID(rs.getInt("SliderID"));
                slider.setTitle(rs.getString("Title"));
                slider.setSubTitle(rs.getString("SubTitle"));
                slider.setDescription(rs.getString("Description"));
                slider.setNavigationLink(rs.getString("NavigationLink"));
                slider.setImageUrl(rs.getString("ImageUrl"));
                slider.setOrder(rs.getInt("Order"));
                slider.setStatus(rs.getBoolean("Status"));
                return slider;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return slider;
    }

    public ArrayList<Slider> getSlidersByStatus(String status) {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dbo.Slider WHERE Status = ? ORDER BY SliderID DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, Boolean.parseBoolean(status));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSliderID(rs.getInt("SliderID"));
                slider.setTitle(rs.getString("Title"));
                slider.setSubTitle(rs.getString("SubTitle"));
                slider.setDescription(rs.getString("Description"));
                slider.setNavigationLink(rs.getString("NavigationLink"));
                slider.setImageUrl(rs.getString("ImageUrl"));
                slider.setOrder(rs.getInt("Order"));
                slider.setStatus(rs.getBoolean("Status"));
                sliders.add(slider);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public int countSlidersByStatus(String status) {
        try {
            String sql = "SELECT COUNT(SliderID) SlideQuantity FROM dbo.Slider WHERE Status = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, Boolean.parseBoolean(status));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("SlideQuantity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Slider> getSlidersByTitleOrBacklink(String text) {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dbo.Slider WHERE Title LIKE ? OR NavigationLink LIKE ? ORDER BY SliderID DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + text + "%");
            stm.setString(2, "%" + text + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setSliderID(rs.getInt("SliderID"));
                slider.setTitle(rs.getString("Title"));
                slider.setSubTitle(rs.getString("SubTitle"));
                slider.setDescription(rs.getString("Description"));
                slider.setNavigationLink(rs.getString("NavigationLink"));
                slider.setImageUrl(rs.getString("ImageUrl"));
                slider.setOrder(rs.getInt("Order"));
                slider.setStatus(rs.getBoolean("Status"));
                sliders.add(slider);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public int countSlidersByTitleOrBacklink(String text) {
        try {
            String sql = "SELECT COUNT(SliderID) SlideQuantity FROM dbo.Slider WHERE Title LIKE ? OR NavigationLink LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + text + "%");
            stm.setString(2, "%" + text + "%");
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("SlideQuantity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void setStatusSlider(int id, boolean status) {
        try {
            String sql = "UPDATE [dbo].[Slider] SET [Status] = ? WHERE SliderID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, status);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSlider(int id) {
        try {
            String sql = "DELETE FROM [dbo].[Slider] WHERE SliderID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertSlider(Slider slider) {
        try {
            String sql = "INSERT INTO [dbo].[Slider]\n"
                    + "           ([Title]\n"
                    + "           ,[SubTitle]\n"
                    + "           ,[Description]\n"
                    + "           ,[NavigationLink]\n"
                    + "           ,[ImageUrl]\n"
                    + "           ,[Order]\n"
                    + "           ,[Status]\n)"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,1\n"
                    + "           ,1)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, slider.getTitle());
            stm.setString(2, slider.getSubTitle());
            stm.setString(3, slider.getDescription());
            stm.setString(4, slider.getNavigationLink());
            stm.setString(5, slider.getImageUrl());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSlider(Slider slider) {
        try {
            String sql = "UPDATE [dbo].[Slider]\n"
                    + "   SET [Title] = ?\n"
                    + "      ,[SubTitle] = ?\n"
                    + "      ,[Description] = ?\n"
                    + "      ,[NavigationLink] = ?\n"
                    + "      ,[ImageUrl] = ?\n"
                    + " WHERE SliderID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, slider.getTitle());
            stm.setString(2, slider.getSubTitle());
            stm.setString(3, slider.getDescription());
            stm.setString(4, slider.getNavigationLink());
            stm.setString(5, slider.getImageUrl());
            stm.setInt(6, slider.getSliderID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
