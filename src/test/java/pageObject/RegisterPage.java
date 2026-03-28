package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement firstName;
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement lastName;
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement eMail;
	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement telephone;
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement password;
	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement passwordConfirm;
	@FindBy(xpath = "//input[@name='agree']")
	WebElement agree;
	@FindBy(xpath = "//input[@value='Continue']")
	WebElement Submit;
	public @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement successMsg;

	public void enterFirstname(String fname) {
		firstName.sendKeys(fname);
	}

	public void enterLastname(String lname) {
		lastName.sendKeys(lname);
	}

	public void enterEmail(String mail) {
		eMail.sendKeys(mail);
	}

	public void enterTelephnone(String telePhn) {
		telephone.sendKeys(telePhn);
	}

	public void enterPassword(String pwd) {
		password.sendKeys(pwd);
	}

	public void enterConfpwd(String pwd) {
		passwordConfirm.sendKeys(pwd);
	}

	public void clickAgreechk() {
		agree.click();
	}

	public void clickContinue() {
		Submit.click();
	}

	public String getConfMsg() {
		try {
			return (successMsg.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
