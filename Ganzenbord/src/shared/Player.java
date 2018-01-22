package shared;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int currentLoc;

    public String getName() {
        return name;
    }

    public void setCurrentLoc(int currentLoc) {
        this.currentLoc = currentLoc;
    }

    public int getCurrentLoc() {
        return currentLoc;
    }

    public Player(String name){
        this.name = name;
        currentLoc = 0;
    }
}
