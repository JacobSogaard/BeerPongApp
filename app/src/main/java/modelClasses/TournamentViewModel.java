package modelClasses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TournamentViewModel extends ViewModel {
    private MutableLiveData<Tournament> tournament;

    public LiveData<Tournament> getTournament(){
        if (tournament == null){
            tournament = new MutableLiveData<>();
            loadTournament();
        }
        return tournament;
    }

    private void loadTournament(){
        tournament.setValue(new Tournament());
    }
}
