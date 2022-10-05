package StepDefinitions;


import Utilities.ExcelUtility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import Utilities.GWD;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    @Before
    public void before()
    {
        System.out.println("Senaryo Başladı...");
    }

    @After
    public void after(Scenario scenario)
    {
        System.out.println("Senaryo Bitti...");
        System.out.println("scenario sonucu="+ scenario.getStatus());
        System.out.println("scenario isFailed ?="+ scenario.isFailed());

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        //excele sonuçlarını yazacağız. Senerio adı, browserTipi, Zaman
        ExcelUtility.writeExcel("src/test/java/ApachePOI/resource/ScenarioStatu.xlsx", scenario,
                GWD.threadBrowserName.get(), date.format(formatter) );


        if (scenario.isFailed()){
            // klasöre
            TakesScreenshot screenshot = (TakesScreenshot) GWD.getDriver();
            File ekranDosyasi = screenshot.getScreenshotAs(OutputType.FILE);

            //Extend reporta ekliyor
            //ExtentTestManager.getTest().addScreenCaptureFromBase64String(getBase64Screenshot());

            try {
                FileUtils.copyFile(ekranDosyasi,
                        new File("target/FailedScreenShots/"+ scenario.getId()+date.format(formatter)+".png"));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // ekran görüntüsü al senaryo hatalı ise
        GWD.quitDriver();
    }

    public String getBase64Screenshot()
    {
        return ((TakesScreenshot) GWD.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

}




//package StepDefinitions;
//
//import Utilities.GWD;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import org.openqa.selenium.TakesScreenshot;
//
//public class Hooks {
//
//    @Before
//    public void before(){
//        System.out.println("Seneryo başladı...");
//    }
//
//    @After
//    public void after(){
//            //ekran görüntüsü al seneryo hatalıysa
//        GWD.quitDriver();
//        System.out.println("Seneryo bitti...");
//    }
//
//}
