package kaz.trei.acs.action;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class);
    private Map<String, Action> actions;

    public ActionFactory(){
        actions = new HashMap<String, Action>();
        actions.put("GET/main", new ShowMainPage());
        actions.put("POST/set-language", new SetLanguage());
        actions.put("POST/signin", new Signin());
        actions.put("GET/signout", new Signout());
        LOGGER.debug("Instantiated ActionFactory");
    }
    public Action create(String actionName) {
        Action action = actions.get(actionName);
        if(action == null) return new ShowErrorPage();
        LOGGER.debug("created action = " + action.getClass().getName());
        return action;
    }
}
