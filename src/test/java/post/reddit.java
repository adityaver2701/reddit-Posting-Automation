package post;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Aditya Verma
 *
 */

public class reddit {

    public String PlanTechSpecFile = "\\src\\test\\resources\\Colleges Reddit Accounts.xlsx";
    String value = null;

    public void Demo(String url) throws InterruptedException {
        Map < String, Object > prefs = new HashMap < String, Object > ();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
        // for (int c1 = 0; c1 <= row2.getLastCellNum() - 1; c1++) {
        driver.get(url);
        Thread.sleep(10);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//header[@data-redditstyle='true']//a[@role='button'][text()='Log In']")));
        driver.findElement(By.xpath("//header[@data-redditstyle='true']//a[@role='button'][text()='Log In']")).click();
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("#loginUsername")).sendKeys("sahaja-yogi");
        driver.findElement(By.cssSelector("#loginPassword")).sendKeys("Whatever@123");
        driver.findElement(By.xpath("//button[@class='AnimatedForm__submitButton m-full-width']")).click();
        Thread.sleep(500);
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='createPost']")));
        driver.findElement(By.xpath("//input[@name='createPost']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@placeholder='Title']")));
        String flairtxt = driver.findElement(By.xpath("//div[text()='Flair']/../..")).getAttribute("aria-label");
        System.out.println("Flair text is :"+flairtxt);
        if (flairtxt.equalsIgnoreCase("Add flair")) {
			System.out.println("Do Flair");
			driver.findElement(By.xpath("//div[text()='Flair']/../..")).click();
			driver.findElement(By.xpath("//div[@aria-label='flair_picker']/div[2]")).click();
			driver.findElement(By.xpath("(//div[@role='dialog']/div//button)[2]")).click();
		} else {
			System.out.println("Do Nothing");
		}
        driver.findElement(By.xpath("//textarea[@placeholder='Title']")).sendKeys("Some title related to sahaja yoga");
        driver.findElement(By.xpath("//div[@class='notranslate public-DraftEditor-content']")).sendKeys(
            "https://www.eventbrite.com/e/easy-meditation-for-students-online-meditate-new-york-tickets-202799939077");
        Thread.sleep(5000);
        driver.close();
        driver.quit();
    }

    public String fetchValueFromExcel(String file, int vsheet, int vRow, int vColumn) throws IOException {
        FileInputStream inputStream = null;
        inputStream = new FileInputStream(System.getProperty("user.dir") + file);
        XSSFWorkbook specfileWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = specfileWorkbook.getSheetAt(vsheet);
        String value; // variable for storing the cell value
        Row row = sheet.getRow(vRow); // returns the logical row
        Cell cell = row.getCell(vColumn); // getting the cell representing the given column
        value = cell.getStringCellValue();
        return value;
    }

    @Test
    public void iterate() throws InterruptedException, IOException {
        for (int i = 1; i < 47; i++) {
            value = fetchValueFromExcel(PlanTechSpecFile, 0, i, 2);
            Demo(value);
            System.out.println(value);

        }
    }

}