package restassured.restassured;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class TestNgImp {
	Reusable payload= new Reusable(FilesData.complexJson());
	JsonPath js=payload.getpayload();
	int count=0; int sum=0;int purchaseamount=0;
	
	@Test(priority=1)
	public void purchaseamount()
	{

		 purchaseamount=js.getInt("dashboard.purchaseAmount");
		System.out.println("purchaseamount: "+purchaseamount);
	}
	
	@Test(priority=0)
	public void courseprice()
	{
		 count=js.getInt("courses.size()");
		for(int i=0;i<count;i++) {
			String course=js.get("courses["+i+"].title");
			int price=js.get("courses["+i+"].price");
			System.out.println("course:"+course);
			System.out.println("price: "+price);
		}
	}
	
	@Test(priority=2)
	public void CountCopies() {
	//print no of copies sold by RPA
			for(int i=0;i<count;i++)
			{
				String courseTitles=js.get("courses["+i+"].title");
				if(courseTitles.equalsIgnoreCase("RPA")) {
					int copies= js.getInt("courses["+i+"].copies");
					System.out.println("copies"+copies);
					break;
				}

			}
	}
	
	@Test(priority=3)
	public void SumOfAllCoursePrice() {
		//Verify the sum of all course price 
		for(int i=0;i<count;i++)
		{
			sum+=(js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies"));
		}
		System.out.println("sum"+sum);
		Assert.assertEquals(sum, purchaseamount);
		
	}
}
