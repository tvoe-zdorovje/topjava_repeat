package ru.javawebinar.topjava.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealServiceTest.class);

    private static StringBuilder testingTimeSummary;

    @ClassRule
    public static ExternalResource externalResource = new ExternalResource() {
        private long startTime;

        @Override
        protected void before() throws Throwable {
            startTime = System.currentTimeMillis();
            testingTimeSummary = new StringBuilder();
        }

        @Override
        protected void after() {
            String separator = "\n================================";
            testingTimeSummary.append(separator)
                    .append(String.format("%n|%-20s - %5d ms|", "Total testing time", (System.currentTimeMillis() - startTime)))
                    .append(separator);
            LOGGER.info(testingTimeSummary.toString());

        }
    };

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        private long startTime;

        @Override
        public void starting(Description description) {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            long testingTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Testing time: {}ms", testingTime);
            testingTimeSummary.append(String.format("%n|%-20.20s - %5d ms|", description.getMethodName(), testingTime));
        }
    };


}
