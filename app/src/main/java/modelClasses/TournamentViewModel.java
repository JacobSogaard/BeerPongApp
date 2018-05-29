package modelClasses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * View model er ikke lavet rigtigt, tjek dokumentationen
 *
 */


public class TournamentViewModel extends ViewModel {
    private MutableLiveData<Tournament2> tournament;

    public LiveData<Tournament2> getTournament(){
        if (tournament == null){
            tournament = new MutableLiveData<>();
            loadTournament();
        }
        return tournament;
    }

    private void loadTournament(){
        tournament.setValue(new Tournament2());
    }
}
