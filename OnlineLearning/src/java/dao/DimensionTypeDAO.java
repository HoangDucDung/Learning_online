package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DimensionType;

public class DimensionTypeDAO extends DBContext {

    public ArrayList<DimensionType> getAllDimensionTypes() {
        ArrayList<DimensionType> dimensionTypes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dbo.DimensionType";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                DimensionType dimensionType = new DimensionType();
                dimensionType.setTypeID(rs.getInt("TypeID"));
                dimensionType.setName(rs.getString("Name"));
                dimensionTypes.add(dimensionType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensionTypes;
    }
}
