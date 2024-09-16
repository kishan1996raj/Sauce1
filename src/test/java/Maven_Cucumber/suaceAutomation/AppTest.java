package Maven_Cucumber.suaceAutomation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/features",glue ="stepdefination"
,monochrome=true, 
plugin= {"html:target/cucumber.html", "json:target/cucumber.json",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class AppTest extends AbstractTestNGCucumberTests{
	
//Uncomment the below code to execute scenarios parallel
	//There is an bug which i have kept intentionally , to remove it please go to ProductSD.java -> line 128 and remove ! from  Assert.assertTrue(!productPage.clickProduct(product));
	
/*	@Override
	@DataProvider(parallel=true)
	public Object[][] scenarios()
	{
		return super.scenarios();
	}
	*/
}