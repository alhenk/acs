package kaz.trei.acs.action;

public class ActionResult {
    private ActionType method;
    private String path;

    public ActionResult() {
        method = ActionType.REDIRECT;
        path = "/";
    }
    public ActionResult(ActionType method, String path){
        this.method = method;
        this.path = path;
    }

    public ActionType getMethod() {
        return method;
    }

    public void setMethod(ActionType method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
