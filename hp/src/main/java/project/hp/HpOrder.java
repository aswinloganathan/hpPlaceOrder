package project.hp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HpOrder {
    public static void main( String[] args ) throws InterruptedException{
    	 
    	System.setProperty("webdriver.chrome.driver","./drivers/chromedriver1.exe");
    	System.setProperty("webdriver.chrome.silentOutput","true");
    	WebDriver driver = new ChromeDriver();
    	driver.manage().window().maximize();
    	
    	
    	// 1) Go to https://store.hp.com/in-en/ 
    	driver.get("https://store.hp.com/in-en/default");
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	
    	
    	// 2) Mouse over on Laptops menu and click on Pavilion
    	Actions action = new Actions(driver);
    	action.moveToElement(driver.findElement(By.xpath("//span[text()='Laptops']"))).perform();
    	action.moveToElement(driver.findElement(By.xpath("//span[text()='Pavilion']"))).click().perform();
    	
    	
    	WebDriverWait wait = new WebDriverWait(driver,30);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inside_visitorNotify']/div"))).click();
    	
    	// 3) Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
    	driver.findElement(By.xpath("//div[contains(@class,'filter-options-content')]/dl/dt/span[text()='Processor']")).click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//span[contains(text(),'Intel Core i7')]")).click();
    	Thread.sleep(3000);
    	
    	// 4) Hard Drive Capacity -->More than 1TB
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("window.scrollBy(0,800)");
    	driver.findElement(By.xpath("//span[text()='More than 1 TB']")).click();
    	Thread.sleep(3000);
    	
    	// 5) Select Sort By: Price: Low to High
    	WebElement sort = driver.findElement(By.id("sorter"));
    	Select options = new Select(sort);
    	options.selectByValue("price_asc");
    	
    	// 6) Print the First resulting Product Name and Price
    	String productName = driver.findElement(By.xpath("(//div[@class='product-item-info'])[1]//a[@class='product-item-link']")).getText();
    	String productPrice = driver.findElement(By.xpath("(//div[@class='price-box price-final_price'])[1]//span[@class='price']")).getText();
    	String sortedPrice = productPrice.replaceAll("\\D", "");
    	int lapPrice = Integer.parseInt(sortedPrice);
    	
    	
    	System.out.println("Product name is : "+productName+" and its price is : "+ lapPrice);
    	
    	driver.findElement(By.xpath("//div[@class='optanon-alert-box-corner-close']/button")).click(); // Close cookies alert
    	Thread.sleep(3000);
    	// 7) Click on Add to Cart
    	driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    	Thread.sleep(3000);
    	
    	//8) Click on Shopping Cart icon --> Click on View and Edit Cart
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'action showcart')]"))).click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//a[contains(@class,'action primary viewcart')]")).click();
    	
    	//9) Check the Shipping Option --> Check availability at Pincode
    	WebElement delOption = driver.findElement(By.id("standard_delivery"));
    	boolean selected = driver.findElement(By.id("standard_delivery")).isSelected();
    	if (selected == true) {
			System.out.println("Shipping options is selected");
		}else {
			action.click(delOption).perform();
		}
    	
    	WebElement pincode = driver.findElement(By.xpath("//input[@name='pincode']"));
    	pincode.click();
    	pincode.sendKeys("600062");
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//input[@name='pincode']//following-sibling::button")).click();
    	
	    //10) Verify the order Total against the product price
    	String totalPrice = driver.findElement(By.xpath("//tr[contains(@class,'grand totals')]/td//span")).getText();
    	String sortedPrice2 = totalPrice.replaceAll("\\D", "");
    	int price= Integer.parseInt(sortedPrice2);
    	System.out.println("Grand total price is :"+price);
    	
    	//11) Proceed to Checkout if Order Total and Product Price matches
    	if (price == lapPrice) {
			System.out.println("Price of the laptop is matched");
			Thread.sleep(3000);
			driver.findElement(By.xpath("(//button[@id='sendIsCAC'])[1]")).click();
    	} else {
			System.out.println("Price of the laptop is not maching");
		}
    	Thread.sleep(3000);
    	
    	
    	//12) Click on Place Order
    	driver.findElement(By.xpath("//div[@class='place-order-primary']/button")).click();
    	Thread.sleep(3000);
    	
    	//13) Capture the Error message and Print
    	String errorOutput = driver.findElement(By.xpath("//div[contains(@class,'message notice')]/span")).getText();
    	System.out.println(errorOutput);
    	driver.close();
    }
}
