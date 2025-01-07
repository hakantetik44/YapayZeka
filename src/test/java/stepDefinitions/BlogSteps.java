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

public class BlogSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    public BlogSteps() {
        this.driver = Driver.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("Blog sayfasına git")
    @Step("Blog sayfasına gidiliyor")
    public void blogSayfasinaGit() {
        try {
            String blogUrl = System.getProperty("blogUrl", "http://localhost:3000");
            driver.get(blogUrl);
            
            wait.until(driver -> {
                String title = driver.getTitle();
                System.out.println("Mevcut Başlık: " + title);
                return title != null && !title.isEmpty();
            });
            
        } catch (Exception e) {
            System.out.println("HATA: Sayfa yüklenemedi!");
            System.out.println("Hata Mesajı: " + e.getMessage());
            throw e;
        }
    }

    @Then("Sayfa başlığı {string} olmalı")
    @Step("Sayfa başlığı kontrol ediliyor: {0}")
    public void sayfaBasligiOlmali(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Sayfa başlığı beklenen değerden farklı!", expectedTitle, actualTitle);
    }

    @Then("Menüde {string} linki olmalı")
    @Step("Menüde {0} linki kontrol ediliyor")
    public void menudeLinkiOlmali(String linkText) {
        try {
            String sectionId = "";
            switch (linkText) {
                case "Hakkımda":
                    sectionId = "about";
                    break;
                case "Projeler":
                    sectionId = "projects";
                    break;
                case "Yetenekler":
                    sectionId = "skills";
                    break;
                default:
                    sectionId = linkText.toLowerCase();
            }
            
            // Nav menüsündeki linkleri bul
            String xpath = String.format("//nav//a[@href='#%s']", sectionId);
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Assert.assertTrue(linkText + " linki görünür değil!", link.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: " + linkText + " linki bulunamadı!");
            throw e;
        }
    }

    @When("{string} bölümüne git")
    @Step("{0} bölümüne gidiliyor")
    public void bolumuneGit(String bolumAdi) {
        try {
            String sectionId = "";
            switch (bolumAdi) {
                case "Hakkımda":
                    sectionId = "about";
                    break;
                case "Projeler":
                    sectionId = "projects";
                    break;
                case "Yetenekler":
                    sectionId = "skills";
                    break;
                default:
                    sectionId = bolumAdi.toLowerCase();
            }
            
            // Section'ı bul
            WebElement section = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(sectionId)));
            
            // Smooth scroll
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", section);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("HATA: Bekleme sırasında kesinti oluştu!");
            }
            
        } catch (Exception e) {
            System.out.println("HATA: " + bolumAdi + " bölümüne gidilemedi!");
            throw e;
        }
    }

    @Then("{string} başlığı görünür olmalı")
    @Step("{0} başlığı kontrol ediliyor")
    public void basligiGorunurOlmali(String heading) {
        try {
            String sectionId = "";
            switch (heading) {
                case "Hakkımda":
                    sectionId = "about";
                    break;
                case "Projeler":
                    sectionId = "projects";
                    break;
                case "Yetenekler":
                    sectionId = "skills";
                    break;
                default:
                    sectionId = heading.toLowerCase();
            }
            
            // Section başlığını bul
            String xpath = String.format("//section[@id='%s']//h2[contains(@class, 'glow-text')]", sectionId);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            
            // Görünürlük kontrolü
            Assert.assertTrue(heading + " başlığı görünür değil", element.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: " + heading + " başlığı bulunamadı!");
            throw e;
        }
    }
}