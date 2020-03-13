package com.example.sepakbola;

public class Schedule {
    private String home;
    private String away;
    private String awayScore;
    private String homeScore;
    private String date;
    private String goalHomeDetails;
    private String goalAwayDetails;
    private String homeTeamId;

    public Schedule(String home, String away, String homeScore, String awayScore, String date, String goalHomeDetails, String goalAwayDetails, String homeTeamId){
        this.home = home;
        this.away = away;
        this.awayScore = awayScore;
        this.homeScore = homeScore;
        this.date = date;
        this.goalHomeDetails = goalHomeDetails;
        this.goalAwayDetails = goalAwayDetails;
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamId(){
        return this.homeTeamId;
    }

    public String getHome() {
        return this.home;
    }
    public String getGoalHomeDetails(){
        return this.goalHomeDetails;
    }
    public String getGoalAwayDetails(){
        return this.goalAwayDetails;
    }

    public String getAway(){
        return this.away;
    }

    public String getDate(){
        return this.date;
    }

    public String getAwayScore(){
        return this.awayScore;
    }

    public String getHomeScore(){
        return this.homeScore;
    }
}
