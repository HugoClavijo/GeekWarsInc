package com.hugo.geekwars;

public class RankingLib {
    private String userName;
    private String userId;
    private int score;


    public RankingLib() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public RankingLib(String username, String userId, Integer userScore) {
        this.userName = username;
        this.userId = userId;
        this.score = userScore;
    }


    public String getUsername() {
        return userName;
    }

    public void setUsername(String name) {
        this.userName = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int userScore) {
        this.score = userScore;
    }

}
