package KojinkaTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.string;

public class AddProductCartTest {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;
    private final static String KOJINKA_BASE_URL = "https://kojinka.ru/";

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupDriver() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        driver.get(KOJINKA_BASE_URL);
    }

    @BeforeEach
    void login() {
        driver.findElement(By.cssSelector(".login")).click();
        driver.findElement(By.name("email")).sendKeys("irinas8@mail.ru");
        driver.findElement(By.name("password")).sendKeys("qwe12345");
        driver.findElement(By.xpath("//input[@value='Войти']")).click();

    }

    @Test
    void addProduct() { //добавление первого в списке каталога товара в корзину
        driver.findElement(By.xpath("//div[@class='menu-button-new-design']")).click();
        driver.findElement(By.xpath("//a[@class='open-pod-menu']/h2[contains(.,'Рюкзаки')]")).click();
        List<WebElement> productList = driver.findElements(By.xpath("//a[@class = 'a-category-kojinka-item']"));
        productList.get(0).click();

        driver.findElement(By.xpath("//div/p[contains(.,'В Корзину')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@class,'button btn')]")));
    }

    @Test
    void addSpecificProduct() throws InterruptedException {//добавление конкретного товара в корзину
        driver.findElement(By.xpath("//div[@class='menu-button-new-design']")).click();
        driver.findElement(By.xpath("//a[@class='open-pod-menu']/h2[contains(.,'Рюкзаки')]")).click();
        List<WebElement> productList = driver.findElements(By.xpath("//a[@class = 'a-category-kojinka-item']"));
        productList.stream().filter(p -> p.getText().contains("Кожаный рюкзак \"Вена\" (коричневый антик)")).findFirst().get().click();
        driver.findElement(By.xpath("//div/p[contains(.,'В Корзину')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@class,'button btn')]")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Кожаный рюкзак \"Вена\" (коричневый антик)')]")));
        assertThat(driver.getCurrentUrl(), containsString("route=checkout/simplecheckout"));

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
