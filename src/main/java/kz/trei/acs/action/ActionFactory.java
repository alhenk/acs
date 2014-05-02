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
        actions.put("GET/create-account", new ShowCreateAccountPage());
        actions.put("GET/user-list", new ShowUserList());
        actions.put("GET/error", new ShowErrorPage());
        actions.put("GET/exception", new ShowExceptionPage());
        actions.put("GET/not-found", new ShowNotFoundPage());
        actions.put("GET/edit-account", new ShowEditAccount());
        actions.put("GET/delete-account", new DeleteAccount());

        LOGGER.debug("Instantiated ActionFactory");
    }

    public Action getAction(String actionName) {
        Action action = actions.get(actionName);
        if (action == null) return new ShowErrorPage();
        LOGGER.debug("created action = " + action.getClass().getName());
        return action;
    }
}
