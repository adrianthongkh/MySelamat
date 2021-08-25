package com.myselamat.model;

public class RiskEvaluationQuestionBook {

    public static String[] questions = new String [] {
            "1. Have you been having Fever, Chills and Shivering for the past 3 days?",
            "2. Are you experiencing any Body Aches, Head Aches and Fatigue?",
            "3. Are you exhibiting Sore Throat, Runny Nose and Nausea?",
            "4. Are you Coughing and having difficulty in Breathing?",
            "5. Have you lost your Sense of Smell and Taste?",
            "6. Have you been in contact/associated with a known COVID-19 cluster or confirmed case?",
    };

    public static boolean[] answers = new boolean[]{
            true, true, true, true, true, true
    };
}

