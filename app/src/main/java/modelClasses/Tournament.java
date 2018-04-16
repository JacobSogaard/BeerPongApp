package modelClasses;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament implements ITournamentService  {
    private ArrayList<Team> allTeams;
    private ArrayList<Match> matches;
    private double numberOfRounds;
    private Map<Double, Match[]> rounds;
    private int numberOfTeams;
    private int currentMatch;
    private double currentRound;
    private boolean oneTeamMatch;


    public Tournament() {
        this.allTeams = new ArrayList();

    }

    @Override
    public boolean addTeam(Team t) {
        return allTeams.add(t);
    }

    private void initTournament(){

        this.rounds = new HashMap();
        this.matches = new ArrayList<>();
        this.numberOfTeams = this.allTeams.size();
        this.numberOfRounds = Math.floor(Math.log(this.numberOfTeams - 1) / Math.log(2));

        double maxTeams = 2;
        for(double i = numberOfRounds; i >= 1; i--){

            Match[] matchList = new Match[(int) (maxTeams/2)];
            this.rounds.put(i, matchList);
            maxTeams = Math.floor(Math.pow(maxTeams, 2));
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
                this.rounds.get(roundTwo)[0] = this.matches.get(this.matches.size() - 1);
                this.matches.remove(this.matches.size() - 1);
                this.oneTeamMatch = true;
                i = 1;
            }
            //iterates through and adds all matches in second round
            for (i = 0; i <= this.rounds.get(roundTwo).length - 1; i++){
                this.rounds.get(roundTwo)[i] = this.matches.get(i);
                this.matches.remove(i);
            }
        }
    }

    private void initFirstRound(){
        double roundOne = numberOfRounds - (numberOfRounds - 1);
        this.matches.trimToSize();

        for(int i = 0; i <= this.matches.size() - 1; i++){
            this.rounds.get(roundOne)[i] = this.matches.get(i);
        }
    }

    @Override
    public boolean start(){
        this.initTournament();
        this.currentMatch = 0;
        this.currentRound = this.numberOfRounds;
        return true;
    }


    /**
     * Set the winner of a current match.
     * @param winner Winner of type Team
     */
    @Override
    public void setWinner(Team winner){
        this.rounds.get(this.currentRound)[this.currentMatch].setWinner(winner);
    }

    /**
     * Method to get the next match in the tournament. If current match is the last in round,
     * next round is initialized and the first match in that round is returned. Else next match in current round
     * @return Next match of type match
     */
    @Override
    public Match getNextMatch(){
        //If round has ended
        if (this.rounds.get(this.currentRound)[this.currentMatch + 1] == null){
            this.initNextRound();
            return this.rounds.get(this.currentRound)[this.currentMatch];
        }

        //Next match
        this.currentMatch++;
        return this.rounds.get(this.currentRound)[this.currentMatch];
    }

    public Match getCurrentMatch(){
        return this.rounds.get(this.currentRound)[this.currentMatch];
    }

    public void setCurrentMatchWinner(Team winner){
        this.getCurrentMatch().setWinner(winner);
    }

    private void initMatches(){
        Match match;
        for(int i = 0; i <= this.allTeams.size() - 1; i += 2) {
            try {
                Log.d("init 2 teams match", this.allTeams.get(i).getName() + " - " + this.allTeams.get(i + 1).getName());
                match = new Match(this.allTeams.get(i), this.allTeams.get(i + 1));
            } catch (IndexOutOfBoundsException n){
                Log.d("Init 1 team match", this.allTeams.get(i).getName());
                match = new Match(this.allTeams.get(i));
            }

            this.matches.add(match);
        }
    }

    private void initNextRound(){
        List<Team> teams = new ArrayList<>();
        for(Match m : this.rounds.get(this.currentRound)){
            teams.add(m.getWinner());
        }

        this.currentRound++;
        Collections.shuffle(teams);
        for (int i = 0; i <= teams.size(); i += 2){
            this.rounds.get(this.currentRound)[i] = new Match(teams.get(i), teams.get(i + 1));
        }
        this.currentMatch = 0;
    }

    public Match getFirstMatch(){
        Log.d("First match", Double.toString(numberOfRounds - (numberOfRounds - 1.0)));
        return this.rounds.get(numberOfRounds - (numberOfRounds - 1.0))[0];
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
