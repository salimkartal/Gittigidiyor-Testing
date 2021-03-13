# Gittigidiyor-Testing

import base.Base;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ExampleTest extends Base {
   // protected WebDriver driver;
    final static Logger logger = Logger.getLogger(ExampleTest.class);
    public static String loginUrl="https://www.gittigidiyor.com/";

    @Before
    public void setUp(){
        logger.info("Test baslatildi.");
        super.setUp();
    }

    @Test
    public void correctLogin(){
        //1)www.gittigidiyor.com sitesi açılır
        driver.get(loginUrl);
        logger.info("Test Edilecek Sayfa : " + driver.getTitle());

        //2)Anasayfanin acildigi kontrol edilir.Siteye login olunur.
        Assert.assertEquals("https://www.gittigidiyor.com/",driver.getCurrentUrl());
        driver.findElement(By.cssSelector("#main-header > div:nth-child(3) > div > div > div > div.sc-1nx8ums-0.oAQul > div > div:nth-child(1) > div > div.gekhq4-4.hwMdSM")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#main-header > div:nth-child(3) > div > div > div > div.sc-1nx8ums-0.oAQul > div > div.gekhq4-5.IIkXV > div.sc-3wdx43-0.bAxXdY > div > div > div > a")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        WebElement mail= driver.findElement(By.id("L-UserNameField"));
        mail.click();
        mail.sendKeys("salimkartal@outlook.com.tr");
        WebElement password = driver.findElement(By.id("L-PasswordField"));
        password.click();
        password.sendKeys("bursa1320");
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.id("gg-login-enter")).click();

        //3)Login islemi kontrol edilir
        Assert.assertTrue(driver.getPageSource().contains("Hesabım"));
        logger.info("Giris yapildi.");

        //4)Arama kutucuguna bilgisayar kelimesi girilir
        WebElement searchBox= driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input"));
        searchBox.click();
        searchBox.sendKeys("Bilgisayar");
        driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button")).click();
        logger.info("Arama kutucuguna bilgisayar kelimesi girildi");

        //5)Arama sonuclari sayfasindan 2.sayfa acilir
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#best-match-right > div.pager.pt30.hidden-m.gg-d-24 > ul > li:nth-child(2) > a")).click();
        logger.info("2.sayfa acildi");

        //6)2.sayfanin acildigi kontrol edilir
        Assert.assertEquals("https://www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2",driver.getCurrentUrl());

        //7)Sonuca göre sergilenen urunlerden rastgele bir urun secilir
        driver.findElement(By.xpath("//*[@id=\"item-info-block-666958175\"]/p/img")).click();
        logger.info("Rastgele bir urun secildi");

        //8)Secilen urun sepete eklenir
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        driver.findElement(By.id("add-to-basket")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        logger.info("Urun sepete eklendi");

        //9)Urun sayfasindaki fiyat ile sepette yer alan urunun fiyatinin dogrulugu karsilastirilir
        WebElement price= driver.findElement(By.id("sp-price-lowPrice"));
        String priceText=price.getText();
        WebElement priceBasket= driver.findElement(By.className("product-new-price"));
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        String priceBasketText= priceBasket.getText();
        Assert.assertEquals(priceText,priceBasketText);
        logger.info("Fiyat karsilastirmasi yapildi");

        //Sepete gidilir
        driver.findElement(By.xpath(".//a[@class='header-cart-hidden-link']")).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

        //10)Adet arttirilarak urun adedinin 2 oldugu dogrulanir.
        By option = By.xpath("//select[@class = 'amount']/option[@value = '2']");
        driver.findElement(option).click();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        logger.info("Urun adedi 2 oldugu dogrulandi");

        //11)Urun sepetten silinerek sepetin bos oldugu kontrol edilir
        driver.findElement(By.xpath(".//a/i[@class='gg-icon gg-icon-bin-medium']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Sepetinizde ürün bulunmamaktadır"));
        logger.info("Sepetin bos oldugu kontrol edildi");

    }
    @After
    public void quitDriver(){
        super.quitDriver();
        logger.info("Test tamamlandi.");
    }
}
