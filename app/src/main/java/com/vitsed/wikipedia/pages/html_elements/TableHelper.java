package com.vitsed.wikipedia.pages.html_elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class TableHelper {
    private int numberOfRows;
    private int numberOfColumns;
    private List<SelenideElement> records = new ArrayList<>();
    private List<SelenideElement> headers = new ArrayList<>();

    public SelenideElement getTable(String selector, int tableIndex) {
        return $(selector, tableIndex);
    }

    public List<SelenideElement> getHeaders(SelenideElement table) {
        List<SelenideElement> list = table.$$(By.tagName("th"));
        this.headers.addAll(list);
        numberOfColumns = list.size();
        return new ArrayList<>(list);
    }

    public List<SelenideElement> getRecords(SelenideElement table) {
        List<SelenideElement> list = table.$$(By.tagName("tr"));
        records.addAll(list);
        numberOfRows = list.size();
        return new ArrayList<>(list);
    }

    public List<String> getDataAsList(SelenideElement record) {
        List<String> list = new ArrayList<>();
        getStrings(record).forEach(el -> list.add(el.getText()));
        return list;
    }



    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public String getValueOfCell(int record, String header) {

        int indexOfHeader = findHeaderIndex(header);

        SelenideElement r = this.records.get(record);
        List<String> list = getDataAsList(r);

        return list.get(indexOfHeader);
    }

    public SelenideElement getLinkOfCell(int record, String header) {

        int index = findHeaderIndex(header);

        SelenideElement link = getStrings(this.records.get(record)).get(index);
        if(link.has(Condition.attribute("href"))) {
            return link;
        } else {
            return null;
        }
    }

    private int findHeaderIndex(String header) {
        int index = -1;

        for (int i = 0; i < numberOfColumns; i++) {
            String hdr = this.headers.get(i).getText();
            if (hdr.contains(header)) {
                return i;
            }
        }

        return index;
    }

    private List<SelenideElement> getStrings(SelenideElement record) {
        List<SelenideElement> list = new ArrayList<>();
        ElementsCollection elements = record.$$(By.tagName("td"));
        list.addAll(elements);
        return list;
    }

    public static class Table {
        private int row;
        private int column;

        public Table(String selector) {

        }
    }
}
