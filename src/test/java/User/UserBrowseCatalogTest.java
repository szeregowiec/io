package User;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class UserBrowseCatalogTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUserBrowseCatalog() throws Exception {
    driver.get("http://localhost:8000/login");
    driver.findElement(By.id("inputEmail")).clear();
    driver.findElement(By.id("inputEmail")).sendKeys("adam.buczek17@gmail.com");
    driver.findElement(By.id("inputPassword")).clear();
    driver.findElement(By.id("inputPassword")).sendKeys("adam123");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Katalog")).click();
    driver.findElement(By.id("coverId")).click();
    driver.findElement(By.linkText("Katalog")).click();
    driver.findElement(By.xpath("(//img[@alt='Brak okładki'])[2]")).click();
    driver.findElement(By.linkText("Katalog")).click();
    driver.findElement(By.linkText("Akcja")).click();
    driver.findElement(By.linkText("Biograficzne")).click();
    driver.findElement(By.linkText("Dla dzieci")).click();
    driver.findElement(By.linkText("Inne")).click();
    driver.findElement(By.id("coverId")).click();
    driver.findElement(By.linkText("Katalog")).click();
    driver.findElement(By.id("inputFindBook")).click();
    driver.findElement(By.id("inputFindBook")).clear();
    driver.findElement(By.id("inputFindBook")).sendKeys("we dwoje");
    driver.findElement(By.id("formFindBook")).submit();
    driver.findElement(By.id("coverId")).click();
    driver.findElement(By.linkText("Katalog")).click();
    driver.findElement(By.id("nameOfUser")).click();
    driver.findElement(By.linkText("Wyloguj")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
