import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BasicAPITests.class,
        OpenHashSetTest.class,
        ClosedHashSetTest1.class,
        ClosedHashSetTest2.class,
        EvyatarTest.class
                    })

public class testsRunner {
}
