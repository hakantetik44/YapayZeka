package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class Driver {
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // Jenkins'te çalışırken headless mod
            String env = System.getProperty("test.env", "local");
            if ("jenkins".equals(env)) {
                options.addArguments("--headless=new");
            }
            
            // Genel ayarlar
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            
            WebDriver driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driverPool.set(driver);
        }
        return driverPool.get();
    }
    
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().close();
            driverPool.remove();
        }
    }
    
    public static void quitDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}