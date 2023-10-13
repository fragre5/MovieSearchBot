package org.example.kpfinder;

import lombok.extern.slf4j.Slf4j;
import org.example.kpfinder.utils.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

@Slf4j
public class Finder {

    public static Optional<String> getMovieURL(String filmName) {

        System.setProperty(Constants.driverName, Constants.driverPath);

        ChromeOptions options = new ChromeOptions();

        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        WebDriver driver = new ChromeDriver(options);

        driver.get(Constants.baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(400));

        WebElement findFilm = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"APjFqb\"]")));

        findFilm.sendKeys(filmName);

        findFilm.sendKeys(Keys.ENTER);

        WebElement received = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"kp-wp-tab-overview\"]/div[2]")));

        if (!received.getText().contains("kinopoisk")) {

            for (String href : Constants.hrefs) {
                try {
                    received = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(href)));
                    if (received.getText().contains("kinopoisk")) {
                        log.info(href);
                        break;
                    }
                } catch (TimeoutException e) {
                    log.error("Элемент с локатором {" + href + "} не был найден в течении 10 секунд!");
                }

            }

        }

        WebElement aTag = received.findElement(By.tagName("a"));

        String currentUrlOfMovie = aTag.getAttribute("href");

        log.info(currentUrlOfMovie);

        driver.quit();

        return currentUrlOfMovie.describeConstable();
    }

}
