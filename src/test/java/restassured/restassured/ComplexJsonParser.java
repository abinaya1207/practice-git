package restassured.restassured;

import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class ComplexJsonParser {
	
	/*1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course

4. Print All course titles and their respective Prices

5. Print no of copies sold by RPA Course

6. Verify if Sum of all Course prices matches with Purchase Amount*/
	
	/*public static void main(String args[])
	{
		JsonPath js=Reusable.rawToJson(Files.complexJson());
		int count=js.getInt("courses.size()");
		System.out.println(count);
		int purchaseamount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseamount);
		
		
		for(int i=0;i<count;i++) {
			String course=js.get("courses["+i+"].title");
			int price=js.get("courses["+i+"].price");
			System.out.println(course);
			System.out.println(price);
		}
		
		//print no of copies sold by RPA
		for(int i=0;i<count;i++)
		{
			String courseTitles=js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				int copies= js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}

		}
		
		//Verify the sum of all course price 
		int sum=0;
		for(int i=0;i<count;i++)
		{
			
			 sum+=(js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies"));
		}
	
		
		Assert.assertEquals(sum, purchaseamount);
		

	}

	*/

}
