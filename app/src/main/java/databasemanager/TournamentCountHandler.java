package databasemanager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.*;

import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TournamentCountHandler {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // counters/${ID}
    public class Counter {
        int numShards;

        public Counter(int numShards) {
            this.numShards = numShards;
        }
    }

    // counters/${ID}/shards/${NUM}
    public class Shard {
        int count;

        public Shard(int count) {
            this.count = count;
        }
    }

    public TournamentCountHandler(){}

    public Task<Void> createCounter(final DocumentReference ref, final int numShards) {
        // Initialize the counter document, then initialize each shard.
        return ref.set(new Counter(numShards))
                .continueWithTask(new Continuation<Void, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        List<Task<Void>> tasks = new ArrayList<>();

                        // Initialize each shard with count=0
                        for (int i = 0; i < numShards; i++) {
                            Task<Void> makeShard = ref.collection("shards")
                                    .document(String.valueOf(i))
                                    .set(new Shard(0));

                            tasks.add(makeShard);
                        }

                        return Tasks.whenAll(tasks);
                    }
                });
    }

    /**
     * Increments counters in firestore. Adds 1 to random of 10 shards
     * @param ref Documentreference, should be db.collection("tournamentcounter").document("tournament") or something for tournament number counter, but change to accomodate other counters
     * @param numShards Number of shards existing in firestore. Used to find random shard
     * @return null
     */
    public Task<Void> incrementCounter(final DocumentReference ref, final int numShards) {
        int shardId = (int) Math.floor(Math.random() * numShards);
        final DocumentReference shardRef = ref.collection("shards").document(String.valueOf(shardId));

        return db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {

                DocumentSnapshot snapshot = transaction.get(shardRef);
                double count = snapshot.getDouble("count") + 1;

                Log.d("count", Double.toString(count));


                transaction.update(shardRef, "count", count);

                return null;
            }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Transaction success!");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Transaction failure.", e);
                }

            });
    }





}


