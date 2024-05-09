package com.edubrawlers.game.equations;

public class Equations {
    private final String equation;
    private final String[] options;
    private final int correctAnswerIndex;

    public Equations(String equation, String[] options, int correctAnswerIndex) {
        this.equation = equation;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getEquation() {
        return equation;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswerIndex;
    }
}