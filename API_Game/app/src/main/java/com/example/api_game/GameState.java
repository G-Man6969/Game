package com.example.api_game;

public enum GameState {
    INSTANCE;

    private int score;
    private int iterations;

    private GameState()
    {
        score = 0;
        iterations = 0;
    }

    public int getIterations() {
        return iterations;
    }

    public int getScore() {
        return score;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
