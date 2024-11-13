package be.pxl.venues.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends BasePage {

	private final WebElement title;
	private final WebElement venueDropDown;
	private final WebElement calculateButton;
	private final WebElement numberOfGuestsField;
	private final WebElement messageField;
	private final WebElement radioButtonBasic;
	private final WebElement radioButtonPremium;

	public MainPage(AppiumDriver driver) {
		super(driver);
		title = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").instance(0)"));
		venueDropDown = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(0)"));
		calculateButton = driver.findElement(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.widget.Button"));
		numberOfGuestsField = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)"));
		messageField = driver.findElement(AppiumBy.accessibilityId("text_result"));
		radioButtonBasic = driver.findElement(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.widget.RadioButton[1]"));
		radioButtonPremium = driver.findElement(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.widget.RadioButton[2]"));
	}

	public List<String> getVenueItemsLabels() {
		List<WebElement> venueItems = getDriver().findElements(AppiumBy.xpath("//android.widget.ScrollView//android.widget.TextView"));
		return venueItems.stream().map(WebElement::getText).toList();
	}

	public String getTitle() {
		return title.getText();
	}

	public void clickVenueDropDown() {
		venueDropDown.click();
	}

	public void clickCalculate() {
		calculateButton.click();
	}

	public void fillNumberOfGuests(int numberOfGuests) {
		numberOfGuestsField.click();
		numberOfGuestsField.sendKeys(String.valueOf(numberOfGuests));
	}

	public String getMessage() {
		return messageField.getText();
	}

	public void selectPremiumLevelOfService() {
		radioButtonPremium.click();
	}

	public void selectBasicLevelOfService() {
		radioButtonBasic.click();
	}

	public void selectVenueOption(String venue) {
		List<WebElement> venueItems = getDriver().findElements(AppiumBy.xpath("//android.widget.ScrollView//android.widget.TextView"));
		for (int i = 0; i < venueItems.size(); i++) {
			try {
				// Refetch the list item in each iteration to avoid stale references
				WebElement venueItem = getDriver().findElements(AppiumBy.xpath("//android.widget.ScrollView//android.widget.TextView")).get(i);
				if (venueItem.getText().equals(venue)) {
					venueItem.click();
					break; // Exit the loop once the desired item is clicked
				}
			} catch (StaleElementReferenceException e) {
				System.out.println("StaleReferenceException caught, refetching element...");
				// Refetch venueItems and continue the loop
				venueItems = getDriver().findElements(AppiumBy.xpath("//android.widget.ScrollView//android.widget.TextView"));
				i--; // Decrement the counter to retry the current index
			}
		}
	}
}
