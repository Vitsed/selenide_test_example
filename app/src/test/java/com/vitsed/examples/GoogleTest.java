package com.vitsed.examples;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.codeborne.selenide.Condition.name;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.exist;

public class GoogleTest {

    @Test
    public void pureSelenium() {
        ChromeDriver driver = new ChromeDriver();
        driver.navigate().to("https://duckduckgo.com");
        WebElement query = driver.findElement(By.name("q"));
        query.sendKeys("selenide heisenbug");
    }


    @Test
    public void userCanSearchKeyword() {
        List<SelenideElement> elements = new ArrayList();
        List<String> text = new ArrayList<>();
        Map<String, String> titlesAndPrice = new TreeMap<>();
        open("https://ecwid.ru/demo/search");

        sleep(6000);


        elements.addAll($("div.ecwid-productBrowser")
                .should(exist)
                .findAll("div.grid-product__wrap"));

        System.out.println(elements.size());
//        System.out.println(elements);
        sleep(2000);


        for (SelenideElement element : elements) {
            String title, price;
            String[] e = element.getText().split("\n");
            title = e[0];
            price = e[1];
            titlesAndPrice.put(title, price);
        }

//        titlesAndPrice.forEach((title, price) -> {
//            System.out.println("Title: " + title + " with price " + price);
//        });

        //get filters
        List<SelenideElement> filtres = $(".ec-filters__body")
                .should(exist)
                .findAll("input.form-control__text");

        filtres.get(0).should(exist).setValue("surfboard").pressEnter();

        elements.addAll($("grid__products").findAll("grid-product__shadow"));
//        filtres.get(1).sendKeys("150");

        sleep(2000);

        for(SelenideElement se : elements) {
            if(!(se.getText().contains("Surfboard"))) {
                Assert.fail("Search filter works incorrect");
            }
        }



    }
}
