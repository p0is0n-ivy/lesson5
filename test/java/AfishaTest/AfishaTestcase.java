package AfishaTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AfishaTestcase {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;
    private final static String AFISHA_BASE_URL = "https://www.afisha.ru/";

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupDriver() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
        actions = new Actions(driver);
        driver.get(AFISHA_BASE_URL);
    }

    @Test
    void addToFavorite() throws InterruptedException {
        login();
        Thread.sleep(500);
        actions.moveToElement(driver.findElement(By.xpath("//nav/a[contains(., 'КИНО')]")))
                .build()
                .perform();
        driver.findElement(By.xpath("//a[contains(., 'Почему темнокожие Анна Болейн, Золушка и Русалочка — это нормально')]")).click();
        driver.navigate().to(driver.getCurrentUrl());
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int i = driver.findElements(By.xpath("//div[@class='HfGWu _3U5bB'][contains(., 'Драма о последних днях Анны Болейн')]//following-sibling::div/div")).size();
        while (i == 0) {
            jse.executeScript("scroll(0,1000);");
        }
        driver.findElement(By.xpath("//div[@class='HfGWu _3U5bB'][contains(., 'Драма о последних днях Анны Болейн')]//following-sibling::div/div")).click();
        int j = driver.findElements(By.xpath("//div[@data-test='IMAGE FALLBACK']")).size();
        while (j == 0) {
            jse.executeScript("scroll(0,-1000);");
        }
        actions.moveToElement(driver.findElement(By.xpath("//div[@data-test='IMAGE FALLBACK']")))
                .build()
                .perform();
        driver.findElement(By.xpath("//a[@class='_3NqYW DWsHS _1l5db']//div[@class='_3zDWC _3x7YU _2cFJG PzbtY _1zSKj']//div[contains(., 'Избранное')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='_3Yfoo'] [contains (.,'Драма о последних днях Анны Болейн')]")));
        assertEquals(driver.findElement(By.xpath("//div[@class='_3Yfoo'] [contains (.,'Драма о последних днях Анны Болейн')]")).isDisplayed(), true);
        Thread.sleep(5000);
    }

    @Test
    void FindFilm() throws InterruptedException {
        login();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='_3jiFM _2oJKT _1wjeh NYM3K _2wba5 _3MVGy _1e-25']")).click();
        driver.findElement(By.xpath("//input[@class='_1zHcQ _2DZMQ Y1g6a _3c12L']")).sendKeys("Человек-паук");
        By.xpath("//div[@class='_2PoNO _3YJjH'][contains(., 'Найти')]").findElement(driver).click();
        Thread.sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 200);");
        List<WebElement> filmsList = driver.findElements(By.xpath("//ul[@class='_22-Vd _267Dy']/div[@class='_1kwbj lkWIA _2Ds3f']"));
        filmsList.get(0);
        int filmlistSize = filmsList.size();
        System.out.println("По запросу 'Человек-паук' найдено " + filmlistSize + " фильмов");
        String actualString = driver.findElement(By.xpath("//ul[@class='_22-Vd _267Dy']/div[@class='_1kwbj lkWIA _2Ds3f']")).getText();
        Assert.assertTrue(actualString.contains("Человек-паук"));
        Thread.sleep(5000);
    }

    void login() throws InterruptedException {
        driver.manage().window().setSize(new Dimension(1800, 1000));
        driver.findElement(By.xpath("//button[.='Войти']")).click();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'rambler.ru/login')]")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        driver.findElement(By.id("login")).sendKeys("irinasiv230@mail.ru");
        driver.findElement(By.id("password")).sendKeys("QWErty123");
        driver.findElement(By.xpath("//span[.='Войти']")).click();
        Thread.sleep(5000);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

