package restassured.restassured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basics {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().relaxedHTTPSValidation().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(FilesData.addPlace()).when()
		.post("/maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		/*String Response="{\r\n" + 
				"    \"status\": \"OK\",\r\n" + 
				"    \"place_id\": \"46114769add4f39f7f74b35b4299930d\",\r\n" + 
				"    \"scope\": \"APP\",\r\n" + 
				"    \"reference\": \"736f3c9bec384af62a184a1936d42bb0736f3c9bec384af62a184a1936d42bb0\",\r\n" + 
				"    \"id\": \"736f3c9bec384af62a184a1936d42bb0\"\r\n" + 
				"}\r\n" + 
				"";*/
		JsonPath js= new JsonPath(Response);
		String place_id=js.getString("place_id");
		System.out.println(place_id);	
		//update place
		given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+place_id+"\",\r\n" + 
				"\"address\":\"50 Summer walk, IND\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"").when().put("/maps/api/place/update/json").then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		//RestAssured.baseURI="https://postman-library-api.glitch.me";
		//given().log().all().header("Content-Type","application/json").body(Files.newBooks()).post("books").then().assertThat().statusCode(201)
		//.body("message", equalTo("OK"));
		
	//	String Books=given().log().all().get("books").then().extract().response().asString();
	//	System.out.println(Books);

		
		
		/*String id="8UoRxZgHq0las70";
		//patch
		String update="ActionHorror";
		String response=given().log().all().header("Content-Type","application/json").body("{\r\n" + 
				"    \"genre\": \""+update+"\"\r\n" + 
				"}").patch("/books/"+id).then().extract().response().asString();
		
		//get book
		String book=given().get("/books/8UoRxZgHq0las70").then().assertThat().extract().response().asString();
		System.out.println(book);
		System.out.println(response);*/

	}

}
