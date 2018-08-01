package LatestWork;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoToWebPath {

	public GoToWebPath(String PreviousPath, String CurrentPath, WebDriver driver){

	}
	
	public static String[] SplitArray(String URL){
		String[] str1 = URL.split("/");
		return str1;
	}
	
	public static int Compare(String[] FirstPath, String[] SecondPath){
	
		int index=0;
		int lenght; 
		
		if (FirstPath.length <= SecondPath.length){
			lenght = FirstPath.length; 
		}else{
			lenght= SecondPath.length; 
		}
			
		for(int x=0 ; x<lenght ; x++){
			if (FirstPath[x].equals(SecondPath[x])){
				System.out.println("Same Element [" +x+" ]:"+ FirstPath[x]);
				index++;
			}	
		}
		return index;
		}
	
	public static void goBack(String [] PathFirst, int index, WebDriver driver) throws InterruptedException
	{
		for( int i=PathFirst.length;i>index;i--)
		{
			driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/main/div/div/div[3]/div[2]/ul/li/a[1]")).click();
			Thread.sleep(100);
			//(i);
		}
	}
	public static void goForward(String []PathSecond, int index, WebDriver driver) throws InterruptedException
	{
		for(int i=index; i<PathSecond.length ; i++)
		{
		
			driver.findElement(By.xpath("//*[text()='" + PathSecond[i] + "']")).click();
			Thread.sleep(1000);
			//Do web scraping;
			
		}
		
	}

	private static void WaitFor(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public static void GoToWebPath(String previousPath, String currentPath, WebDriver driver) throws InterruptedException 
	{
		
		String [] FirstPath= SplitArray(previousPath);
		String [] SecondPath = SplitArray(currentPath);
		
		if ( previousPath==""){
			goForward(SecondPath,0, driver);
			
		}else{
			int index = Compare(FirstPath, SecondPath);
			goBack(FirstPath,index, driver);
			goForward(SecondPath,index, driver);
		}
		
		
		
		
	}
}
