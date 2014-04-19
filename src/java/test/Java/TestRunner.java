package Java;

import Java.db.DBTest;
import Java.pageGenerator.PageGeneratorTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import templater.PageGenerator;

/*
 Created by p.Kochetkov on 29.03.14.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DBTest.class, PageGeneratorTest.class);
        if (result.wasSuccessful()) {
            System.out.println("All tests are sucсess");
        } else {
            for (Failure failure: result.getFailures()) {
                System.out.println("Failed: " + failure.toString() +"\n==========\n");
            }
        }
    }
}
