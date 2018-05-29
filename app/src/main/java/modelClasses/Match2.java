package modelClasses;

import java.util.UUID;

public class Match2 {
    private Team team1, team2, winTeam, loseTeam;
    private int team1Score, team2Score, endRound;
    private UUID id;

    public Match2(Team team1, Team team2, UUID id) {
        this.team1 = team1;
        this.team2 = team2;
        this.id = id;
        this.team1Score = 0;
        this.team2Score = 0;
    }

    public Team getTeam1() {
        return this.team1;
    }

    public Team getTeam2() {
        return this.team2;
    }

    public UUID getId() {
        return this.id;
    }

    public void setTeamScore(Team team, int score) {
        if (this.team1.equals(team))
            this.team1Score = score;
        else if (this.team2.equals(team))
            this.team2Score = score;
    }
    public int getTeamScore(Team team) {
        if (this.team1.equals(team))
            return this.team1Score;
        else if (this.team2.equals(team))
            return this.team2Score;

        return -1;
    }
    public int getEndRound() {
        return this.endRound;
    }

    public void setMatchWinner(Team winnerTeam, int round) {
        if (this.team1.equals(winnerTeam)) {
            this.winTeam = this.team1;
            this.loseTeam = this.team2;
        }
        else if (this.team2.equals(winnerTeam)) {
            this.winTeam = this.team2;
            this.loseTeam = this.team1;
        }

        this.endRound = round;
    }

    public Team getWinner() {
        if (this.winTeam == null) {
            if (this.team1Score > this.team2Score)
                return this.team1;
            else if (this.team2Score > this.team1Score)
                return this.team2;
            else
                return null;
        }
        return null;
    }

    public Team getLoser() {
        if (this.winTeam == null) {
            if (this.team1Score < this.team2Score)
                return this.team1;
            else if (this.team2Score < this.team1Score)
                return this.team2;
            else
                return null;
        } else if (this.winTeam.equals(this.team1))
            return this.team2;
        else if (this.winTeam.equals(this.team2))
            return this.team1;

        return null;
    }

    public boolean isWinner(Team team) {
        return this.winTeam.equals(team);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String teamNames = String.format("%1$1s%2$40s", this.winTeam.getName(), this.loseTeam.getName());
        String scores = String.format("%1$20s%2$50s", this.getTeamScore(this.winTeam), this.getTeamScore(this.loseTeam));
        //String teamNames = this.winTeam.getName() + "\t\t\t" + this.loseTeam.getName();
        //String scores = this.getTeamScore(this.winTeam) + "\t\t\t" + this.getTeamScore(this.loseTeam);
        str.append(teamNames)
                .append("\n")
                .append(scores);

        return new String(str);
    }
}
