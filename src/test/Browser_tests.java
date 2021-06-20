package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class) public class Browser_tests {

    @Parameters public static Collection<String[]> browsers() {
        return Arrays.asList(new String[][] {{"edge"}, {"chrome"}});
    }

    public Browser_tests(String browser) {
        this.browser = browser;
    }

    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;
    private String baseUrl = "http://www.amazon.de";
    private String browser;

    @Before public void setUp() {
        if (browser.equals("edge")) {
            System.setProperty("webdriver.edge.driver", "C:/Program Files (x86)/Microsoft/Edge/Application/msedgedriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.edge();
            capabilities.setCapability("test_edge", true);
            capabilities.setJavascriptEnabled(true);
            driver = new EdgeDriver(capabilities);
        } else
       if (browser.equals("chrome")) {
           System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/chromedriver.exe");
           ChromeOptions options = new ChromeOptions();
           options.addArguments("--headless", "--disable-gpu");
           driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test public void testThatDemonstratesSelenium() {
        driver.get(baseUrl);
        driver.findElement(By.id("twotabsearchtextbox")).clear();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("bürostuhl gr");
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.attributeContains(By.id("nav-flyout-searchAjax"), "style", "display: block;"));

        //arrow down one time to select 'bürostuhl grau'
        for(int i = 0; i<1;i++){
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ARROW_DOWN);
        }

        //press enter to choose 'bürostuhl grau'
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ENTER);

//        new WebDriverWait(driver, 10)
//                .until(ExpectedConditions.urlContains("k=b%C3%BCrostuhl+grau"));
//        System.out.println(driver.getCurrentUrl());

        assertTrue(driver.getCurrentUrl().contains("k=b%C3%BCrostuhl+grau"));
    }

    @After public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
