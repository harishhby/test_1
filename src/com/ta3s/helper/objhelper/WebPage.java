package com.ta3s.helper.objhelper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ta3s.baseclass.BaseUtil;


public class WebPage extends BaseUtil {
	public static WebDriver driver;

	/**
	 * type method enters values into text fields
	 * @param key This describes the name of locator
	 * @param value This describes the value of which we need to pass
	 * @author Hareesh HB
	 */
	
	public void type(String key, String value) throws IOException {
		try {
			if (readObject(key).isEnabled()) {
				readObject(key).clear();
				readObject(key).sendKeys(value);

			}
		} catch (Exception e) {
			takeScreenShot("Error_" + key + ".jpeg");
			System.out.println("Typing failed on the key " + key);
		}
	}
	
	/**
	 * clear method clears values from text fields
	 * @param key This describes the name of locator
	 * @author Hareesh HB
	 */

	public void clear(String key) throws IOException {
		try {
			readObject(key).clear();
		} catch (Exception e) {
			takeScreenShot("Error_" + key + ".jpeg");
			System.out.println("Typing failed on the key " + key);
		}
	}
	
	/**
	 * click method clicks on WebElement
	 * @param key This describes the name of locator
	 * @author Hareesh HB
	 */
	public void click(String key) throws IOException {
		try{
			if (readObject(key).isEnabled()) {
				readObject(key).click();
			}
		}
		catch(Exception e){
			takeScreenShot("Error_" + key + ".jpeg");
			System.out.println("clicking failed on the key " + key);
		}
	}
	
	/**
	 * selectByVisibleText method selects values from drop down by visible text
	 * @param key This describes the name of locator
	 * @param key This describes visible text
	 * @author Hareesh HB
	 */

	public void selectByVisibleText(String key, String value)
			throws IOException {
		Select select = new Select(readObject(key));
		select.selectByVisibleText(value);
	}
	
	/**
	 * selectByValue method selects values from drop down by visible text
	 * @param key This describes the name of locator
	 * @param key This describes value to be selected
	 * @author Hareesh HB
	 */

	public void selectByValue(String key, String value) throws IOException {
		Select select = new Select(readObject(key));
		select.selectByValue(value);
	}

	
	/**
	 * getText method get the text of WebElement
	 * @param key This describes the name of locator
	 * @author Hareesh HB
	 */
	public String getText(String key) {
		try {
			// System.out.println(value);
			String str = readObject(key).getText();
			System.out.println("The text is " + str);
			return readObject(key).getText();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Get text failed on the key " + key);
		}
		return null;
	}
	
	/**
	 * getAttribute method get the value for respective attribute
	 * @param key This describes the name of locator
	 * @author Hareesh HB
	 */

	public String getAttribute(String key) {
		try {
			return readObject(key).getAttribute("value");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Get Attribute failed on the key " + key);
		}
		return null;
	}
	
	/**
	 * keyTab method performs keyboard tab operation
	 * @author Hareesh HB
	 */

	public void keyTab() throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
	}
	
	/**
	 * acceptAlert method accepts the alert
	 * @author Hareesh HB
	 */

	public void acceptAlert() {
		Alert alt = null;
		try {
			alt = driver.switchTo().alert();
		} catch (Exception e) {
			alt.accept();
		}

	}

	/**
	 * verifyIsElementdisplayed method checks presence of WebElement on WebPage
	 * @param key This describes the name of locator
	 * @author Hareesh HB
	 */
	public boolean verifyIsElementdisplayed(String key) throws IOException {
		boolean flag = false;
		try {
			if (readObject(key).isDisplayed()) {
				flag = true;
				System.out.println(key +" is displayed");
			}
		} catch (Exception e) {
			System.out.println(key+" is not displayed ");
		}
		return flag;
	}

}
