package kaz.trei.asc.action;

import junit.framework.TestCase;
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

        action = actionFactory.create(actionName);
        expectedAction = new ShowMain();
        assertEquals(action.getClass().getName(),
                expectedAction.getClass().getName());
    }
}
