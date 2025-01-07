package utilities;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import java.util.ArrayList;
import java.util.List;

public class TestResultListener implements ConcurrentEventListener {
    private static final List<TestResult> testResults = new ArrayList<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        TestCase testCase = event.getTestCase();
        Status status = event.getResult().getStatus();
        String errorMessage = event.getResult().getError() != null ? 
            event.getResult().getError().getMessage() : null;
        long duration = event.getResult().getDuration().toMillis();

        testResults.add(new TestResult(
            testCase.getName(),
            status.toString(),
            duration,
            errorMessage
        ));
    }

    public static List<TestResult> getTestResults() {
        return new ArrayList<>(testResults);
    }

    public static void clearResults() {
        testResults.clear();
    }

    public static class TestResult {
        private final String name;
        private final String status;
        private final long duration;
        private final String error;

        public TestResult(String name, String status, long duration, String error) {
            this.name = name;
            this.status = status;
            this.duration = duration;
            this.error = error;
        }

        public String getName() { return name; }
        public String getStatus() { return status; }
        public long getDuration() { return duration; }
        public String getError() { return error; }
    }
}
