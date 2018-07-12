package app.beerpong.beerpongapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class end_tournament_activity extends AppCompatActivity {

    @BindView(R.id.winnerTW)
    public TextView winnerTW;

    private String winner = "No winner found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_tournament_activity);
        Intent i = getIntent();
        ButterKnife.bind(this);


        if (i != null) {
            //Winner extra is apparently null... It put in current_match_activity in nextMatch()
            this.winner = i.getStringExtra("winner");
            this.winnerTW.setText(this.winner);
        }
    }
}
