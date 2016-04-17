package com.alimun.keywordframework;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alimun.keywordframework.util.Log;




public class FaceBookFriends {
	WebDriver driver;
	WebElement element;
	String BrowserType;
	//ExtentReports report;
	//ExtentTest test;
	
	@BeforeClass
	public void openBrowser() throws IOException{
		DOMConfigurator.configure("log4j.xml");
		Log.startTestCase("FaceBookFriends List");
		//report = new ExtentReports("C:\\Users\\Liaquat\\Desktop\\facebookFriends.html");
		//test = report.startTest("FaceBookFriend List Test");
		
		BrowserType =  "Mozilla";
		Log.info("Opening the browser: "+BrowserType);
		//test.log(LogStatus.INFO, "Opening the browser: "+BrowserType);
		if(BrowserType.equals("Mozilla")){
			driver = new FirefoxDriver();
			}else if (BrowserType.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
				driver =new ChromeDriver();
			}else if (BrowserType.equals("IE")){
				System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		//test.log(LogStatus.INFO, "Maximizing the browser..");
		driver.manage().window().maximize();
		///test.log(LogStatus.INFO, "Implicit wait for 20 sec");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	@Test
	public void faceBook_FriendList() throws InterruptedException {
		//login information
		String userEmail = "ayon88@hotmail.com";
		String encodedPswd = "TGl0dG9uNTc2Nw==";
		driver.get("https://www.facebook.com/");
		
		//login to facebook
		Log.info("Logging in to Facebook account");
		//test.log(LogStatus.INFO, "Logging in to Facebook account");
		driver.findElement(By.id("email")).clear();
		Log.info("Entering the user email: "+userEmail);
		//test.log(LogStatus.INFO, "Entering the user email" +userEmail);
		driver.findElement(By.id("email")).sendKeys(userEmail);
		Log.info("Entering the encoded password : "+encodedPswd);
		//test.log(LogStatus.INFO, "Entering the password" +encodedPswd);
		driver.findElement(By.id("pass")).sendKeys(decodedStr(encodedPswd));
		//test.log(LogStatus.INFO, "Clicking the login Button");
		driver.findElement(By.xpath("//label[@id='loginbutton']/input")).click();
		
		Thread.sleep(3000);
		Log.info("Clicking into my profile link");
		//test.log(LogStatus.INFO, "Clicking into my Profile Link");
		driver.findElement(By.xpath("//span[text()='Mohammad']")).click();
		Thread.sleep(3000);         
		
		Log.info("Clicking into my friends link");
		//test.log(LogStatus.INFO, "Clicking into myFriends Link");
		driver.findElement(By.xpath("//a[text()='Friends']")).click();
		 
		  //js.executeScript("window.location='http://gmail.com'");
		  //js.executeScript("document.getElementById('Email').value='selenium'");
		 		 
		 List<WebElement> friends = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));		  
		  //System.out.println("Total - "+ friends.size());
		  
		 while(true){
			 int beforeScrolling = friends.size();
			 int y = friends.get(friends.size()-1).getLocation().y;
			 JavascriptExecutor js = (JavascriptExecutor)driver;
			 js.executeScript("window.scrollTo(0, "+ y +" )");
			 Thread.sleep(5000);
			 friends = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));
			 int afterScrolling = friends.size();
			 
			 if(beforeScrolling == afterScrolling){
				 Log.info("Total no of friends is :"+friends.size());
				// test.log(LogStatus.INFO, "Total no of friends is :"+friends.size());
				 Log.info("Extracting and Printing the friends name");
				 //test.log(LogStatus.INFO, "Extracting and Printing the friends name");
				 for(int j=0; j<friends.size(); j++){
					String frindsName = (friends.get(j).getText());
					 Log.info("Friend Name "+ j +") is: "+ frindsName );
					 //test.log(LogStatus.INFO, "Friend Name "+ j +") is: "+ frindsName );
				 }
				 //test.log(LogStatus.PASS, "Listed all Friends List Successfully");
				 break;
			 }		
			
		 }
		  
	}
	@AfterClass
	public void EndTestNQuitBrow(){
		Log.endTestCase("End");
		//test.log(LogStatus.INFO,"Closing the Browser");
		driver.close();
		//report.endTest(test);
		//report.flush();
	}
	
	public static String decodedStr(String encodedPswd){
		byte[]decoded = Base64.decodeBase64(encodedPswd); 
		return  new String(decoded);
	}
}
