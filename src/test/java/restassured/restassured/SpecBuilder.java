package restassured.restassured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Location;
import pojo.PostGoogleMaps;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilder{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PostGoogleMaps pg= new PostGoogleMaps();
		List<String> typeList=new ArrayList<String>();
		typeList.add("shoe park");
		typeList.add("shop");
		pg.setTypes(typeList);
		Location l= new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		pg.setLocation(l);
		pg.setAccuracy(50);
		pg.setAddress("29, side layout, cohen 09");
		pg.setLanguage("French-IN");	
		pg.setName("Frontline house");
		pg.setPhone_number("(+91) 983 893 3937");
		pg.setWebsite("http://google.com");
		
		RequestSpecification request=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification response=new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();
		
		
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().spec(request).relaxedHTTPSValidation()
				.log().all()
				.body(pg).when().post("/maps/api/place/add/json")
		.then().spec(response)
		.extract().asString();
		System.out.println(Response);

	}

}

