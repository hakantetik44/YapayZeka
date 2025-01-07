package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlogPage {
    WebDriver driver;

    @FindBy(tagName = "title")
    WebElement pageTitle;

    @FindBy(xpath = "//h1[text()='First Post']")
    WebElement firstPostTitle;

    public BlogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isFirstPostDisplayed() {
        return firstPostTitle.isDisplayed();
    }
}