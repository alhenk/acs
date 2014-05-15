package kz.trei.acs.action.employee;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowCreateEmployeePage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        List<String> positions = new ArrayList<String>();
        for (PositionType type : PositionType.values()) {
            positions.add(type.getPosition());
        }
        List<String> departments = new ArrayList<String>();
        for (DepartmentType type : DepartmentType.values()) {
            departments.add(type.toString());
        }

        List<String> rooms = new ArrayList<String>();
        for (RoomType type : RoomType.values()) {
            rooms.add(type.toString());
        }

        session.setAttribute("positions", positions);
        session.setAttribute("departments", departments);
        session.setAttribute("rooms", rooms);
        return new ActionResult(ActionType.FORWARD, "create-employee");
    }
}
