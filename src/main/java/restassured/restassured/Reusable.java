package restassured.restassured;

import io.restassured.path.json.JsonPath;

public class Reusable {
	
	
	JsonPath js;
	public Reusable(String ToJson) {
		js= new JsonPath(ToJson);
		
		// TODO Auto-generated constructor stub
	}
	
	public  JsonPath getpayload()
	{
		return js;
	}

	/*public static JsonPath rawToJson(String ToJson)
	{
		JsonPath js= new JsonPath(ToJson);
		return js;
	}*/

}
