package restassured.restassured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class Oauth {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		try {
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.xpath("//*[@type='email']")).sendKeys("abinayaabisheku@gmail.com");
		driver.findElement(By.xpath("//*[@type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@type='password']")).sendKeys("Abisheku@123");
		driver.findElement(By.xpath("//*[@type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		String currenturl=driver.getCurrentUrl();
		//String currenturl="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWgavdfo5e1ZF6XM9xxghIX_Rc0DDdZTnWTA-LKunvmdeXw_q9ZqGMPf-bPT4CfZkKYRhA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
		currenturl=currenturl.substring((currenturl.indexOf("?")+1), currenturl.indexOf("&")).split("=")[1];
		driver.close();
		System.out.println("currenturl-------------------------------------------------"+currenturl);
		//System.out.println(currenturl.indexOf("?")+1);
		
		 System.out.println("Get access token");
		String accessToken=given().urlEncodingEnabled(false)
		.queryParams("code", currenturl)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(accessToken);
		JsonPath js= new JsonPath(accessToken);
		 accessToken=js.getString("access_token");
		
		
		
		
		 System.out.println("Get details");
		GetCourse GC=given().queryParam("access_token", accessToken).relaxedHTTPSValidation()
				.expect().defaultParser(Parser.JSON)
				
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(GC.getLinkedIn());
		System.out.println(GC.getExpertise());
		// printing price
		List<Api> api=GC.getCourses().getApi();
		for(int i=0;i<api.size();i++)
		{
			
			if(api.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(api.get(i).getPrice());
			}
			
		}
		ArrayList<String> al= new ArrayList();
		List<WebAutomation> webautomation=GC.getCourses().getWebAutomation();
		for(int i=0;i<webautomation.size();i++)
		{
			//get all course title
			al.add(
			webautomation.get(i).getCourseTitle());
		}
		//To compare array and arrayList
		String s[]= {"Selenium Webdriver Java","Cypress","Protractor"};
		Assert.assertEquals(Arrays.asList(s), al);


	}
	
	catch(Exception e)
	{
		System.out.println(e);
	}
}
}
