package model;

public class CompletedQuestion {

    private int accountID;
    private Question questionID;
    private int selectedAnswerID;
    private int status;

    public CompletedQuestion() {
    }

    public CompletedQuestion(int AccountID, Question QuestionID, int SelectedAnswerID, int Status) {
        this.accountID = AccountID;
        this.questionID = QuestionID;
        this.selectedAnswerID = SelectedAnswerID;
        this.status = Status;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int AccountID) {
        this.accountID = AccountID;
    }

    public Question getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Question QuestionID) {
        this.questionID = QuestionID;
    }

    public int getSelectedAnswerID() {
        return selectedAnswerID;
    }

    public void setSelectedAnswerID(int SelectedAnswerID) {
        this.selectedAnswerID = SelectedAnswerID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int Status) {
        this.status = Status;
    }

}
