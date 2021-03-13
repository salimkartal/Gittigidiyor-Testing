package base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Base {
    protected WebDriver driver;

    @Before
    public void setUp(){
     //   logger.info("Test baslatildi.");
        System.setProperty("webdriver.chrome.driver","C:/driver/chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void quitDriver(){
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.quit();
      //  logger.info("Test tamamlandi.");
    }
}
