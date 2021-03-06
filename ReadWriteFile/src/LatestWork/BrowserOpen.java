package LatestWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import LatestWork.GoToWebPath;

public class BrowserOpen {
	 private static final String WebElement = null;
	static WebDriver driver;
	 static String ApplicationLocation = getJarPath(); //D:\reportingTool\bin

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
		String ExcelFileLocation = ApplicationLocation +"\\InputXpathFirefox.xlsx";   
		

		 driver = BrowserOption(ApplicationLocation);
		 String baseUrl = "https://amadeus.cytric.net/ibe/?system=Demo_MMahato";
		 
		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);
		waitforlogin(driver, baseUrl);
		GoToPath(driver,"//a[text()='Management']");
		GoToPath(driver,"//html[1]/body[1]/div[1]/div[2]/header[1]/div[1]/nav[1]/ul[1]/li[2]/ul[1]/li[2]/a[1]");
		/*GoToPath(driver,"//a[text()='Travel Policy Administration']");
		GoToPath(driver,"//*[@id='primaryContent']/ul/li[4]/a");
		GoToPath(driver,"//*[@id='primaryContent']/ul/li[1]/a");
		GoToPath(driver,"//*[@id='primaryContent']/div[3]/div/div/div[1]/ul/li[1]/a");*/
			
			
		 
		//driver.get("D://Airpolicy.html");
			
		
		//ConsoleOutput();
		//driver.get("D://Airpolicy.html");
		//driver.getPageSource(); 
		
		//Read from the Excel file
		ExcelData1 excel = new ExcelData1(ExcelFileLocation, 0);
		ReadLinesExcel(excel);
		/*String currentUrl = driver.getCurrentUrl();
		System.out.println("The Current Url is:" + currentUrl);
		driver.getPageSource();*/
		Thread.sleep(1000);
		
		driver.close();	
		    

	}
	/* public static void ConsoleOutput(){
		 try {
			PrintStream myconsole = new PrintStream(new File ("D://Airpolicy.html"));
			 System.setOut(myconsole);
			 System.out.println(""+driver.getPageSource());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 } */
	
	public static void ReadLinesExcel(ExcelData1 excel) throws InterruptedException {
		String PreviousPath = "";
		for (int i = 1; i <= excel.getLastRowNum(); i++) {
			//String Name = excel.getData(i,0);
			String CurrentPath = excel.getData(i,1);
			String xPath = excel.getData(i,2);
			String type = excel.getData(i,3);
			 
			GoToWebPath.GoToWebPath(PreviousPath, CurrentPath, driver);
			PreviousPath = CurrentPath;
			
			
			String OutputData = WhichFunctionToUse(type, xPath);
			//System.out.println(OutputData);
			
			try {
				System.out.println(OutputData);
				excel.WriteExcel(OutputData, i);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			excel.CloseExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			
		} 
		driver.close();
	}
	
	private static String WhichFunctionToUse(String type, String xPath) throws InterruptedException {
		String OutputData ="";
		if (WaitFor(driver, xPath)){
			switch (type)
			{
			
			  case "List":
				OutputData = ListFunction(driver,xPath);
			    break;
			    
			 
			    
			  case "Checkbox":
				OutputData =Checkboxfunction(driver,xPath);
				break;
				
			 case "MultiList":
				  OutputData = MultiListFunction(driver,xPath);
				  break;
			 case "TextBox":
				 OutputData = TextBoxFunction(driver,xPath);
				 break;
			  case "RadioButton":
				  OutputData = RadioButtonFunction(driver,xPath);
				  break;
			  default:
			    System.out.println("function not found");
			}
		}else{
			OutputData = "X-Path not found";
		}
			
			
			
		
		return OutputData;
	}

	/*private static void GoToWebPath(String TempPath, String Path) throws InterruptedException {
		//parse the path to use the GotoPath function
		
		
	} */

	public static String ListFunction(WebDriver driver,String Xpath) throws InterruptedException {
		String output =null;
		
		//System.out.println(""+Xpath);
		List<WebElement> dropboxes = driver.findElements(By.xpath(Xpath));
		for (int i = 0; i < dropboxes.size(); i++) {

		
			if(dropboxes.get(i).isSelected()){
				if (output == null) {
					output = (dropboxes.get(i).getText());
				}else{
					output = output + "/"+ (dropboxes.get(i).getText());
				}
				 
				
			} 
		}
		return output;
	}
	
	public static String MultiListFunction(WebDriver driver,String Xpath) throws InterruptedException {
		String output=null;
		String check="option checked";
		//List<WebElement> MultiList = driver.findElements(By.xpath(Xpath));
		//for (int i = 0; i < MultiList.size(); i++) {
			WebElement MultiList =  driver.findElement(By.xpath(Xpath));
			output=(MultiList.getAttribute("class"));
			if(output.equals(check))
			{
				output="checked";
			}
			else{
				output="Not checked";
			}
			//if(MultiList.get(i).isSelected()){
				//if (output == null) {
					//output = (MultiList.get(i).getText());
				//}else{
				//output = (MultiList.get(i).getText())+ "/"+ (MultiList.get(i).getAttribute("class"));
				//}
				 
				
			//} 
		//}
		return output;
	} 
	public static String TextBoxFunction(WebDriver driver,String Xpath) throws InterruptedException {
	String output=null;
	
	List<WebElement> Textboxes = driver.findElements(By.xpath(Xpath));
	for (int i = 0; i < Textboxes.size(); i++)
	{
		if (output == null) 
		{
			output = (Textboxes.get(i).getText());
		}
		else
		{
			output =output+"/"+(Textboxes.get(i).getText());
		}
		
	}
	return output;
		
}
	
		
	public static WebDriver BrowserOption(String ApplicationLocation) throws IOException {

		System.setProperty("webdriver.gecko.driver", ApplicationLocation + "\\geckodriver.exe");
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxBinary firefoxBinary = new FirefoxBinary();
		//firefoxBinary.addCommandLineOptions("--headless");


		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setBinary(firefoxBinary).setProfile(profile);
		driver = new FirefoxDriver(firefoxOptions);
		return driver;
	}
	
	public static String Checkboxfunction(WebDriver driver, String Xpath) throws InterruptedException{
		String output=null;
		List<WebElement> Check = driver.findElements(By.xpath(Xpath));
			for (int j = 0; j < Check.size(); j++) {
				if(Check.get(j).isSelected())
				{
					output=(Check.get(j).getAttribute("checked"));
				}
				else{
					output="Not checked";
				}
			}
		return output;
		
		
	} 
	
	
	
	
	/*public static String Checkboxfunction(WebDriver driver, String Xpath) throws InterruptedException{
		String output=null;
		
		List<WebElement>  elements =  driver.findElements(By.xpath(Xpath));

		for( WebElement element: elements){
			
			element.getAttribute("innerHTML");
			System.out.println(element);
		}
		
		
		/*if(Check.isDisplayed())
		{
			output="Checked";
			
		}
		else {
			output = "Not checked";
		}
				
		return output;
		
		
	}*/
	public static String RadioButtonFunction(WebDriver driver, String Xpath) throws InterruptedException{
		String output = null;
		List<WebElement> radiobtns = driver.findElements(By.xpath(Xpath));
		for (int i = 0; i < radiobtns.size(); i++) {
				if(radiobtns.get(i).isSelected()){
						//System.out.println("----------------------------------");
					//if(output == null){
						output=(radiobtns.get(i).getAttribute("value"))+" / "+ (radiobtns.get(i).getAttribute("checked"));
					//}else{
						//output = output + "/" +(radiobtns.get(i).getAttribute("checked"));
					//}
					
				}
			
		}
		return output;
	}
		
	

	
	public static void ChangeSetting(WebDriver driver) throws InterruptedException {
		 //new Select(driver.findElement(By.xpath("//select[@id='location']"))).selectByVisibleText("Mitsubishi Fuso Truck and Bus Corporation, Kawasaki-shi");
		 Uncheck(driver, "//input[@name='column_internal_id']");
		 Uncheck(driver, "//input[@name='column_external_reference']");
		 Check(driver, "//input[@name='column_last_change']");
		 Check(driver, "//input[@name='column_last_login']");
		 new Select(driver.findElement(By.xpath("//select[@name='column_apis_id']"))).selectByVisibleText("5");
	 }
	 
	 public static void WaitOnCSVFile(WebDriver driver) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
		Date date = new Date();

		String Xpath= "//span[contains(text(), '"+dateFormat.format(date)+"')]";
		
		List<WebElement> elems = driver.findElements(By.xpath(Xpath));
		while (elems.size() == 0) {
			TimeUnit.SECONDS.sleep(10);
			GoToPath(driver,"//input[@name='btnRefresh']");
			elems = driver.findElements(By.xpath(Xpath));
		}
		driver.findElement(By.xpath(Xpath)).click();
	 }
	 
	 
	 public static void waitforlogin(WebDriver driver, String baseUrl) {
		    WebDriverWait wait = new WebDriverWait(driver, 30);
		    wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(baseUrl)));
		    String url =  driver.getCurrentUrl();
		    System.out.println(url);
	 }
	 
	 public static void GoToPath(WebDriver driver, String Xpath) throws InterruptedException {
		 WaitFor(driver, Xpath);
		 driver.findElement(By.xpath(Xpath)).click();		  
	 }
	 
	 public static boolean WaitFor(WebDriver driver, String Xpath)  {
		 try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			 wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Xpath))); 
			 return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			e.getMessage();
			//System.out.println("OOP!!! the Xpath is not correct!!");
			return false;
		}
	 }
	 
	 public static void Uncheck(WebDriver driver, String Xpath) throws InterruptedException {
		 System.out.println(Xpath);
		 if (driver.findElement(By.xpath(Xpath)).isSelected() )
		 {
		     driver.findElement(By.xpath(Xpath + "/following-sibling::i")).click();
		 }
	 }
	 
	 public static void Check(WebDriver driver, String Xpath) throws InterruptedException {
		 System.out.println(Xpath);
		 if ( !driver.findElement(By.xpath(Xpath)).isSelected() )
		 {
		      driver.findElement(By.xpath(Xpath + "/following-sibling::i")).click();
		 }
	 }
	 
	 public static String getJarPath() {
			String path = BrowserOpen.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String pathName = "";

			try {
				pathName = URLDecoder.decode(path, "UTF-8");
				pathName = pathName.substring(1, pathName.lastIndexOf("/"));
				pathName = pathName.replaceAll("/", "\\\\");
				System.out.println(pathName);
				pathName = "D:\\reportingTool\\bin";

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pathName;
		}
	 

}
