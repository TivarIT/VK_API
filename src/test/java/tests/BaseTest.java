package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected Logger log = AqualityServices.getLogger();
    protected static final ISettingsFile CONFIG = new JsonSettingsFile("configData.json");
    protected static final String BASE_URL = CONFIG.getValue("/baseURL").toString();

    @BeforeMethod
    protected void beforeMethod() {
        log.info(String.format("Go to the %s", BASE_URL));
        AqualityServices.getBrowser().goTo(BASE_URL);
        AqualityServices.getBrowser().maximize();
        AqualityServices.getBrowser().waitForPageToLoad();
    }

    @AfterMethod
    public void afterTest() {
        AqualityServices.getBrowser().quit();
    }
}
