package com.myselamat.model;

import java.io.Serializable;

public class IsolationSurvey implements Serializable {

    private Boolean question1;
    private Boolean question2;
    private Boolean question3;
    private Boolean question4;
    private Boolean question5;

    private boolean severity;

    public IsolationSurvey() {
    }

    public boolean isSeverity() { return severity; }

    public void setSeverity(boolean severity) { this.severity = severity; }

    public String getWarning() { return "Please answer the following questions with honesty as this assessment concerns your health."; }

    public String getQuestion1String() { return "Are you currently experiencing fever or headache?"; }

    public String getQuestion2String() { return "Are you currently experiencing difficulties in breathing AND/OR shortness of breath?"; }

    public String getQuestion3String() { return "Are you currently experiencing nausea and vomiting?"; }

    public String getQuestion4String() { return "Are you experiencing pain or pressure in your chest?"; }

    public String getQuestion5String() { return "Do you experience any signs of dehydration? (e.g. dry skin, dry mouth, decreased urine output)?"; }

    public Boolean getQuestion1() { return question1; }

    public void setQuestion1(Boolean question1) { this.question1 = question1; }

    public Boolean getQuestion2() { return question2; }

    public void setQuestion2(Boolean question2) {
        this.question2 = question2;
    }

    public Boolean getQuestion3() { return question3; }

    public void setQuestion3(Boolean question3) { this.question3 = question3; }

    public Boolean getQuestion4() { return question4; }

    public void setQuestion4(Boolean question4) { this.question4 = question4; }

    public Boolean getQuestion5() { return question5; }

    public void setQuestion5(Boolean question5) { this.question5 = question5; }


    public double calculateSeverity() {

        double score = 0;

        if (question1)
            score += 0.1;       // Fever weightage : 5%
        if (question2)
            score += 0.5;       // Shortness of breath weightage : 50%
        if (question3)
            score += 0.15;      // Nausea and vomiting weights : 15%
        if (question4)
            score += 0.2;       // Chest pain and pressure weightage : 20%
        if (question5)
            score += 0.1;       // Dehydration weightage : 10%

        return score;
    }

    public String getMessageFromScore() {

        double score = calculateSeverity();
        String s = "";
        if (score == 0)
            s = "No symptoms detected!\nRemember to stay hydrated and practice caution at all times!";
        else if (score > 0 && score <= 0.2)
            s = "Mild symptoms of Covid-19 detected.\nConsume Paracetamol if necessary.";
        else if (score > 0.2 && score < 0.5)
            s = "Consult your nearest Covid-19 clinic \nfor further medical assistance \nif symptoms complicates";
        else if (score >= 0.5) {
            s = "Medical assistance is required.\nClick Button below and call for help!";
            setSeverity(true);
        }

        return s;
    }
}
