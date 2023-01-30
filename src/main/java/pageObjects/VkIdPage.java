package pageObjects;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class VkIdPage extends Form {

    private final ITextBox txbPassword = getElementFactory().getTextBox(By.xpath("//input[@autocomplete = 'current-password']")
            , "Textbox password");
    private final IButton btnContinue = getElementFactory().getButton(By.xpath("//span[contains(@class, 'vkuiButton') and contains(@class, 'in')]")
            , "Button continue");

    public VkIdPage() {
        super(By.className("vkuiAppRoot"), "Login Page");
    }

    public void enterPassword(String password) {
        txbPassword.clearAndType(password);
    }

    public void clickButtonContinue() {
        btnContinue.click();
    }
}