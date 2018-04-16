package app.beerpong.beerpongapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Statistics extends AppCompatActivity {

    @BindView(R.id.startedTourTV)
    public TextView startedTourTV;

    @BindView(R.id.completedTourTV)
    public TextView completedTourTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);

        loadStartedTournaments();
        loadCompletedTournaments();
    }

    private void loadStartedTournaments() {
        startedTourTV.setText("4");
    }

    private void loadCompletedTournaments() {
        completedTourTV.setText("2");
    }
}
