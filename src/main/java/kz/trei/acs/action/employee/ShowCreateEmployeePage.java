package kz.trei.acs.action.employee;


import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
import kz.trei.acs.office.structure.DepartmentType;
import kz.trei.acs.office.structure.PositionType;
import kz.trei.acs.office.structure.RoomType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowCreateEmployeePage implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowCreateEmployeePage.class);
    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<String> positions = PositionType.getList();
        List<String> departments = DepartmentType.getList();
        List<String> rooms = RoomType.getList();

        session.setAttribute("positions", positions);
        session.setAttribute("departments", departments);
        session.setAttribute("rooms", rooms);
        LOGGER.debug("...");
        return new ActionResult(ActionType.FORWARD, "create-employee");
    }
}
