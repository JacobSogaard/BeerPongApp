package app.beerpong.beerpongapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class end_tournament_activity extends AppCompatActivity {

    @BindView(R.id.winnerTW)
    public TextView winnerTW;

    @BindView(R.id.newTournamentBTN)
    public Button newTournament;

    private String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_tournament_activity);
        Intent i = getIntent();
        ButterKnife.bind(this);
        this.setNewTournament();


        if (i != null) {
            //Winner extra is apparently null... It put in current_match_activity in nextMatch()
            this.winner = i.getStringExtra("winner");
            this.winnerTW.setText(this.winner);
        }
    }

    private void setNewTournament(){
        this.newTournament.setOnClickListener(v -> {
            Intent i = new Intent(end_tournament_activity.this, add_teams_activity.class);
            startActivity(i);
        });
    }
}
