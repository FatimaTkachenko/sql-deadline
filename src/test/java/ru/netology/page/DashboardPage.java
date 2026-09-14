package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private final SelenideElement heading = $("h2[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public void shouldHaveHeading(String expectedText) {
        heading.shouldHave(text(expectedText));
    }
}