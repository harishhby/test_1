package com.ta3s.baseclass;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ta3s.config.GetPath;
import com.ta3s.helper.objhelper.Xls_Reader;

import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;
import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;


public class BaseUtil {
	{
		try {
			getAtuReportPath();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}


	public void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("Automation Tester ", Utils.getCurrentTime(),"1.0");
	}

	public static Xls_Reader datatable;
	public static WebDriver driver;
	public static final int DEFAULT_TIMEOUT = 60;
	public static  ATUTestRecorder recorder;



	@BeforeSuite
	public void initiate() throws IOException, AWTException, ATUTestRecorderException {	

		String browser = readConfigFile("browser");
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					GetPath.getBasePath() + File.separator+"Drivers"
							+File.separator+ "geckodriver.exe");		
			driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("chrome")) {
			DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome ();       
					handlSSLErr.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
			
			System.setProperty("webdriver.chrome.driver",
					GetPath.getBasePath() + File.separator+"Drivers"
							+File.separator+ "chromedriver.exe");							
			driver = new ChromeDriver(handlSSLErr);			
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					GetPath.getBasePath() + File.separator
					+ "chromedriver.exe");
			driver = new InternetExplorerDriver();
		}

		driver.manage().window().maximize(); 
		driver.manage().timeouts() .implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get(readConfigFile("url"));
		
	}

	/*
	 * @BeforeSuite public void initiate() throws IOException, AWTException {
	 * 
	 * DesiredCapabilities capability = DesiredCapabilities.chrome();
	 * capability.setBrowserName("firefox");
	 * capability.setBrowserName("firefox"); //ip address and port of machine B.
	 * driver = new RemoteWebDriver(new
	 * URL("http://10.10.11.55:8080/wd/hub"),capability); driver = new
	 * RemoteWebDriver(new URL("http://10.10.11.51:8080/wd/hub"),capability);
	 * 
	 * driver.manage().window().maximize(); driver.manage().timeouts()
	 * .implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
	 * driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	 * driver.get(readConfigFile("url"));
	 * 
	 * }
	 */

	/*
	 * @Parameters("browser")
	 * 
	 * @BeforeClass public void initiate(String browser) throws IOException,
	 * AWTException {
	 * 
	 * if (browser.equalsIgnoreCase("firefox")) { driver = new FirefoxDriver();
	 * } else if (browser.equalsIgnoreCase("chrome")) { // Set Path for the
	 * executable file System.setProperty("webdriver.chrome.driver",
	 * GetPath.getBasePath() + File.separator+"chromedriver.exe");
	 * driver = new ChromeDriver(); }
	 * 
	 * driver.manage().window().maximize(); driver.manage().timeouts()
	 * .implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
	 * driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	 * driver.get("https://bstest.remscriptss.com/bsweb/");
	 * //driver.get(readConfigFile("url")); }
	 */

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws ATUTestRecorderException {
		driver.quit();
	}

	public WebElement readObject(String key) throws IOException {
		String strControllerPath = GetPath.getBasePath()
				+ File.separator +"ObjectRepo"+ File.separator +"OR.xlsx";		
		datatable = new Xls_Reader(strControllerPath);
		Object[][] data = datatable.getData("Elements");
		for (int row = 0; row < data.length; row++) {
			if (data[row][0].equals(key)) {
				String identifier = (String) data[row][1];
				String locator = (String) data[row][2];
				if (identifier.equalsIgnoreCase("xpath")) {
					return driver.findElement(By.xpath(locator));
				} else if (identifier.equalsIgnoreCase("id")) {
					return driver.findElement(By.id(locator));
				} else if (identifier.equalsIgnoreCase("name")) {
					return driver.findElement(By.name(locator));
				} else if (identifier.equalsIgnoreCase("cssSelector")) {
					return driver.findElement(By.cssSelector(locator));
				}
			}
		}
		return null;

	}

	public List<WebElement> readObjects(String key) throws IOException {
		String value[] = readORFile(key).split("");
		String identifier = value[0];
		String locator = value[1];
		if (identifier.equalsIgnoreCase("xpath")) {
			return driver.findElements(By.xpath(locator));
		} else if (identifier.equalsIgnoreCase("id")) {
			return driver.findElements(By.id(locator));
		} else if (identifier.equalsIgnoreCase("name")) {
			return driver.findElements(By.name(locator));
		}
		return null;
	}

	public String readConfigFile(String key) throws IOException {
		Properties p = new Properties();
		FileInputStream fs = new FileInputStream(GetPath.getBasePath()
				+ File.separator + "src" + File.separator + "com"
				+ File.separator + "ta3s" + File.separator + "config"
				+ File.separator + "config.properties");
		p.load(fs);
		return (String) p.get(key);
	}

	/*
	 * public static String readDBConfigFile(String key) throws IOException {
	 * Properties p = new Properties(); FileInputStream fs = new
	 * FileInputStream(GetPath.getBasePath() +
	 * File.separator+"src"+File
	 * .separator+"com"+File.separator+"Database"+File.separator
	 * +"db.properties"); p.load(fs); return (String) p.get(key); }
	 */

	public String readORFile(String key) throws IOException {
		Properties p = new Properties();
		FileInputStream fs = new FileInputStream(GetPath.getBasePath()	+File.separator	+"ObjectRepo"
				+ File.separator + "OR.xlsx");
		p.load(fs);
		return (String) p.get(key);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static void takeScreenShot(String fileName) throws IOException {
		File screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(GetPath.getBasePath()
				+ File.separator + "Error_ScreenShot" + File.separator
				+ fileName));
	}


	public static boolean loadController() {

		try {
			String strControllerPath = GetPath.getBasePath()
					+ File.separator +"ObjectRepo"+ File.separator +"OR.xlsx";
			if (isFilePresent(strControllerPath)) {
				datatable = new Xls_Reader(strControllerPath);
				Reporter.log("The Controller sheet is successfully loaded");
				return true;
			} else {
				Reporter.log("The Controller sheet loading is failed");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.log("Some Exception occured in loading the Configuration file");
			Assert.fail("Some Exception occured in loading the Configuration file");
			return false;
		}
	}

	public static boolean loadTescaseSheet() {

		try {
			String strControllerPath = GetPath.getBasePath()
					+ File.separator + "ControllerSheet.xlsx";
			if (isFilePresent(strControllerPath)) {
				datatable = new Xls_Reader(strControllerPath);
				Reporter.log("The Testcase Controller sheet is successfully loaded");
				return true;
			} else {
				Reporter.log("The Test case Controller sheet loading is failed");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.log("Some Exception occured in loading the Configuration file");
			Assert.fail("Some Exception occured in loading the Configuration file");
			return false;
		}
	}

	public static boolean isFilePresent(String strFilePath) {
		try {
			if ((strFilePath).trim() == "") {
				Reporter.log("The passed file path paramenter is blank");
				return false;
			} else {
				File file = new File(strFilePath);
				boolean exists = file.exists();
				if (exists) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}


	public void getTitle() {
		try {
			String getTitle = driver.getTitle();
			System.out.println("The title of the Page is - " + getTitle);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("The title of the page is not displayed");

		}

	}

	/*
	 * This method is used for ATU Report absolute path
	 */

	public void getAtuReportPath() throws IOException {
		try {
			String pathAtuReport = getAbsolutePath();
			System.setProperty("atu.reporter.config", pathAtuReport);
			//System.out.println(pathAtuReport+"  "+"pathAtuReport");
		} catch (IOException e) {

		}
	}

	/*
	 * Get absolute path of ATU Report.
	 */

	public String getAbsolutePath() throws IOException {

		String path = new File("src/atu.properties").getAbsolutePath();
//		System.out.println("Path "+path);
		return path;

	}

	
	public void checkvoid(String testCaseName) {
		if (Xls_Reader.isSkip(testCaseName)) {
			System.out.println("Test Case Skipped" + testCaseName);
			throw new SkipException("Skipped TC");
		}

	}
}
