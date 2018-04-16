package modelClasses;

public class Match {
    private Team[] teams;
    private Team winner;

    public Match(Team t1, Team t2) {
        teams = new Team[2];
        teams[0] = t1;
        teams[1] = t2;
    }

    public Match(Team t1){
        teams = new Team[1];
        teams[0] = t1;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public Team[] getTeams() {
        return teams;
    }
}
