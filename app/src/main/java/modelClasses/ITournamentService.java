package modelClasses;

public interface ITournamentService {
    boolean addTeam(Team t);
    boolean start();
    void setWinner(Team winner);
    Match getNextMatch();
    double getCurrentRound();
    int getCurrentMatchIndex();
}
