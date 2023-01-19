package restassured.restassured;

import io.restassured.RestAssured;
import pojo.Location;
import pojo.PostGoogleMaps;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class Serialize {

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
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().queryParam("key", "qaclick123").relaxedHTTPSValidation()
				.log().all()
				.body(pg).when().post("/maps/api/place/add/json")
		.then().assertThat()
		.statusCode(200)
		.extract().asString();
		System.out.println(Response);

	}

}
