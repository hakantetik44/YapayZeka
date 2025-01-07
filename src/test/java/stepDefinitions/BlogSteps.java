package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import utilities.Driver;
import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Dimension;
import java.util.List;
import java.util.Map;

public class BlogSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(3);

    public BlogSteps() {
        this.driver = Driver.getDriver();
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Given("Blog sayfasına git")
    @Step("Blog sayfasına gidiliyor")
    public void blogSayfasinaGit() {
        try {
            String blogUrl = System.getProperty("blogUrl", "http://localhost:3000");
            driver.get(blogUrl);
            
            // Sayfa yüklenmesini bekle
            new WebDriverWait(driver, PAGE_LOAD_TIMEOUT).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
                    .equals("complete")
            );
        } catch (Exception e) {
            throw new RuntimeException("Blog sayfası yüklenemedi: " + e.getMessage());
        }
    }

    @Then("Sayfa {int} saniye içinde yüklenmeli")
    public void sayfaSaniyeIcindeYuklenmeli(int saniye) {
        long startTime = System.currentTimeMillis();
        new WebDriverWait(driver, Duration.ofSeconds(saniye))
            .until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
        long endTime = System.currentTimeMillis();
        long loadTime = (endTime - startTime) / 1000;
        Assert.assertTrue("Sayfa " + saniye + " saniye içinde yüklenmedi!", loadTime <= saniye);
    }

    @When("Ekran boyutu {string} olarak ayarlanır")
    public void ekranBoyutuOlarakAyarlanir(String boyut) {
        String[] dimensions = boyut.split("x");
        int width = Integer.parseInt(dimensions[0].trim());
        int height = Integer.parseInt(dimensions[1].trim());
        driver.manage().window().setSize(new Dimension(width, height));
        // Yeniden boyutlandırma sonrası kısa bekleme
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("Sayfa responsive olarak görüntülenmeli")
    public void sayfaResponsiveOlarakGoruntulenmeli() {
        // Viewport kontrolü
        Long viewportWidth = (Long) ((JavascriptExecutor) driver)
            .executeScript("return window.innerWidth;");
        Long viewportHeight = (Long) ((JavascriptExecutor) driver)
            .executeScript("return window.innerHeight;");
        Assert.assertTrue("Viewport boyutları uygun değil", 
            viewportWidth > 0 && viewportHeight > 0);
    }

    @Then("Menü öğeleri doğru şekilde görüntülenmeli")
    public void menuOgeleriDogruSekildeGoruntulenmeli() {
        int viewportWidth = ((Long) ((JavascriptExecutor) driver)
            .executeScript("return window.innerWidth;")).intValue();

        if (viewportWidth <= 768) {
            // Mobil menü butonuna tıkla
            WebElement menuButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".mobile-menu-button"))
            );
            menuButton.click();

            // Kısa bir bekleme ekle
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Mobil menü görünürlüğünü kontrol et
            WebElement mobileMenu = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mobile-menu"))
            );
            String displayStyle = mobileMenu.getCssValue("display");
            Assert.assertNotEquals("Mobil menü görünür değil", "none", displayStyle);

            // Mobil menü öğelerini kontrol et
            List<WebElement> menuItems = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".mobile-menu .nav-link")
                )
            );
            Assert.assertTrue("Mobil menü öğeleri bulunamadı", menuItems.size() > 0);

            menuItems.forEach(item -> {
                Assert.assertTrue("Mobil menü öğesi görünür değil: " + item.getText(),
                    item.isDisplayed());
                Assert.assertTrue("Mobil menü öğesi tıklanabilir değil: " + item.getText(),
                    item.isEnabled());
            });
        } else {
            // Desktop menü öğelerini kontrol et
            List<WebElement> menuItems = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".desktop-menu .nav-link")
                )
            );
            Assert.assertTrue("Desktop menü öğeleri bulunamadı", menuItems.size() > 0);

            menuItems.forEach(item -> {
                Assert.assertTrue("Desktop menü öğesi görünür değil: " + item.getText(),
                    item.isDisplayed());
                Assert.assertTrue("Desktop menü öğesi tıklanabilir değil: " + item.getText(),
                    item.isEnabled());
            });
        }
    }

    @When("{string} bölümüne git")
    @Step("{0} bölümüne gidiliyor")
    public void bolumuneGit(String bolumAdi) {
        try {
            String sectionId = bolumAdi.toLowerCase()
                .replace("ı", "i")
                .replace("ğ", "g")
                .replace("ü", "u")
                .replace("ş", "s")
                .replace("ö", "o")
                .replace("ç", "c")
                .replace(" ", "-");
            
            WebElement section = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id(sectionId))
            );
            
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                section
            );
            
            // Smooth scroll için kısa bekleme
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(bolumAdi + " bölümüne gidilemedi: " + e.getMessage());
        }
    }

    @Then("Form alanları görünür olmalı")
    public void formAlanlariGorunurOlmali(List<Map<String, String>> alanlar) {
        alanlar.forEach(alan -> {
            String fieldName = alan.get("alan").toLowerCase()
                .replace(" ", "-");
            WebElement field = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(String.format("input[name='%s'], textarea[name='%s']", fieldName, fieldName))
                )
            );
            Assert.assertTrue(
                alan.get("alan") + " alanı görünür değil", 
                field.isDisplayed()
            );
        });
    }

    @Then("Form validasyonları çalışmalı")
    public void formValidasyonlariCalismali() {
        // Boş form gönderimi
        WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))
        );
        submitButton.click();
        
        // Hata mesajlarını kontrol et
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".error-message")
        ));
    }

    @Then("Form başarıyla gönderilebilmeli")
    public void formBasariylaGonderilebilmeli() {
        // Form alanlarını doldur
        fillFormField("ad-soyad", "Test Kullanıcı");
        fillFormField("email", "test@example.com");
        fillFormField("konu", "Test Mesajı");
        fillFormField("mesaj", "Bu bir test mesajıdır.");
        
        // Formu gönder
        WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))
        );
        submitButton.click();
        
        // Başarı mesajını kontrol et
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".success-message")
        ));
    }

    private void fillFormField(String fieldName, String value) {
        WebElement field = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(String.format("input[name='%s'], textarea[name='%s']", fieldName, fieldName))
            )
        );
        field.clear();
        field.sendKeys(value);
    }
}