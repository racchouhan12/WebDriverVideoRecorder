package com.automationtools;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestWDVideoGen {

	WebDriver driver;
	WebDriverVideoRecorder videoRecorder;

	@BeforeTest
	public void setup(){
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver81.exe");
		driver = new ChromeDriver();
		videoRecorder = new WebDriverVideoRecorder(driver);
		videoRecorder.setRecorder(videoRecorder);
		videoRecorder.getRecorder().startRecording(System.getProperty("user.dir") + "/target/output", "articleSearch");
		driver.manage().window().maximize();
		driver.get("https://techcrunch.com/");
	}

	@Test
	public void testPage() {
		driver.findElement(By.cssSelector(".search-toggle.search-toggle--search")).click();
		driver.findElement(By.id("search-box-form__input")).sendKeys("starlink");
		driver.findElement(By.id("search-box-form__input")).sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//body[@id='ysch']//li//li[1]//div[2]//h4[1]//a[1]")).click();

	}

	@AfterTest
	public void tearDown(){
		try {
			videoRecorder.getRecorder().stopRecording();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

}
