package kz.trei.acs.action;

public class ActionResult {
    private ActionType method;
    private String view;

    public ActionResult() {
        method = ActionType.REDIRECT;
        view = "/";
    }
    public ActionResult(ActionType method, String view){
        this.method = method;
        this.view = view;
    }

    public ActionType getMethod() {
        return method;
    }

    public void setMethod(ActionType method) {
        this.method = method;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
