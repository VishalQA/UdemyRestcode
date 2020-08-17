package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {
	
	@Test(dataProvider="Booksdata")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		
		String responseaddbook = given().header("Content-Type", "application/json")
		.body(Payloads.Addbooks(isbn ,aisle))
		.when()
		.post("Library/Addbook.php")
		.then()
		.log()
		.all()
		.assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = Reusablemethods.Rawtojson(responseaddbook);
		String id = js.getString("ID");
		
		System.out.println("The book id is ===>"  +id);
		
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void deleteBook(String id) {
		RestAssured.baseURI = "http://216.10.245.166";
		
		String responsedeletebook = given().header("Content-Type", "application/json")
		.body(Payloads.Deletebooks(id))
		.when()
		.post("/Library/DeleteBook.php")
		.then()
		.log()
		.all()
		.assertThat().statusCode(200)
		.extract().response().asString();
		
//		JsonPath js = Reusablemethods.Rawtojson(responsedeletebook);
//		String id = js.getString("ID");
		
//		System.out.println("The book id is ===>"  +id);
		
	}
	
	@DataProvider(name="Booksdata")
	public Object[][] getdata() {
		
		return new Object [][]{{"zMonday","1007"},{"zTuesday","1008"},{"zWednesday","1009"}};
	}

}
