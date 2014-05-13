package kz.trei.acs.action;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class);
    private Map<String, Action> actions;

    public ActionFactory() {
        actions = new HashMap<String, Action>();
        //General
        actions.put("GET/main", new ShowMainPage());
        actions.put("GET/dashboard", new ShowDashboard());
        actions.put("POST/sign-in", new Signin());
        actions.put("GET/sign-out", new Signout());
        actions.put("POST/set-language", new SetLanguage());
        //Error
        actions.put("GET/error", new ShowErrorPage());
        actions.put("GET/exception", new ShowExceptionPage());
        //User
        actions.put("GET/user-list", new ShowUserListPage());
        actions.put("POST/edit-user", new EditUser());
        actions.put("GET/edit-user", new ShowEditUserPage());
        actions.put("GET/create-user", new ShowCreateUserPage());
        actions.put("POST/sign-up", new Signup());
        actions.put("GET/delete-user", new DeleteUser());
        //RfidTag
        actions.put("GET/rfidtag-list", new ShowRfidTagListPage());
        actions.put("GET/create-rfidtag", new ShowCreateRfidTagPage());
        actions.put("POST/create-rfidtag", new CreateRfidTag());
        //Employee
        actions.put("GET/employee-list", new ShowEmployeeListPage());
        LOGGER.debug("Instantiated ActionFactory");
    }

    public Action getAction(String actionName) {
        Action action = actions.get(actionName);
        if (action != null){
        LOGGER.debug("created action = " + action.getClass().getName());
        }else{
            LOGGER.debug("created action = null");
        }
        return action;
    }
}
