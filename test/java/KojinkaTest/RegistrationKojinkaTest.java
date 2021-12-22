package KojinkaTest;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WrapsElementMatchers.isDisplayed;

public class RegistrationKojinkaTest {
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

    @Test
    void PositiveRegistrationTest() {
        driver.manage().window().setSize(new Dimension(1800, 1000));
        driver.findElement(By.xpath("//li[contains(.,'Регистрация')]")).click();
        driver.findElement(By.name("register[email]")).sendKeys("irinas9321@mail.ru");
        driver.findElement(By.name("register[firstname]")).sendKeys("Ирина");
        driver.findElement(By.name("register[telephone]")).sendKeys("8-911-54-33-30");
        driver.findElement(By.name("register[password]")).sendKeys("qwe123");
        driver.findElement(By.name("register[confirm_password]")).sendKeys("qwe123");
        driver.findElement(By.xpath("//a[contains(.,'Продолжить')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Ваша учётная запись создана!')]")));
    }

    @Test
    void UserWithSuchEmailAlreadyExistsTest() throws InterruptedException {
        driver.manage().window().setSize(new Dimension(1800, 1000));
        driver.findElement(By.xpath("//li[contains(.,'Регистрация')]")).click();
        driver.findElement(By.name("register[email]")).sendKeys("irinas91134@mail.ru");// пользователь с таким email уже зарегистрирован
        driver.findElement(By.name("register[firstname]")).sendKeys("Ирина");
        driver.findElement(By.name("register[telephone]")).sendKeys("8-911-94-13-3");
        driver.findElement(By.name("register[password]")).sendKeys("qwe123");
        driver.findElement(By.name("register[confirm_password]")).sendKeys("qwe123");
        driver.findElement(By.xpath("//a[contains(.,'Продолжить')]")).click();
        Assertions.assertEquals(driver.findElement(By.xpath("//div[contains(., 'Адрес уже зарегистрирован!')]")).isDisplayed(), true);


    }

    @Test
    void WrongConfirmPasswordTest() {
        driver.manage().window().setSize(new Dimension(1800, 1000));
        driver.findElement(By.xpath("//li[contains(.,'Регистрация')]")).click();
        driver.findElement(By.name("register[email]")).sendKeys("irinas71134@mail.ru");
        driver.findElement(By.name("register[firstname]")).sendKeys("Ирина");
        driver.findElement(By.name("register[telephone]")).sendKeys("8-911-94-33-30");
        driver.findElement(By.name("register[password]")).sendKeys("qwe123");
        driver.findElement(By.name("register[confirm_password]")).sendKeys("rwe123");
        driver.findElement(By.xpath("//a[contains(.,'Продолжить')]")).click();
        Assertions.assertEquals(driver.findElement(By.xpath("//div[contains(., 'Подтверждение пароля не соответствует паролю!')]")).isDisplayed(), true);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
