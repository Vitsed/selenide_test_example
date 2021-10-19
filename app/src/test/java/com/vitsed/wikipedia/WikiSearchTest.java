package com.vitsed.wikipedia;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.vitsed.wikipedia.pages.html_elements.TableHelper;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;

public class WikiSearchTest {

    private final String WIKI_MAIN_PAGE = "https://ru.wikipedia.org";

    private List<SelenideElement> headers = new ArrayList<>();
    private List<ElementsCollection> data = new ArrayList<>();
    private List<SelenideElement> rows = new ArrayList<>();
    private List<ElementsCollection> records = new ArrayList<>();
    private final String tableLocator = "table[class='wikitable sortable jquery-tablesorter']";
    private final int tableIndex = 0;
    private final TableHelper tableHelper= new TableHelper();

    @Test
    public void searchInfo() {
        open(WIKI_MAIN_PAGE);

        $("input[name='search']")
                .setValue("Самарский метрополитен")
                .should(Condition.appear)
                .pressEnter();

        var tables = $$("table[class='wikitable sortable jquery-tablesorter']").shouldHave(size(3));

        // получаем таблицу
//        var table1 = $("table[class='wikitable sortable jquery-tablesorter']", 0);

        var table1 = tableHelper.getTable(tableLocator, 0);
        // получаем записи
        var r = tableHelper.getRecords(table1);
        System.out.println("Records: " + tableHelper.getRecords(table1).size());

        // получаем заголовки
        var headers = tableHelper.getHeaders(table1);
        this.headers.addAll(headers);

        // отображаем заголовки, удаляя разделитель в виде новой строки
        for (var record : this.headers) {
//            System.out.println(record + "<- This is a record \t");
            String text = record.getText();
            System.out.print(text.replace("\n"," ") + "\t");
        }
        System.out.println();

        // отображаем записи
        for (int i = 0; i < rows.size(); i++) {
            var record = rows.get(i).$$(By.tagName("td"));
            records.add(record);
//            System.out.println(record + "<- This is a record \t");
        }
        drawSplitLane();
        List<String> list = tableHelper.getDataAsList(r.get(1));
        list.forEach(System.out::println);
        drawSplitLane();

        System.out.printf("Headers in the table %d%n", headers.size());
        System.out.printf("Rows in the table %d%n", rows.size());
        drawSplitLane();

        printCollection(data);
        System.out.println();

        drawSplitLane();

        sleep(8000);

        drawSplitLane();

        System.out.println("Вывожу данные в столбце");

        String text = tableHelper.getValueOfCell(3, "Дата");
        System.out.println(text);

        SelenideElement link = tableHelper.getLinkOfCell(1, "участок");
        System.out.println(link);

    }

    @Test
    public void table() {
        drawTable();
    }

    private static void drawTable() {
        int a = 25;
        String leftAlignFormat = "| %-" + a + "s | %-4d |%n";

        System.out.format("+" + drawLine(a) + "+------+%n");
        System.out.format(leftAlignFormat, "some data", 12);
        System.out.format("+"+drawLine(a)+"+------+%n");
        for (int i = 0; i < 5; i++) {
            System.out.format(leftAlignFormat, "some data" + i, i * i);
        }
        System.out.format("+-----------------+------+%n");
    }

    private static String drawLine(int width) {
        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat(Math.max(0, width - 2)));
        return sb.toString();
    }

    private static void drawSplitLane() {
        System.out.println("*********************************************************");
    }

    private static void printCollection(List<ElementsCollection> list) {

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%s", list.get(i));
        }
        System.out.println();
    }

    private static void printDataAtTable(List<ElementsCollection> list) {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).forEach(el -> System.out.printf("%s\t", el.getText()));
            System.out.println();
        }
    }

}
