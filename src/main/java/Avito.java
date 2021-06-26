
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Avito {

    public static void findAndClick(WebDriver driver, String xPath) {
        WebElement deliveryButton = driver.findElement(By.xpath(xPath));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", deliveryButton);
        deliveryButton.click();
    }

    public static void findItem(WebDriver driver, String cssSelector) {
        WebElement item = driver.findElement(By.cssSelector(cssSelector));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", item);
        List<WebElement> printList = driver.findElements(By.cssSelector(".items-items-38oUm"));
        for (int i = 0; i < printList.size(); i++) {
            System.out.println("Название принтера: " +
                    printList.get(i).findElement(By.cssSelector(".iva-item-titleStep-2bjuh")).getText()
                    + " Стоимость принтера: " + printList.get(i).findElement(By.cssSelector(".iva-item-priceStep-2qRpg"))
                    .getText());
        }
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get("https://www.avito.ru/");
        Select category = new Select(driver.findElement(By.id("category")));
        category.selectByValue("99");
        driver.findElement(By.cssSelector(".suggest-root-1v2AH.js-suggest.suggest_search"))
                .sendKeys("Принтер");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".form-suggest-match"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".main-text-2PaZG"))).click();
        WebElement fieldCity = driver.findElement(By.cssSelector(".suggest-input-3p8yi"));
        fieldCity.clear();
        fieldCity.sendKeys("Владивосток", Keys.ENTER);
        driver.findElement(By.cssSelector(".suggest-suggest-content-KQ__w")).click();
        driver.findElement(By.cssSelector(".button-button-2Fo5k.button-size-m-7jtw4.button-primary-1RhOG")).click();
        findAndClick(driver, "/html/body/div[1]/div[3]/div[3]/div[1]/div/div[2]/div[1]/form/div[6]/div/" +
                "div/div/div/div/div/label");
        driver.findElement(By.cssSelector(".button-button-2Fo5k.button-size-s-3-rn6.button-primary-1RhOG." +
                "width-width-12-2VZLz")).click();
        WebElement option = driver.findElement(By.cssSelector(".sort-select-3QxXG.select-select-box-3LBfK" +
                ".select-size-s-2gvAy"));
        option.click();
        Select moreExpensive = new Select(option.findElement(By.cssSelector(".select-select-3CHiM")));
        moreExpensive.selectByVisibleText("Дороже");
        findItem(driver, ".iva-item-content-m2FiN");

        driver.quit();
    }
}
