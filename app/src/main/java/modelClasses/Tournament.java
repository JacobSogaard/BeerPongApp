package modelClasses;

import java.util.ArrayList;

public class Tournament {
    private ArrayList<Team> allTeams;
    private ArrayList<Match> matches;

    public Tournament() {
        this.allTeams = new ArrayList();
    }

    public boolean addTeam(Team t) {
        return allTeams.add(t);
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }
}
