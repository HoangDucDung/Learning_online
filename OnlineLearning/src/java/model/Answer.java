package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("text")
    private String text;

    @Expose
    @SerializedName("explain")
    private String explain;
    private int questionId;
    private int status;

    private Question question;

    public Answer() {
    }

    public Answer(int id, String text, String explain) {
        this.id = id;
        this.text = text;
        this.explain = explain;
    }

    public Answer(int AnswerID, String AnswerText, String Explain, int Status, Question QuestionID) {
        this.id = AnswerID;
        this.text = AnswerText;
        this.explain = Explain;
        this.status = Status;
        this.question = QuestionID;
    }

    /**
     * Use getId()
     *
     * @return
     * @deprecated
     */
    @Deprecated
    public int getAnswerID() {
        return id;
    }

    /**
     * Use setId()
     *
     * @param AnswerID
     * @deprecated
     */
    @Deprecated
    public void setAnswerID(int AnswerID) {
        this.id = AnswerID;
    }

    /**
     * Use getText()
     *
     * @return
     * @deprecated
     */
    @Deprecated
    public String getAnswerText() {
        return text;
    }

    /**
     * Use setText()
     *
     * @param AnswerText
     */
    @Deprecated
    public void setAnswerText(String AnswerText) {
        this.text = AnswerText;
    }

    public String getExplain() {
        return explain;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        this.status = Status;
    }

    /**
     * Wrong name. Should be getQuestion
     * @return
     * @deprecated
     */
    @Deprecated
    public Question getQuestionID() {
        return question;
    }

    @Deprecated
    public void setQuestionID(Question QuestionID) {
        this.question = QuestionID;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Answer other = (Answer) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("id: %s, text: %s, status: %s, questionId: %s", id, text, status, questionId);
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
