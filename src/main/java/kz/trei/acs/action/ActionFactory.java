package kz.trei.acs.action;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class);
    private Map<String, Action> actions;

    public ActionFactory() {
        actions = new HashMap<String, Action>();
        actions.put("GET/main", new ShowMainPage());
        actions.put("POST/set-language", new SetLanguage());
        actions.put("POST/sign-in", new Signin());
        actions.put("POST/sign-up", new Signup());
        actions.put("GET/sign-out", new Signout());
        actions.put("GET/dashboard", new ShowDashboard());
        actions.put("GET/getAction-account", new ShowCreateAccountPage());

        LOGGER.debug("Instantiated ActionFactory");
    }

    public Action getAction(String actionName) {
        Action action = actions.get(actionName);
        if (action == null) return new ShowErrorPage();
        LOGGER.debug("created action = " + action.getClass().getName());
        return action;
    }
}
