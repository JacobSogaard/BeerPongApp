package databasemanager;

import modelClasses.IDBService;

public class DBHandler implements IDBService {

    private WriteDB writer;

    public DBHandler(){
        this.writer = new WriteDB();
    }

    @Override
    public boolean addTournamentPlayed() {
        writer.addTournamentPlayed();
        return true;
    }
}
