package be.pxl.venues.tests;

import be.pxl.venues.pages.MainPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculateVenueCostTest {

	private static final String APPIUM_SERVER_URL = System.getProperty("appium.url");
	private static final String EMULATOR_NAME = System.getProperty("emulator.name");
	private AndroidDriver driver;

	@BeforeAll
	public void setup() throws IOException, InterruptedException {
		UiAutomator2Options options = new UiAutomator2Options();
		options.setPlatformName("Android");
		options.setDeviceName(EMULATOR_NAME);
		options.setApp("/Users/nelec/apps/venuecalculator-app-debug.apk");

		// change the url of the appium server.
		driver = new AndroidDriver(URI.create(APPIUM_SERVER_URL).toURL(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterAll
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	// TODO create a parameterized test
	public void calculateCost() {
		MainPage mainPage = new MainPage(driver);
		mainPage.clickVenueDropDown();
		mainPage.selectVenueOption("City Hall");
		mainPage.fillNumberOfGuests(150);
		mainPage.selectPremiumLevelOfService();
		mainPage.clickCalculate();
		String cost = mainPage.getMessage();
		String expectedCost = "Total Cost: 25000 €\nUpfront Payment: 0 €";
		assertEquals(expectedCost, cost);
	}

	// TODO create a new test where the number of guests outnumber the capacity of the venue

	// TODO create a new test where the user pays 5% upfront

	// TODO create a new test where the user pays 10% or more upfront
}
