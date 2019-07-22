package fr.tf_i.model;

public class User {
    String mFirstname;
    int mNumberOfQuestions;
    int mScore = -1;
    int mPercentage;

    //SCORE Getter/Setter
    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    //NUMBER OF QUESTIONS Getter/Setter
    public int getNumberOfQuestions() {
        return mNumberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        mNumberOfQuestions = numberOfQuestions;
    }

    // FIRST NAME Getter/Setter
    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    //PERCENTAGE Getter/Setter
    public int getPercentage() {
        return mPercentage;
    }

    public void setPercentage(int percentage) {
        mPercentage = percentage;
    }
}
