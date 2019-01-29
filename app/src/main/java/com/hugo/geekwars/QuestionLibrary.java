package com.hugo.geekwars;

public class QuestionLibrary {

    public static int numQuestions;

    private String mQuestions [] = {
            "¿Quién Construyó a 3CPO?",
            "¿En cuantos parsecs han solo realizó la carrera kessel?",
            "Obi-wan Kenobi era aprendiz de...",
            "Obi-wan Kenobi nació en el planeta..."
    };


    private String mChoices [][] = {
            {"Los Jawas", "Anakin Skywalker", "Princesa Amidala"},
            {"12", "14", "16"},
            {"Mace Windu", "Qui-gon Jinn", "Yoda"},
            {"Alderaan", "Coruscant", "Stewjon"}
    };



    private String mCorrectAnswers[] = {"Anakin Skywalker", "12", "Qui-gon Jinn", "Stewjon"};

    public QuestionLibrary() {
        numQuestions = mQuestions.length;
    }

    public QuestionLibrary(String[] a, String [][]b, String []c ) {
        this.setQuestion(a);
        this.mChoices = b;
        this.setCorrectAnswer(c);
        this.numQuestions = this.mQuestions.length;
    }

    public void setQuestion(String[] questions)
    {
        this.mQuestions = questions;
    }

    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }


    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }

    public void setCorrectAnswer(String[] answers)
    {
        this.mCorrectAnswers = answers;
    }


    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }
}
