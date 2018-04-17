package modelClasses;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament implements ITournamentService, Serializable {
    private ArrayList<Team> allTeams;
    private ArrayList<Match> matches;
    private double numberOfRounds;
    private Map<Double, ArrayList<Match>> rounds;
    private int numberOfTeams;
    private int currentMatch;
    private double currentRound;
    private boolean oneTeamMatch, tournamentHasEnded;


    public Tournament() {
        this.allTeams = new ArrayList();

    }

    @Override
    public boolean addTeam(Team t) {
        for (Team team : this.allTeams) {
            if (team.equals(t))
                return false;
        }

        this.allTeams.add(t);
        return true;
    }

    public void addTeams(ArrayList<Team> teams){
        this.allTeams = teams;
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public ArrayList<Team> getAllTeams() {
        return allTeams;
    }

    private void initTournament(){

        this.rounds = new HashMap();
        this.matches = new ArrayList<>();
        this.numberOfTeams = this.allTeams.size();
        this.numberOfRounds = Math.floor(Math.log(this.numberOfTeams - 1) / Math.log(2));

        for(double i = numberOfRounds; i >= 0; i--){
            ArrayList<Match> matchList = new ArrayList();
            this.rounds.put(i, matchList);


        }



        this.initMatches();
        this.initSecondRound();
        this.initFirstRound();

    }


    private void initSecondRound(){
        int i;
        double roundTwo = numberOfRounds - (numberOfRounds - 2);
        //If number of teams is 1, 2, 4, 8 or 16, 1 round will contain all matches -
        // else 2. round will be filled with matches and rest in 1 round
        if (this.numberOfTeams != 1 && this.numberOfTeams != 2 && this.numberOfTeams != 4
                && this.numberOfTeams != 8 && this.numberOfTeams != 16) {
            //checks if matches has 1 match with only one team
            if (this.matches.get(this.matches.size() - 1).getTeams().length == 1){
                this.rounds.get(roundTwo).add(this.matches.get(this.matches.size() - 1));
                this.matches.remove(this.matches.size() - 1);
                this.oneTeamMatch = true;
                i = 1;
            }
            //iterates through and adds all matches in second round
            for (i = 0; i <= this.rounds.get(roundTwo).size() - 1; i++){
                this.rounds.get(roundTwo).add(this.matches.get(i));
                this.matches.remove(i);
            }
        }
    }

    private void initFirstRound(){
        double roundOne = numberOfRounds - (numberOfRounds - 1);
        this.matches.trimToSize();
        this.rounds.put(roundOne, matches);


    }

    @Override
    public boolean start(){
        this.initTournament();
        this.currentMatch = 0;
        this.currentRound = 1;
        return true;
    }


    /**
     * Set the winner of a current match.
     * @param winner Winner of type Team
     */
    @Override
    public void setWinner(Team winner){
        this.rounds.get(this.currentRound).get(this.currentMatch).setWinner(winner);
    }

    /**
     * Method to get the next match in the tournament. If current match is the last in round,
     * next round is initialized and the first match in that round is returned. Else next match in current round
     * @return Next match of type match
     */
    @Override
    public Match getNextMatch(){

        if(this.currentRound == this.numberOfRounds){
            this.tournamentHasEnded = true;
            return this.getCurrentMatch();
        }
        //If round has ended
        if (this.rounds.get(this.currentRound).size() - 1 == currentMatch){

            this.initNextRound();
            return this.rounds.get(this.currentRound).get(this.currentMatch);
        }

        //Next match
        this.currentMatch++;
        return this.getCurrentMatch();
    }

    public Match getCurrentMatch(){

        return this.rounds.get(this.currentRound).get(this.currentMatch);
    }

    public void setCurrentMatchWinner(Team winner){
        this.getCurrentMatch().setWinner(winner);
    }

    public boolean getTournamentHasEnded(){
        return this.tournamentHasEnded;
    }

    private void initMatches(){
        Match match;
        for(int i = 0; i <= this.allTeams.size() - 1; i += 2) {
            try {

                match = new Match(this.allTeams.get(i), this.allTeams.get(i + 1));
            } catch (IndexOutOfBoundsException n){

                match = new Match(this.allTeams.get(i));
            }

            this.matches.add(match);
        }
    }

    private void initNextRound(){

        List<Team> teams = new ArrayList<>();
        for(Match m : this.rounds.get(this.currentRound)){
            teams.add(m.getWinner());
            Log.d("Init next round", m.getWinner().getName());
        }

        this.currentRound++;
        Collections.shuffle(teams);
        for (int i = 0; i <= teams.size() - 1; i += 2){
            Log.d("Current round", Double.toString(currentRound));
            this.rounds.get(this.currentRound).add(new Match(teams.get(i), teams.get(i + 1)));
        }
        this.currentMatch = 0;
    }

    public Match getFirstMatch(){

        return this.rounds.get(numberOfRounds - (numberOfRounds - 1.0)).get(0);
    }

    @Override
    public double getCurrentRound() {
        return this.currentRound;
    }

    @Override
    public int getCurrentMatchIndex() {
        return this.currentMatch;
    }
}
