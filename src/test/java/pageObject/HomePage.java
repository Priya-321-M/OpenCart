package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	
	@FindBy(xpath="//span[@class='hidden-xs hidden-sm hidden-md' and text()=\"My Account\"]") WebElement myAccount;
	@FindBy(xpath="//a[normalize-space()='Register']") WebElement register;
	@FindBy(xpath="//i[@class='fa fa-home']") WebElement homeIcon;
	
	public void clickMyaccount() {
		myAccount.click();
		}
 
	public void clickRegister() {
		register.click();
		}
	
	public void clickHomeicon() {
		homeIcon.click();
		}
}
