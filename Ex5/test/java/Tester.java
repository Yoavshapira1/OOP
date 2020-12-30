package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.java.DirectoryProcessorTest;
import test.java.SecondaryTester;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DirectoryProcessorTest.class,
        SecondaryTester.class
})

public class Tester {
}
