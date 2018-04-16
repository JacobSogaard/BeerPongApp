package modelClasses;

import java.util.UUID;

public class Player {
    private String name;
    private final UUID id = UUID.randomUUID();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
}
