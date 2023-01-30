package pageObjects;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginPage extends Form {
    private final ITextBox txbLogin = getElementFactory().getTextBox(By.id("index_email"), "Textbox login");
    private final IButton btnSignIn = getElementFactory().getButton(By.xpath(
            "//button[contains(@class, 'VkIdForm') and contains(@class, 'signInButton')]"), "Button sign in");

    public LoginPage(){
        super(By.xpath("//form[contains(@class, 'VkIdForm')]"), "Login Page");
    }

    public void enterLogin(String login){
        txbLogin.clearAndType(login);
    }

    public void clickSignInButton(){
        btnSignIn.click();
    }
}
