package app.beerpong.beerpongapp;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import modelClasses.Player;
//import modelClasses.Tournament;
import modelClasses.Team;
import modelClasses.Tournament2;
import modelClasses.TournamentViewModel;

public class add_teams_activity extends AppCompatActivity {

    //Declare UI binds
    @BindView(R.id.addTeamBTN)
    public Button addTeamBTN;

    @BindView(R.id.teamNameTin)
    public TextInputEditText teamNameTin;

    @BindView(R.id.teamsLView)
    public ListView teamsLView;

    @BindView(R.id.startBTN)
    public Button startBTN;


    //Class attributes
    private TournamentViewModel tournamentView;
    private Tournament2 tournament;
    private ArrayAdapter<Team> teamAdapter;
    private boolean removeTeam = false;
    private Team teamToRemove = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teams_activity);
        ButterKnife.bind(this);
        this.setAddTeamBTNStuff();
        this.setStartBTN();

        this.tournament = ViewModelProviders.of(this).get(Tournament2.class);

        teamAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_project_text_color, R.id.list_content, tournament.getAllTeams());
        teamsLView.setAdapter(teamAdapter);
        setListViewListener();
    }

    //Adding teams to the tournament
    private void setAddTeamBTNStuff() {
        this.addTeamBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Team team = handleTeamCreation(teamNameTin.getText());
                if (team == null)
                    teamNameTin.setError("Please Type A Name");
                else {
                    if (!tournament.addTeam(team))
                        teamNameTin.setError("Name Already Taken");
                    else {
                        teamNameTin.getText().clear();
                        handleListStuff(team);
                    }
                }
            }
        });
    }

    private Team handleTeamCreation(Editable t) {
        Team team = null;

        if (!t.toString().matches("")) {
            team = new Team();
            team.setName(t.toString());
        }
        return team;
    }

    private void handleListStuff(Team team) {
        teamAdapter.notifyDataSetChanged();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //Possible null pointer
        imm.hideSoftInputFromWindow(teamNameTin.getWindowToken(), 0);
        teamNameTin.setFocusable(false);
        teamNameTin.setFocusableInTouchMode(true);

    }

    /**
     * Sets the listener on the listView to listen for the user holding on a team
     */
    private void setListViewListener() {
        teamsLView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?>adapter, View v, int position, long id) {
                teamToRemove = (Team) adapter.getItemAtPosition(position);
                openRemoveTeamAlert();
                return true;
            }
        });
    }

    /**
     * Creates the alertDialog to prompt user for deleting team, and then deletes it.
     */
    private void openRemoveTeamAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(teamsLView.getContext());
        builder.setMessage("Remove This Team:\n\t\t" + teamToRemove.getName() + "?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tournament.removeTeam(teamToRemove.getName());
                teamAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeTeam = false;
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setStartBTN() {
        this.startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(add_teams_activity.this,current_match_activity.class);
                i.putExtra("tournament", tournament);
                startActivity(i);
            }
        });
    }


    private void stuffDo() {

    }


}
