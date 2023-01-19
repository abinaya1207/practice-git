package restassured.restassured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Library {
	
	@DataProvider(name="BookDetails")
	public Object[][] get() {
		
		return new Object[][] {{"oip","300"}};
	}
	@DataProvider(name="id")
	public Object[][] getId() {
		
		//return new Object[][] {{"oip300"},{"shek301"},{"naya302"}};
		return new Object[][] {{"oip300"}};
	}
   
	@Test(dataProvider="BookDetails",priority=0)
	public void addBook(String isbn,String aisle) throws IOException
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all().header("content-type","application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Amstrong\\eclipse-workspace\\restassured\\Library.json"))))
				.when().post("/Library/Addbook.php")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		Reusable payload=new Reusable(response);
		JsonPath js=payload.getpayload();
		String addedBook=js.getString("ID");
		System.out.println(addedBook);
	}
	
	@Test(dataProvider="id",priority=1)
	public void deleteBook(String ID)
	{
		RestAssured.baseURI="http://216.10.245.166";
		given().log().all().body(FilesData.deleteBook(ID)).when().delete("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
	}

}
