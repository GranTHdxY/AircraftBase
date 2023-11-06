package edu.hitsz.Dao;

public class PlayerScore {
    private String PlayerName;
    private int score;
    private String time;
    private String degree;
    public PlayerScore(String PlayerName, int score, String time, String degree) {
        this.PlayerName = PlayerName;
        this.score =score;
        this.time = time;
        this.degree = degree;

    }
    public String getPlayerName(){return PlayerName;}
    public int getScore(){return score;}
    public String getTime(){return time;}
    public String gerDegree(){return degree;}

}
