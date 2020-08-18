package pojo;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class SelializeTest {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		Addplace p = new Addplace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setName("Frontline house");
		p.setWebsite("http://google.com");
		
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		
		p.setTypes(mylist);
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		Response xresponse = given().log().all().
				
								queryParam("key", "qaclick123")
				.body(p)
		.when()
		.post("/maps/api/place/add/json")
		.then()
		.assertThat()
		.statusCode(200)
		.extract()
		.response();
		
		
		String responsestring = xresponse.asString();
		
		System.out.println(responsestring);
		
		
		
	}

}
