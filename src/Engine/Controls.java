package Engine;

import java.util.HashMap;

public class Controls {
    private HashMap<Integer, String> keyMap;

    public Controls(HashMap<Integer, String> keyMap) {
        this.keyMap = keyMap;
    }

    public HashMap<Integer, String> getKeyMap() {
        return keyMap;
    }
    public void setKeyMap(HashMap<Integer, String> keyMap) {
        this.keyMap = keyMap;
    }
}
