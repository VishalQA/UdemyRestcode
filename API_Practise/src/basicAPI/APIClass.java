package basicAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payloads;
import files.Reusablemethods;
public class APIClass {
	
	public static void main(String[] args) {
		//POST
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key" , "qaclick123").header("Content-Type" ,"application/json")
		.body(Payloads.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat()
		.statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server" ,"Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String placeid = js.getString("place_id");
		System.out.println("place id is ===>  "  +placeid);
		
		
//		//PUT
		String newaddress = "106, Summer walk, USA";
		
		given().log().all().queryParam("key" ,"qaclick123" ).header("Content-Type" ,"application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+newaddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n"+"")
		.when().put("/maps/api/place/update/json")
		.then()
		.assertThat()
		.log()
		.all()
		.statusCode(200)
		.body("msg" ,equalTo("Address successfully updated") )
		.extract().response().asString();
	
		
		//GET 
		
		String getPlaceResponse  = given().log().all().queryParam("key" , "qaclick123")
		.queryParam("place_id", placeid)
		.when()
		.get("/maps/api/place/get/json")
		.then()
		.assertThat()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.asString();
		
		
		JsonPath js1 = Reusablemethods.Rawtojson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		System.out.println("New updated Address is ==>  "  +actualAddress);
		
		//JUnit, TestNG
		
		Assert.assertEquals(actualAddress, newaddress);
		
		
	}

}
