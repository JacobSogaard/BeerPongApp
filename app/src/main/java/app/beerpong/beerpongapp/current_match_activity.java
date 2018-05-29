package app.beerpong.beerpongapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import modelClasses.ITournamentService;
import modelClasses.Match;
import modelClasses.Match2;
import modelClasses.Team;
//import modelClasses.Tournament;
import modelClasses.Tournament2;
import modelClasses.TournamentViewModel;

public class current_match_activity extends AppCompatActivity {

    @BindView(R.id.matchScreenTitle)
    public TextView match_screen_title;

    @BindView(R.id.teamOneBTN)
    public Button teamOneBTN;

    @BindView(R.id.teamTwoBTN)
    public Button teamTwoBTN;

    @BindView(R.id.winnerTW)
    public TextView winnerTW;

    private String currentMatch, roundText;
    private double currentRound;
    private Tournament2 tournament;
    private Match2 match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_match_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent != null) {
            this.tournament = (Tournament2) intent.getSerializableExtra("tournament");
        }


        //this.getTournament().addTeams(t.getAllTeams());
        //tournament = ViewModelProviders.of(add_teams_activity.class).get(Tournament2.class);



        //this.initTestTur();

            this.getTournament().start();
            this.match = this.getTournament().getNextMatch();
            teamOneBTN.setText(match.getTeam1().getName());
            teamTwoBTN.setText(match.getTeam2().getName());
            if(!this.getTournament().hasRound(this.getTournament().getCurrentRound())){
                this.winnerTW.setText(R.string.winner);
            }
            this.setTitle();

        this.initTeamOneBTN();
        this.initTeamTwoBTN();
        
    }

    private void initTeamOneBTN(){
        this.setTeamOneBTNText();


        this.teamOneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMatch(match.getTeam1());
                setTeamOneBTNText();
                setTeamTwoBTNText();
            }
        });
    }

    private void initTeamTwoBTN(){
        this.setTeamTwoBTNText();

        this.teamTwoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMatch(match.getTeam2());
                setTeamTwoBTNText();
                setTeamOneBTNText();
            }
        });
    }

    private void setTeamOneBTNText(){
        String buttonTeam = match.getTeam1().getName();
        this.teamOneBTN.setText(buttonTeam);
    }

    private void setTeamTwoBTNText(){
        String buttonTeam = match.getTeam2().getName();
        this.teamTwoBTN.setText(buttonTeam);
    }

    private void initTestTur(){
        Team t1 = new Team("Team 1");
        this.getTournament().addTeam(t1);

        Team t2 = new Team("Team 2");

        Team t3 = new Team("Team 3");

        Team t4 = new Team("Team 4");





        this.tournament.addTeam(t2);
        this.tournament.addTeam(t3);
        this.tournament.addTeam(t4);



        this.tournament.start();
    }




    private void setTitle(){
        this.updateRoundAndMatch(this.getTournament().getCurrentRound(), this.getTournament().getConcludedMatches().size() + 1);
        if (this.currentRound == 0){
            this.roundText = getString(R.string.roundofsixteen);
        } else if (this.currentRound == 1) {
            this.roundText = getString(R.string.quarterfinals);
        } else if (this.currentRound == 2) {
            this.roundText = getString(R.string.semifinals);
        } else if (this.currentRound == 3) {
            this.roundText = getString(R.string.finals);
        } else {
            this.roundText = getString(R.string.unknown);
        }

        Resources res = getResources();
        String title = String.format(res.getString(R.string.match_screen_title), currentMatch, this.roundText);
        match_screen_title.setText(title);

    }

    private void nextMatch(Team winner){
        match.setMatchWinner(winner, this.getTournament().getCurrentRound());
        this.getTournament().concludeMatch(match);
        this.match = this.getTournament().getNextMatch();
        this.setTitle();
    }


    private void updateRoundAndMatch(int currentRound, int currentMatch){
        this.setCurrentMatch(currentMatch);
        this.setCurrentRound(currentRound);
    }

    private void setCurrentRound(int currentRound){
        this.currentRound = currentRound;
    }

    private void setCurrentMatch(int currentMatch){
        this.currentMatch = Integer.toString(currentMatch + 1);
    }

    private Tournament2 getTournament(){
        return this.tournament;
    }
}
