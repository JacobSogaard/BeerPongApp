package app.beerpong.beerpongapp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import modelClasses.Player;

public class add_teams_activity extends AppCompatActivity {

    //Declare UI binds
    @BindView(R.id.addTeamBTN)
    public Button addTeamBTN;

    @BindView(R.id.teamNameTin)
    public TextInputEditText teamNameTin;


    //Class attributes
    private ArrayList<String> teams = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teams_activity);
        ButterKnife.bind(this);
        this.setAddTeamBTNStuff();
    }

    private void setAddTeamBTNStuff() {
        this.addTeamBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                teams.add(teamNameTin.getText().toString());
                teamNameTin.getText().clear();
            }
        });
    }

    private void stuffDo() {
        System.out.println("test");

    }


}
