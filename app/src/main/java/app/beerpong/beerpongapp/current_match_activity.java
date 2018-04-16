package app.beerpong.beerpongapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class current_match_activity extends AppCompatActivity {

    @BindView(R.id.matchScreenTitle)
    public TextView match_screen_title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_match_activity);
        ButterKnife.bind(this);

        String matchTitle = String.format(getResources().getString(R.string.match_screen_title), "TITLE");
        this.match_screen_title.setText(matchTitle);
        
    }
}
