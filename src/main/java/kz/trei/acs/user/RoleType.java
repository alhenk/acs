package kz.trei.acs.user;

import java.util.ArrayList;
import java.util.List;

public enum RoleType {
    ADMINISTRATOR, SUPERVISOR, EMPLOYEE, UNREGISTERED;

    public static List<String> getList() {
        List<String> types = new ArrayList<String>();
        for (RoleType type : RoleType.values()) {
            types.add(type.toString());
        }
        return types;
    }
}
