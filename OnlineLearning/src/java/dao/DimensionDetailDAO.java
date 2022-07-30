package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DimensionDetail;

public class DimensionDetailDAO extends DBContext {

    public ArrayList<DimensionDetail> getDimensionDetail(int lessonID) {
        ArrayList<DimensionDetail> dimensionDetails = new ArrayList<>();
        try {
            String sql = "with t1 as (\n" +
"                     select count(CompletedQuestion.SelectedAnswerID) as 'NumberCorrect', Dimension.Name as DimensionName, Dimension.DimensionID as DimensionID, DimensionType.TypeID as TypeID\n" +
"                     from CompletedQuestion join Question on CompletedQuestion.QuestionID = Question.QuestionID\n" +
"                     join Dimension on Question.QuestionID = Dimension.QuestionID\n" +
"                    join DimensionType on Dimension.TypeID = DimensionType.TypeID\n" +
"                    where CompletedQuestion.Status = 1 and Question.LessonID = ?\n" +
"                    group by Dimension.Name, Dimension.DimensionID, DimensionType.TypeID\n" +
"                    ),t2 as(\n" +
"                    select count(CompletedQuestion.SelectedAnswerID) as 'Number', Dimension.Name as DimensionName2 , Dimension.DimensionID as DimensionID2, DimensionType.TypeID as TypeID2\n" +
"                    from CompletedQuestion join Question on CompletedQuestion.QuestionID = Question.QuestionID\n" +
"                    join Dimension on Question.QuestionID = Dimension.QuestionID\n" +
"                    join DimensionType on Dimension.TypeID = DimensionType.TypeID\n" +
"                    where Question.LessonID =?\n" +
"                    group by Dimension.Name, Dimension.DimensionID,DimensionType.TypeID\n" +
"                    )\n" +
"                    select * from t1\n" +
"                    inner join t2 on t1.DimensionID = t2.DimensionID2"
                    + "";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonID);
            stm.setInt(2, lessonID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                DimensionDetail dimensionDetail = new DimensionDetail();
                dimensionDetail.setDimensionID(rs.getInt("DimensionID"));
                dimensionDetail.setDimensionName(rs.getString("DimensionName"));
                dimensionDetail.setNumberCorrect(rs.getInt("NumberCorrect"));
                dimensionDetail.setNumber(rs.getInt("Number"));
                dimensionDetail.setTypeID(rs.getInt("TypeID"));

                dimensionDetails.add(dimensionDetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dimensionDetails;
    }

}
