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
import modelClasses.Team;
import modelClasses.Tournament;
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
    private TournamentViewModel tournament;
    private Match match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_match_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Tournament t = (Tournament) intent.getSerializableExtra("tournament");

        this.tournament = ViewModelProviders.of(this).get(TournamentViewModel.class);
        this.getTournament().addTeams(t.getAllTeams());

        this.initTestTur();
        this.tournament.getTournament().observe(this, Tournament -> {
            Match m = this.tournament.getTournament().getValue().getCurrentMatch();
            teamOneBTN.setText(m.getTeams()[0].getName());
            teamTwoBTN.setText(m.getTeams()[1].getName());
            if(tournament.getTournament().getValue().getTournamentHasEnded()){
                this.winnerTW.setText(R.string.winner);
            }
            this.setTitle();
        });

        match = this.getTournament().getFirstMatch();
        this.initTeamOneBTN(match);
        this.initTeamTwoBTN(match);
        
    }

    private void initTeamOneBTN(Match match){
        this.setTeamOneBTNText();


        this.teamOneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMatch(0);
                setTeamOneBTNText();
                setTeamTwoBTNText();
            }
        });
    }

    private void initTeamTwoBTN(Match match){
        this.setTeamTwoBTNText();

        this.teamTwoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMatch(1);
                setTeamTwoBTNText();
                setTeamOneBTNText();
            }
        });
    }

    private void setTeamOneBTNText(){
        String buttonTeam = match.getTeams()[0].getName();
        this.teamOneBTN.setText(buttonTeam);
    }

    private void setTeamTwoBTNText(){
        String buttonTeam = match.getTeams()[1].getName();
        this.teamTwoBTN.setText(buttonTeam);
    }

    private void initTestTur(){
        Team t1 = new Team("Team 1");
        this.getTournament().addTeam(t1);

        Team t2 = new Team("Team 2");

        Team t3 = new Team("Team 3");

        Team t4 = new Team("Team 4");





        this.tournament.getTournament().getValue().addTeam(t2);
        this.tournament.getTournament().getValue().addTeam(t3);
        this.tournament.getTournament().getValue().addTeam(t4);



        this.tournament.getTournament().getValue().start();
    }




    private void setTitle(){
        this.updateRoundAndMatch((int) this.tournament.getTournament().getValue().getCurrentRound(), this.tournament.getTournament().getValue().getCurrentMatchIndex());
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

    private void nextMatch(int winner){
        if (getTournament().getTournamentHasEnded()){
            this.winnerTW.setText(R.string.winner);
        } else {
            getTournament().getCurrentMatch().setWinner(this.match.getTeams()[winner]);
            match = getTournament().getNextMatch();
            this.updateRoundAndMatch((int) getTournament().getCurrentRound(), getTournament().getCurrentMatchIndex());
        }

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

    private Tournament getTournament(){
        return this.tournament.getTournament().getValue();
    }
}
