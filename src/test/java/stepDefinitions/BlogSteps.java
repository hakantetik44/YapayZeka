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

public class BlogSteps {
    private WebDriver driver;

    public BlogSteps() {
        this.driver = Driver.getDriver();
    }

    @Given("Blog sayfasına git")
    @Step("Blog sayfasına gidiliyor")
    public void blogSayfasinaGit() {
        try {
            driver.get("http://localhost:3000");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            wait.until(driver -> {
                String title = driver.getTitle();
                System.out.println("Mevcut Başlık: " + title);
                return title != null && !title.isEmpty();
            });
            
            System.out.println("Sayfa URL: " + driver.getCurrentUrl());
            System.out.println("Sayfa Başlığı: " + driver.getTitle());
            
        } catch (Exception e) {
            System.out.println("HATA: Sayfa yüklenemedi!");
            System.out.println("Mevcut URL: " + driver.getCurrentUrl());
            System.out.println("Mevcut Başlık: " + driver.getTitle());
            System.out.println("Hata Mesajı: " + e.getMessage());
            throw e;
        }
    }

    @Then("Sayfa başlığı {string} olmalı")
    @Step("Sayfa başlığı kontrol ediliyor: {0}")
    public void sayfaBasligiOlmali(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Then("Menüde {string} linki olmalı")
    @Step("Menüde {0} linki kontrol ediliyor")
    public void menudeLinkiOlmali(String linkText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
            WebElement link = driver.findElement(By.linkText(linkText));
            Assert.assertTrue(linkText + " linki görünür değil!", link.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: " + linkText + " linki bulunamadı!");
            throw e;
        }
    }

    @Then("LinkedIn ikonu görünür olmalı")
    @Step("LinkedIn ikonu kontrol ediliyor")
    public void linkedinIkonuGorunurOlmali() {
        try {
            WebElement icon = driver.findElement(By.cssSelector("a[href*='linkedin.com'] i.fab.fa-linkedin"));
            Assert.assertTrue("LinkedIn ikonu görünür değil", icon.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: LinkedIn ikonu bulunamadı!");
            throw e;
        }
    }

    @Then("GitHub ikonu görünür olmalı")
    @Step("GitHub ikonu kontrol ediliyor")
    public void githubIkonuGorunurOlmali() {
        try {
            WebElement icon = driver.findElement(By.cssSelector(".social-icons .fab.fa-github"));
            Assert.assertTrue("GitHub ikonu görünür değil", icon.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: GitHub ikonu bulunamadı!");
            throw e;
        }
    }

    @Then("Twitter ikonu görünür olmalı")
    @Step("Twitter ikonu kontrol ediliyor")
    public void twitterIkonuGorunurOlmali() {
        try {
            WebElement icon = driver.findElement(By.cssSelector(".social-icons .fab.fa-twitter"));
            Assert.assertTrue("Twitter ikonu görünür değil", icon.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: Twitter ikonu bulunamadı!");
            throw e;
        }
    }

    @When("{string} bölümüne git")
    @Step("{0} bölümüne gidiliyor")
    public void bolumuneGit(String bolumAdi) {
        try {
            WebElement element = driver.findElement(By.linkText(bolumAdi));
            element.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains(bolumAdi.toLowerCase()));
        } catch (Exception e) {
            System.out.println("HATA: " + bolumAdi + " bölümüne gidilemedi!");
            throw e;
        }
    }

    @Then("{string} başlığı görünür olmalı")
    @Step("{0} başlığı kontrol ediliyor")
    public void basligiGorunurOlmali(String heading) {
        try {
            String xpath = String.format("//*[contains(text(),'%s')]", heading);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Assert.assertTrue(heading + " başlığı görünür değil", element.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: " + heading + " başlığı bulunamadı!");
            throw e;
        }
    }

    @Then("{string} kartı görünür olmalı")
    @Step("{0} kartı kontrol ediliyor")
    public void kartiGorunurOlmali(String cardTitle) {
        try {
            String xpath = String.format("//h3[contains(text(),'%s')]", cardTitle);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement card = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Assert.assertTrue(cardTitle + " kartı görünür değil", card.isDisplayed());
        } catch (Exception e) {
            System.out.println("HATA: " + cardTitle + " kartı bulunamadı!");
            throw e;
        }
    }
}