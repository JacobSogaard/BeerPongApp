package modelClasses;

public interface ITournamentService {
    boolean addTeam(Team t);
    void start();
    void setWinner(Team winner);
    Match2 getNextMatch();
    int getCurrentRound();
    void concludeMatch(Match2 match);
    //int getCurrentMatchIndex();

}
