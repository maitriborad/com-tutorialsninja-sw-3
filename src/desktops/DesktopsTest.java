package desktops;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DesktopsTest extends Utility {
    String baseURL="http://tutorialsninja.com/demo/index.php?";
    @Before
    public void setUp(){
        openBrowser(baseURL);
    }
    @Test
    public void verifyProductArrangeInAlphabeticalOrder() throws InterruptedException {
        //Mouse hover on Desktops Tab.and click
        mouseHover(By.xpath("//div[@class='collapse navbar-collapse navbar-ex1-collapse']/ul/li[1]"));
        //Click on “Show All Desktops”
        clickOnElement(By.linkText("Show AllDesktops"));
        //get list
        List<WebElement> list=driver.findElements(By.xpath("//div[@class='caption']//h4"));
        List<String> expectedList= new ArrayList<>();
        for(WebElement e:list){
            expectedList.add(e.getText());
        }
        Collections.reverse(expectedList);
        //Select Sort By position "Name: Z to A"
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='input-sort']"),"Name (Z - A)");
        //Verify the Product will arrange in Descending order.
        Thread.sleep(2000);
        List<WebElement> list1=driver.findElements(By.xpath("//div[@class='caption']//h4"));
        List<String> actualList= new ArrayList<>();
        for(WebElement e:list1){
            actualList.add(e.getText());
        }
        Assert.assertEquals("List are not sorted",expectedList,actualList);
    }
    @Test
    public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {
        Thread.sleep(1000);
//        2.1 Mouse hover on Desktops Tab. and click
        mouseClickOnElement(By.xpath("//div[@class='collapse navbar-collapse navbar-ex1-collapse']/ul/li[1]"));
//        2.2 Click on “Show All Desktops”
        clickOnElement(By.linkText("Show AllDesktops"));
        //Select Sort By position "Name: A to Z"
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='input-sort']"),"Name (A - Z)");
        //Select product “HP LP3065”
        clickOnElement(By.xpath("//a[normalize-space()='HP LP3065']"));
        //Verify the Text "HP LP3065"
        verifyThis("HP LP3065",By.xpath("//h1[normalize-space()='HP LP3065']"));
        //Select Delivery Date "2022-11-30"
        String year = "2022";
        String month = "November";
        String date = "30";
        clickOnElement(By.xpath("//body/div[@id='product-product']/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/span[1]/button[1]/i[1]"));

        while (true) {
            String monthYear = driver.findElement(By.xpath("//div[@class='datepicker']/div[1]/table/thead/tr[1]/th[2]")).getText();

            //Splitting year and month Nov 2022
            String arr[] = monthYear.split(" ");
            //Actual dates
            String mon = arr[0];
            String yer = arr[1];

            if (mon.equalsIgnoreCase(month) && yer.equalsIgnoreCase(year)) {
                break;
            } else {
                clickOnElement(By.xpath("//div[@class='datepicker']/div[1]/table/thead/tr[1]/th[3]"));
            }

        }
        //Select Date
        List<WebElement> allDates = driver.findElements(By.xpath("//div[@class='datepicker']//div//table//td"));
        for (WebElement dt : allDates) {
            if (dt.getText().equalsIgnoreCase(date)) {
                dt.click();
                break;
            }
        }
        //Enter Qty "1” using Select class.
        driver.findElement(By.xpath("//input[@id='input-quantity']")).sendKeys(Keys.BACK_SPACE);
        sendTextToElement(By.xpath("//input[@id='input-quantity']"),"1");
        // Click on “Add to Cart” button
        clickOnElement(By.xpath("//button[@id='button-cart']"));
        //Verify the Message “Success: You have added HP LP3065 to your shopping cart!”
        Thread.sleep(1000);
        verifyThis("Success: You have added HP LP3065 to your shopping cart!\n" +
        "×",By.xpath("//body/div[@id='product-product']/div[1]"));
        //Click on link “shopping cart” display into success message
        clickOnElement(By.xpath("//a[contains(text(),'shopping cart')]"));
        //Verify the text "Shopping Cart"
        String actual=getTextFromElement(By.xpath("//h1[contains(text(),'Shopping Cart')]"));
        Assert.assertTrue("Noy displayed correctly",actual.contains("Shopping Cart"));
        //Verify the Product name "HP LP3065"
        verifyThis("HP LP3065",By.linkText("HP LP3065"));
//      Verify the Delivery Date "2022-11-30"
        verifyThis("Delivery Date:2022-11-30",By.xpath("//small[normalize-space()='Delivery Date:2022-11-30']"));
//      Verify the Model "Product21"
        verifyThis("Product 21",By.xpath("//td[normalize-space()='Product 21']"));
//      Verify the Total "£74.73" convert to pounds from dollars
        clickOnElement(By.xpath("//span[normalize-space()='Currency']"));
        Thread.sleep(1000);
        clickOnElement(By.xpath("(//button[@class='currency-select btn btn-link btn-block'])[2]"));
        //verify total £74.73
        verifyThis("£74.73",By.xpath("//tbody//tr//td[6]"));
    }
    @After
    public void tearDown(){
        //closeBrowser();
    }
}
