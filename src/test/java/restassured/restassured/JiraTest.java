package restassured.restassured;

import org.testng.Assert;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraTest {
	String sessionId;String Response;String CommentId;String body;int id=10114;
	JsonPath js;
	SessionFilter newSession= new SessionFilter();
	
	@Test(priority=0)
	public void addLogin()
	{
		System.out.println("addLogin");
		RestAssured.baseURI="http://localhost:8081";
		 Response=given().log().all().header("Content-Type","application/json").body("{ \"username\": \"abinaya95srr\", \"password\": \"Abinaya\" }").filter(newSession)
		.when().post("rest/auth/1/session").then().log().all().assertThat().statusCode(200).extract().response().asString();
		 js= new JsonPath(Response);
		sessionId=js.getString("session.value");
		System.out.println("sessionId-----------------------------"+sessionId);
		
		
	}
	
	@Test(priority=1,enabled = false)
	public void addIssue()
	{
		System.out.println("addIssue");
		RestAssured.baseURI="http://localhost:8081";
		Response=given().log().all().header("Content-Type","application/json").header("Cookie","JSESSIONID="+sessionId).body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"RSA\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"credit card details\",\r\n"
				+ "        \"description\": \"credit card not working\",\r\n"
				+ "        \"issuetype\": \r\n"
				+ "            {\r\n"
				+ "                \"name\": \"Bug\"\r\n"
				+ "            }        \r\n"
				+ "    }\r\n"
				+ "}").when().post("/rest/api/2/issue").then().log().all().assertThat().statusCode(201).extract().response().asString();
		 js= new JsonPath(Response);
		 id=js.getInt("id");
		 
		 
	}
	
	@Test(priority=2,enabled = true)
	public void addComments()
	{
		System.out.println("addComments");
		RestAssured.baseURI="http://localhost:8081";
		 body="Comment section added by restAssured automation";
		Response=given().log().all().pathParam("id", id).header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+body+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(newSession).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		js= new JsonPath(Response);
		CommentId=js.getString("id");
	}
	
	@Test(priority=3,enabled = false)
	public void addAttachments()
	{
		given().header("X-Atlassian-Token","no-check").filter(newSession).pathParam("id", id)
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("Jira.txt"))
		.when()
		.post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
	}
	
	@Test(priority=4,enabled=true)
	public void getIssues()
	{
		RestAssured.baseURI="http://localhost:8081";
		Response=given().log().all().filter(newSession).pathParam("id", id)
		.queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{id}").then().log().all()
		.assertThat()
		.statusCode(200).extract().response().asString();
		js= new JsonPath(Response);
		int Commentscount=js.get("fields.comment.comments.size()");
		
		for(int i=0;i<Commentscount;i++) {
			
			if((js.get("fields.comment.comments["+i+"].id").toString().equalsIgnoreCase(CommentId))){
				String Message=js.get("fields.comment.comments["+i+"].body").toString();
				Assert.assertEquals(Message, body);
			}
		}
	}

}
