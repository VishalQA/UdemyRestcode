package basicAPI;

import files.Payloads;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ComplexJSONParse {
	
	public static void main(String[] args) {

	JsonPath js = new JsonPath(Payloads.courseprice());
	int coursecount = js.getInt("courses.size");
	System.out.println("1. The total courses are ===>  "   +coursecount);
	
	int totalamount = js.getInt("dashboard.purchaseAmount");
	System.out.println("2. Total amount is ====>"  +totalamount);
	
	String Alltitles = js.getString("courses.title");
	System.out.println("3. Title  is ====>"  +Alltitles);
	
	
	String Firsttitle = js.getString("courses[0].title");
	System.out.println("4. First Title  is ====>"  +Firsttitle);
	
	String Secondtitle = js.getString("courses[1].title");
	System.out.println("5. Second Title  is ====>"  +Secondtitle);
	
	String Thirdtitle = js.getString("courses[2].title");
	System.out.println("6. Third Title  is ====>"  +Thirdtitle);
	
	for(int i=0; i<coursecount; i++) {
		
		String titlelist = js.getString("courses["+i+"].title");
		System.out.println(titlelist);
		System.out.println(js.get("courses["+i+"].price").toString());
//		System.out.println(titleprice);

		
		
		
	
//		
//		String coursetiltelist = js.get("courses["+i+"]).title");
//		System.out.println(coursetiltelist);
		
	
		
	}
	
	System.out.println("Print copies sold by RPA");
	
	for (int i = 0 ; i<coursecount ; i++) {
		
//		String titlelist = js.getString("courses["+i+"].title");
		String listoftitle = js.getString("courses["+i+"].title");
//		
    	if (listoftitle.equalsIgnoreCase("RPA")) {
			
			int copies = js.get("courses["+i+"].copies");
			System.out.println("RPA sold copies are ===>" +copies);
			break;
		}
	}
	
	
	
	
	
}
	
}
