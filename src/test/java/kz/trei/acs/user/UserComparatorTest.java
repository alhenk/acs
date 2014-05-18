package kz.trei.acs.user;

import junit.framework.TestCase;
import kz.trei.acs.office.structure.Account1C;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class UserComparatorTest extends TestCase {
    private static final Logger LOGGER = Logger.getLogger(UserComparatorTest.class);
    User user1;
    User user2;
    User user3;
    @Before
    public void setUp() throws Exception {
        user1 = new User
                .Builder("admin", "123")
                .id(1L)
                .email("admin@example.com")
                .role(RoleType.ADMINISTRATOR)
                .account1C(Account1C.buildAccount1C("KK00000001"))
                .build();

        user2 = new User
                .Builder("bob", "123")
                .id(2L)
                .email("bdmin@example.com")
                .role(RoleType.SUPERVISOR)
                .account1C(Account1C.buildAccount1C("KK00000002"))
                .build();

        user3 = new User
                .Builder("Carlos", "123")
                .id(3L)
                .email("carlos@example.com")
                .role(RoleType.EMPLOYEE)
                .build();
    }

    @Test
    public void testCompare() throws Exception {
        UserComparator.CompareType type = UserComparator.CompareType.ID;
        UserComparator userComparator = new UserComparator(type);
        assertEquals( userComparator.compare(user1,user2), -1);
        assertEquals( userComparator.compare(null,user2), -1);
        assertEquals( userComparator.compare(user1,null), 1);
        assertEquals( userComparator.compare(null, null), 0);
        type = UserComparator.CompareType.TABLE_ID;
        userComparator = new UserComparator(type);
        assertEquals( userComparator.compare(user1,user2), -1);
        assertEquals( userComparator.compare(user2,user1), 1);
        assertEquals( userComparator.compare(user2,user3), 1);
        assertEquals( userComparator.compare(user3,user3), 0);
        LOGGER.debug("USER3.ACCOUNT1C should be NULL " + (user3.getAccount1C() == null));
    }
}
