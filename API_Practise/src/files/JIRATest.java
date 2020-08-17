package files;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JIRATest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		//Create session
		
		String sessionresponse = given()
				.relaxedHTTPSValidation()
		.header("Content-Type" ,"application/json")
		.body("{\"username\":\"vishal.mailbox1\", \"password\":\"Vishal123$\"}")
		.log()
		.all()
		.filter(session)
		.when()
		.post("/rest/auth/1/session")
		.then()
		.log()
		.all()
		.extract()
		.response()
		.asString();
		
		String expectedmessage = "Hi how r u ?";
		//Add comment to the issue
		
		String addcommentresponse = given().pathParam("id", "10000")
		.log()
		.all()
		.header("Content-Type" ,"application/json")
		.body("{\r\n" + 
				"    \"body\": \""+expectedmessage+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}")
		.filter(session)
		.when()
		.post("rest/api/2/issue/{id}/comment")
		.then()
		.log()
		.all()
		.assertThat()
		.statusCode(201)
		.extract()
		.response()
		.asString();
		
		JsonPath js = new JsonPath(addcommentresponse);
		String commentid = js.getString("id");
		
		
//		given()
//		.header("X-Atlassian-Token" ,"no-check")
//		.filter(session)
//		.pathParam("id", "10000")
//		.header("Content-Type" ,"multipart/form-data")
//		.multiPart("file" ,new File("Attachement.txt"))
//		.when()
//		.post("rest/api/2/issue/{id}/attachments")
//		.then()
//		.log()
//		.all()
//		.assertThat()
//		.statusCode(200);
		
		
		//get the issue details 
		
		String issuedetails = 
		given()
		.filter(session)
		.pathParam("id", "10000")
		.queryParam("fields", "comment")
		.log()
		.all()
		.when()
		.get("/rest/api/2/issue/{id}")
		.then()
		.log()
		.all()
		.extract()
		.response()
		.asString();
		
		System.out.println("The response is as below ====>");
		
		System.out.println(issuedetails);
		
		JsonPath js1 = new JsonPath(issuedetails);
		int commentscount = js1.get("fields.comment.comments.size()");
		
		for (int i=0; i<commentscount; i++) {
			
			String commentidissue = js1.get("fields.comment.comments["+i+"].id").toString();	
			if (commentidissue.equalsIgnoreCase(commentid)) {
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedmessage);
			}
			
			}
		
		
		
		

	}

}
