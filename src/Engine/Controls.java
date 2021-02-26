package Engine;

import java.util.HashMap;

public class Controls {
    private HashMap<Constants.Directions, String> keyMap;

    public Controls(HashMap<Constants.Directions, String> keyMap) {
        this.keyMap = keyMap;
    }

    public HashMap<Constants.Directions, String> getKeyMap() {
        return keyMap;
    }
    public void setKeyMap(HashMap<Constants.Directions, String> keyMap) {
        this.keyMap = keyMap;
    }
}
