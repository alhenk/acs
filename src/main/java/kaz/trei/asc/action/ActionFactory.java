package kaz.trei.asc.action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String, Action> actions;

    public ActionFactory(){
        actions = new HashMap<String, Action>();
        actions.put("GET/main", new ShowMainPage());
    }
    public Action create(String actionName) {
        return actions.get(actionName);
    }
}
