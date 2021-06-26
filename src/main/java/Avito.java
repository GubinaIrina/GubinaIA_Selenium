
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Avito {

    public static void findAndClick(WebDriver driver, String cssSelector) {
        WebElement deliveryButton = driver.findElement(By.cssSelector(cssSelector));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", deliveryButton);
        deliveryButton.click();
    }

    public static void findItem(WebDriver driver, String cssSelector) {
        WebElement item = driver.findElement(By.cssSelector(cssSelector));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", item);
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.get("https://www.avito.ru/");
        Select category = new Select(driver.findElement(By.id("category")));
        category.selectByValue("99");
        driver.findElement(By.cssSelector("[data-marker='search-form/suggest']"))
                .sendKeys("Принтер");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
                ("[data-marker='suggest/list/item-with-category']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-marker='search-form/region']")))
                .click();
        WebElement fieldCity = driver.findElement(By.cssSelector("[data-marker='popup-location/region/input']"));
        fieldCity.clear();
        fieldCity.sendKeys("Владивосток", Keys.ENTER);
        driver.findElement(By.cssSelector("[data-marker='suggest(0)']")).click();
        driver.findElement(By.cssSelector("[data-marker='popup-location/save-button']")).click();
        findAndClick(driver, "[data-marker='delivery-filter/text']");
        driver.findElement(By.cssSelector("[data-marker='search-filters/submit-button']")).click();
        WebElement option = driver.findElement(By.cssSelector(".sort-select-3QxXG.select-select-box-3LBfK" +
                ".select-size-s-2gvAy"));
        Select moreExpensive = new Select(option.findElement(By.cssSelector(".select-select-3CHiM")));
        moreExpensive.selectByVisibleText("Дороже");
        findItem(driver, "[data-marker='catalog-serp']");

        List<WebElement> printList = driver.findElements(By.cssSelector("[data-marker='item']"));
        for (int i = 0; i < 3; i++) {
            System.out.println("Название принтера: " +
                    printList.get(i).findElement(By.cssSelector("[itemprop='name']")).getText()
                    + " Стоимость принтера: " + printList.get(i).findElement(By.cssSelector
                    ("[data-marker='item-price']")).getText());
        }

        driver.quit();
    }
}
