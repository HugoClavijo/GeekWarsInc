package com.hugo.geekwars;

public class RankingLib {
    private String username;
    private String id;
    private int score;


    public RankingLib() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public RankingLib(String username, String userId, Integer userScore) {
        this.username = username;
        this.id = userId;
        this.score = userScore;
    }
}
