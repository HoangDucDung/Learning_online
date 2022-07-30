package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Slider;

public class SliderDAO extends DBContext {

    public ArrayList<Slider> getSliders() {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT s.SliderID, s.Title, s.SubTitle, s.Description, s.NavigationLink, s.ImageUrl, s.[Order], s.Status, s.SliderCollectionID\n"
                    + "FROM dbo.Slider s INNER JOIN dbo.SliderCollection sc ON sc.SliderCollectionID = s.SliderCollectionID\n"
                    + "WHERE sc.Status = 1 AND s.Status = 1\n"
                    + "ORDER BY sc.[Order] ASC, s.[Order] ASC";
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

}
