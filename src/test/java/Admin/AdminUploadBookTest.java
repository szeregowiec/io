package Admin;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AdminUploadBookTest {
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
  public void testAdminUploadBook() throws Exception {
    driver.get("http://localhost:8000/login");
    driver.findElement(By.id("inputEmail")).clear();
    driver.findElement(By.id("inputEmail")).sendKeys("admin@wp.pl");
    driver.findElement(By.id("inputPassword")).clear();
    driver.findElement(By.id("inputPassword")).sendKeys("admin");
    driver.findElement(By.xpath("//form[@id='formLogIn']/div[3]/label")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("nameOfUser")).click();
    driver.findElement(By.linkText("Ustawienia")).click();
    driver.findElement(By.id("inputTitle")).click();
    driver.findElement(By.id("inputTitle")).clear();
    driver.findElement(By.id("inputTitle")).sendKeys("Test");
    driver.findElement(By.id("inputAuthor")).clear();
    driver.findElement(By.id("inputAuthor")).sendKeys("Test");
    driver.findElement(By.id("inputKategory")).click();
    new Select(driver.findElement(By.id("inputKategory"))).selectByVisibleText("Thriller");
    driver.findElement(By.xpath("//option[@value='thriller']")).click();
    driver.findElement(By.id("inputPublishingHouse")).click();
    driver.findElement(By.id("inputPublishingHouse")).clear();
    driver.findElement(By.id("inputPublishingHouse")).sendKeys("Test");
    driver.findElement(By.id("inputDate")).clear();
    driver.findElement(By.id("inputDate")).sendKeys("1111");
    driver.findElement(By.id("inputPlace")).click();
    driver.findElement(By.id("inputPlace")).clear();
    driver.findElement(By.id("inputPlace")).sendKeys("Test");
    driver.findElement(By.id("inputPages")).click();
    driver.findElement(By.id("inputPages")).clear();
    driver.findElement(By.id("inputPages")).sendKeys("100");
    driver.findElement(By.id("inputDescription")).click();
    driver.findElement(By.id("inputDescription")).clear();
    driver.findElement(By.id("inputDescription")).sendKeys("Test");
    driver.findElement(By.id("inputLanguage")).click();
    new Select(driver.findElement(By.id("inputLanguage"))).selectByVisibleText("Jezyk polski");
    driver.findElement(By.xpath("//select[@id='inputLanguage']/option[2]")).click();
    driver.findElement(By.id("inputIsbn")).click();
    driver.findElement(By.id("inputIsbn")).clear();
    driver.findElement(By.id("inputIsbn")).sendKeys("1234567891258");
    driver.findElement(By.id("inputAmount")).click();
    driver.findElement(By.id("inputAmount")).clear();
    driver.findElement(By.id("inputAmount")).sendKeys("1");
    driver.findElement(By.id("inputCover")).click();
    driver.findElement(By.id("inputCover")).clear();
    driver.findElement(By.id("inputCover")).sendKeys("https://thumb7.shutterstock.com/display_pic_with_logo/161175115/524165524/stock-vector-book-cover-design-vector-template-in-a-size-annual-report-abstract-brochure-design-simple-524165524.jpg");
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.id("add")).click();
    driver.findElement(By.linkText("Katalog")).click();
    driver.findElement(By.id("coverId")).click();
    driver.findElement(By.id("deleteButton")).click();
    driver.findElement(By.id("nameOfUser")).click();
    driver.findElement(By.linkText("Ustawienia")).click();
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
