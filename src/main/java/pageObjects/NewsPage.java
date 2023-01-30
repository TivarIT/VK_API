package pageObjects;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewsPage extends Form {
    private final ITextBox txbMyPage = getElementFactory().getTextBox(By.id("l_pr"), "My page button");

    public NewsPage() {
        super(By.className("side_bar_nav"), "Navigation bar");
    }

    public void clickMyPageBtn(){
        txbMyPage.state().waitForClickable();
        txbMyPage.click();
    }
}
