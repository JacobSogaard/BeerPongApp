package app.beerpong.beerpongapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button sub;
    ImageView beer;
    Animation fromBottom, fromtop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sub = (Button) findViewById(R.id.sub);
        beer = (ImageView) findViewById(R.id.ballon);

        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);


        sub.setAnimation(fromBottom);
        beer.setAnimation(fromtop);



        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,add_teams_activity.class));
            }
        });




    }
}
