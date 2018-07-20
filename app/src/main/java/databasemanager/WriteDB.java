package databasemanager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class WriteDB {
    private static final TournamentCountHandler count = new TournamentCountHandler();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public WriteDB(){

    }

    public void addTournamentPlayed(){
        //DocumentReference ref = db.document();
        //count.createCounter(ref, 10);

        //count.createCounter(db.collection("tournamentcounter").document(), 10);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        //count.createCounter(db.collection("tournamentcounter").document("tournament"), 10);
        count.incrementCounter(db.collection("tournamentcounter").document("tournament"), 10);

    }


}
