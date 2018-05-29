package modelClasses;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Tournament2  extends ViewModel implements ITournamentService, Serializable {
    private List<Team> teams;
    private Boolean isRandom;
    private Random rnd;
    private int currentRound, maxRounds, firstRoundMatches, cupLimit;
    private List<Match2> onGoingMatches, concludedMatches;
    private Set<Integer> teamsPickedThisRound;

    public Tournament2() {
        this.isRandom = false;
        this.rnd = new Random();
        this.currentRound = 1;
        this.teams = new ArrayList();
        this.onGoingMatches = new ArrayList();
        this.concludedMatches = new ArrayList();
        this.teamsPickedThisRound = new HashSet();
    }

    /**
     * Returns false if a team with the same name already exists.
     * @param team
     * @return
     */
    public boolean addTeam(Team team) {
        for (int i = 0; i < this.teams.size(); i++) {
            if (this.teams.get(i).getName().equals(team.getName()))
                return false;
        }

        Log.d("Teams Size", Integer.toString(this.teams.size()));
        return this.teams.add(team);
    }

    public List<Team> getAllTeams(){
        return this.teams;
    }


    /**
     * Returns false if the teamName does not exist in the tournament.
     * @param teamName
     * @return
     */
    public boolean removeTeam(String teamName) {
        for (int i = 0; i < this.teams.size(); i++) {
            if (this.teams.get(i).getName().equals(teamName)) {
                this.teams.remove(i);
                return true;
            }
        }

        return false;
    }

    public void setRandom(Boolean bool) {
        if (bool)
            this.rnd = new Random();
        this.isRandom = bool;
    }

    public void setCupLimit(int limit) {
        this.cupLimit = limit;
    }
    public int getCupLimit() {
        return this.cupLimit;
    }
    public List<Match2> getConcludedMatches() {
        return this.concludedMatches;
    }

    public void start() {
        //Maybe change a boolean to be checked in fx addTeam and stuff, so that method is no longer doing anything

        int teams = this.teams.size();
        Log.d("Teams size", Integer.toString(teams));
        Log.d("Teams size", Integer.toString(teams));
        if (teams == 4) {
            this.maxRounds = 2;
            this.firstRoundMatches = 2;
        }
        else if (teams < 9) {
            this.maxRounds = 3;
            this.firstRoundMatches = teams - 4;
        }
        else if (teams < 17) {
            this.maxRounds = 4;
            this.firstRoundMatches = teams - 8;
        }
        else if (teams < 33) {
            this.maxRounds = 5;
            this.firstRoundMatches = teams - 16;
        }

        this.loadMatchesForRound();
    }
    public int getCurrentRound() {
        return this.currentRound;
    }
    public int getMaxRounds() {
        return this.maxRounds;
    }

    /**
     * Should be called after tournament has ended and before each call on
     * getMatchesForRound(int round).
     * @param round
     * @return
     */
    public boolean hasRound(int round) {
        return this.maxRounds >= round;
    }

    /**
     * Should only be called if hasRound(int round) returns true
     * for the same round.
     * @param round
     * @return
     */
    public List<Match2> getMathcesForRound(int round) {
        List<Match2> theMatches = new ArrayList();

        for (Match2 m : this.concludedMatches) {
            if (m.getEndRound() == round)
                theMatches.add(m);
        }

        return theMatches;
    }


    private void loadMatchesForRound() {
        Log.d("loadMatchesForRound()", "Start");
        this.teamsPickedThisRound.clear();
        if (this.currentRound == 1) {
            Log.d("loadMatchesForRound()", Integer.toString(this.firstRoundMatches));
            for (int i = 0; i < this.firstRoundMatches; i++) {
                Log.d("loadMatchesForRound()", "onGoingMatches");
                this.onGoingMatches.add(new Match2(this.getNextTeam(), this.getNextTeam(), UUID.randomUUID()));
            }
        } else {
            for (int i = 0; i < this.teams.size() / 2; i++) {
                Log.d("loadMatchesForRound()", "not round 1");
                this.onGoingMatches.add(new Match2(this.getNextTeam(), this.getNextTeam(), UUID.randomUUID()));
            }
        }

    }
    private Team getNextTeam() {
        if (this.teams.isEmpty()) {
            return null;
        }

        int index;
        do {
            index = this.rnd.nextInt(this.teams.size());
        } while (this.teamsPickedThisRound.contains(index));
        this.teamsPickedThisRound.add(index);

        return this.teams.get(index);
    }

    public Match2 getNextMatch() {
        if (!this.onGoingMatches.isEmpty()) {
            return this.onGoingMatches.get(0);
        }
        return null;
    }


    public void concludeMatch(Match2 match) {
        //Update the match with the new score
        for (int i = 0; i < this.onGoingMatches.size(); i++) {
            if (this.onGoingMatches.get(i).getId().equals(match.getId())) {
                this.onGoingMatches.remove(i);
                this.concludedMatches.add(match);
                this.teams.remove(match.getLoser());
                System.out.println("Round: " + this.currentRound + "\nMatches: " + this.concludedMatches.size());
                break;
            }
        }

        this.setWinner(match.getWinner());
    }

    public void setWinner(Team team) {
        if (this.onGoingMatches.isEmpty()) {
            this.currentRound++;
            this.loadMatchesForRound();
        }
    }
}
