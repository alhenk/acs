package kz.trei.acs.action;

import junit.framework.TestCase;
import kz.trei.acs.action.general.ShowMainPage;
import org.junit.Before;
import org.junit.Test;

public class ActionFactoryTest extends TestCase {
    private ActionFactory actionFactory;
    private Action action;
    private Action expectedAction;

    @Before
    public void setUp() throws Exception {
        actionFactory = new ActionFactory();
    }

    @Test
    public void testCreate() throws Exception {
        String actionName = "GET/main";

        action = actionFactory.getAction(actionName);
        expectedAction = new ShowMainPage();
        assertEquals(action.getClass().getName(),
                expectedAction.getClass().getName());
    }
}
