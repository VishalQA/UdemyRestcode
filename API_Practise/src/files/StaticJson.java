package files;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	@Test
	public void addBook() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";
		
		String responseaddbook = given().header("Content-Type", "application/json")
		.body(GenerateStaticStringFromResource("D:\\MyData_Dell_27June20\\Training_Udemy_RestAPI_23July2020\\API_Practise_Static.json"))
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
	
	public static String GenerateStaticStringFromResource(String path) throws IOException{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
}
