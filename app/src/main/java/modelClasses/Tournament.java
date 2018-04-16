package modelClasses;

import java.util.ArrayList;

public class Tournament {
    private ArrayList<Team> allTeams;
    private ArrayList<Match> matches;

    public Tournament() {
        this.allTeams = new ArrayList();
    }

    public boolean addTeam(Team t) {
        for (Team team : this.allTeams) {
            if (team.equals(t))
                return false;
        }

        this.allTeams.add(t);
        return true;
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public ArrayList<Team> getAllTeams() {
        return allTeams;
    }
}
