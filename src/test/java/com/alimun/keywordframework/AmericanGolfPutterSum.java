package com.alimun.keywordframework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alimun.keywordframework.util.Constants;
import com.alimun.keywordframework.util.Log;


public class AmericanGolfPutterSum {
	
	String BrowserType;
	WebDriver driver;
	Properties CONFIG;
	Properties OR;
	//ExtentReports report;
	//ExtentTest test;

	@BeforeClass
	public void openBrowser() throws IOException{
		DOMConfigurator.configure("log4j.xml");
		Log.startTestCase("AmericanGolfPutterSum VerificationTest");
		//report = new ExtentReports("C:\\Users\\Liaquat\\Desktop\\AmericanGolf.html");
		//test = report.startTest("American Golf PutterSum Test");
		Log.info("Initializing  CONFIG.prperties file");
		//test.log(LogStatus.INFO, "Initializing  CONFIG.prperties file");
		CONFIG = new Properties();
		FileInputStream fis = new FileInputStream (Constants.CONFIG_path);
		CONFIG.load(fis);
		//System.out.println(CONFIG.getProperty("URL_Name"));
		
		Log.info("Initializing OR.prperties file");
		//test.log(LogStatus.INFO,"Initializing OR.prperties file");
		OR = new Properties();
		fis = new FileInputStream (Constants.OR_path);
		OR.load(fis);
		//System.out.println(OR.getProperty("loginLink_xpath"));
		
		if(CONFIG.getProperty("BrowserType").equals("Mozilla")){
			Log.info("Openning Firefox Browser "+CONFIG.getProperty("BrowserType"));
			driver = new FirefoxDriver();
			}else if (CONFIG.getProperty("BrowserType").equals("Chrome")){
				Log.info("Openning Chrome Browser "+CONFIG.getProperty("BrowserType"));
				System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
				driver =new ChromeDriver();
			}else if (CONFIG.getProperty("BrowserType").equals("IE")){
				Log.info("Openning IE Browser "+CONFIG.getProperty("BrowserType"));
				System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		//test.log(LogStatus.INFO,"Maximizing Browser");
		driver.manage().window().maximize();
		//test.log(LogStatus.INFO,"Implicit wait for 20 sec");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	@Test
	public void sumOfGolfPutters() throws InterruptedException{
		
		//- Go to :http://www.americangolf.co.uk/golf-clubs/putters
		Log.info("Navigating to url: "+CONFIG.getProperty("americanGolf_URL"));
		//test.log(LogStatus.INFO,"Navigating to url: "+CONFIG.getProperty("americanGolf_URL"));
		driver.get(CONFIG.getProperty("americanGolf_URL"));
		Log.info(driver.getTitle());
		//test.log(LogStatus.INFO,driver.getTitle());
		Thread.sleep(3000);

		//check the sum
		//WebElement element = driver.findElement(By.id("//div[@class='refinement brand']//a[@class='listingchange']"));
		List<WebElement> putterCount =driver.findElements(By.xpath("//div[@class='refinement brand']//span[@class='refinement-count']"));
		
		System.out.println("No of putter count found is "+putterCount.size());
		Log.info("No of putter count found is "+putterCount.size());
		int sum =0;
		for (int i=0; i<putterCount.size(); i++){
			String putterText= putterCount.get(i).getText();
			String[] putterNo = putterText.split("\\(");
			System.out.println(putterNo[0]);
			String[] putterNum = putterNo[1].split("\\)");
			int putterNum1 = Integer.parseInt(putterNum[0]);
			Log.info("The No of putter for brand : "+putterNo[0]+ " is "+ putterNum1);
			//test.log(LogStatus.INFO, "The No of putter for brand : "+putterNo[0]+ " is "+putterNum1);
			sum =( sum +putterNum1);
			
		}
		Log.info("Sum of all Putter is: "+sum);
		//test.log(LogStatus.INFO, "Sum of all Putter is: "+sum);
		int putterActualNum =Integer.parseInt( driver.findElement(By.xpath("//*[@id='primary']/div[2]/div[1]/div[1]/span")).getText());
		Log.info("Actual total num of Putter from app : "+putterActualNum);
		System.out.println(putterActualNum);
		try{
		Assert.assertEquals(putterActualNum, sum);
		Log.info("Assertion pass since actual "+putterActualNum+" is matching with expected : " +sum);
		//test.log(LogStatus.PASS, "Assertion pass since actual "+putterActualNum+" is matching with expected : " +sum);
		}catch(Exception exp){
			System.out.println(exp.getMessage());
			Log.info("Failed to match because : "+exp.getMessage());
			//test.log(LogStatus.FAIL, "Failed to match because : "+exp.getMessage());
		}
	}
	
	@AfterClass
	public void EndTestNQuitBrow(){
		Log.endTestCase("AmericanGolfPutterSum VerificationTest");
		//test.log(LogStatus.INFO, "Closing the Browser");
		driver.close();
		//report.endTest(test);
		//report.flush();
	}
		
}
