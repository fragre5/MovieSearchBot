package org.example.kpfinder;

import lombok.extern.slf4j.Slf4j;
import org.example.kpfinder.utils.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Finder {

    public static List<String> getMovieURL(String filmName) {

        List<String> searchResults = new ArrayList<>();

        System.setProperty(Constants.driverName, Constants.driverPath);

        ChromeOptions options = new ChromeOptions();

        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        WebDriver driver = new ChromeDriver(options);

        driver.get(Constants.baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(600));

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
                    log.error("Элемент с локатором {" + href + "} не был найден в течении 0.4 секунд!");
                }

            }

        }

        WebElement aTag = received.findElement(By.tagName("a"));

        String currentUrlOfMovie = aTag.getAttribute("href");

        currentUrlOfMovie = currentUrlOfMovie.replace(org.example.bot.commands.Constants.origin_ru_tld, org.example.bot.commands.Constants.pathfinder_common_tld);

        searchResults.add(currentUrlOfMovie);

        log.info(currentUrlOfMovie);

        List<WebElement> additionalLinks = driver.findElements(By.xpath("//a[@jsname='ZWuC2']"));

        for (WebElement additionalLink : additionalLinks) {

            String nameOfAdditionalMovies = additionalLink.getAttribute("aria-label");

            searchResults.add(nameOfAdditionalMovies);
        }

        driver.quit();

        return searchResults;
    }

}
