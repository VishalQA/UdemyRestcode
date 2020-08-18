package pojo;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecBuilder {
	
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
		
		
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification responsespec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		
		RequestSpecification xresponse = given()
		.spec(req)
		.body(p);
		
		
	
		
		Response yresponse = xresponse.when()
		.post("/maps/api/place/add/json")
		.then()
		.spec(responsespec)
		.extract()
		.response();
		
		
		String responsestring = yresponse.asString();
		
		System.out.println(responsestring);
		
		
		
	}

}
