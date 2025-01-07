package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.Driver;
import utilities.VideoRecorder;
import io.qameta.allure.Attachment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks {
    private WebDriver driver;
    private String currentScenarioName;

    @Before
    public void setUp(Scenario scenario) {
        try {
            currentScenarioName = scenario.getName().replaceAll("\\s+", "_");
            VideoRecorder.startRecording(currentScenarioName);
            driver = Driver.getDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            }
            
            // Video kaydını durdur
            VideoRecorder.stopRecording();
            
            // Video dosyasını Allure raporuna ekle
            String videoPath = VideoRecorder.getLastRecordingPath();
            if (videoPath != null && new File(videoPath).exists()) {
                try {
                    byte[] videoBytes = Files.readAllBytes(Paths.get(videoPath));
                    attachVideo(videoBytes);
                    scenario.attach(videoBytes, "video/mp4", "Test Video");
                } catch (IOException e) {
                    System.err.println("Video dosyası okunamadı: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.err.println("Video dosyası bulunamadı: " + videoPath);
            }
            
        } catch (Exception e) {
            System.err.println("Teardown sırasında hata: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Driver.closeDriver();
        }
    }
    
    @Attachment(value = "Video kaydı", type = "video/mp4")
    private byte[] attachVideo(byte[] video) {
        return video;
    }
}
