package oauth;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Getcourses;
import pojo.Api;

public class OauthTest {
	
	public static void main(String[] args) {
		
//		String[] coursetitles = {"Selenium Webdriver Java","Cypress", "Protractor"};
		
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jaju_v\\Downloads\\chromedriver_win32\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		
		String url = "https://rahulshettyacademy.com/getCourse.php?state=validateapi&code=4%2F3AERAcVi_V-Kw0Y7ZaG6fXJLy_V7vRnEtmlToWnOF7Ep9JBwr_04Ghnys_-KrFy4JdBOG4Ri7agZmzVX0EZwsvA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
				
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope=")[0];
		
		System.out.println(code);
		
		
		
		String accesstokenresponse = given()
				.urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when()
		.log()
		.all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.asString();
		
		
		JsonPath js = new JsonPath(accesstokenresponse);
		String accesstoken = js.getString("access_token");
		
		Getcourses gc  =  given().queryParam("access_token", accesstoken)
	    .expect()
	    .defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php")
		.as(Getcourses.class);
		
//		System.out.println(response);
		
		System.out.println(gc.getCourses());
		System.out.println(gc.getExpertise());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getServices());
		
		
	}

}
