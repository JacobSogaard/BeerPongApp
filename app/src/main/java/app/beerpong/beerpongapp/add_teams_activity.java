package app.beerpong.beerpongapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class add_teams_activity extends AppCompatActivity {

    @BindView(R.id.testButton)
    public Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teams_activity);
        ButterKnife.bind(this);
    }

    private void stuffDo() {
        System.out.println("test");

    }


}
