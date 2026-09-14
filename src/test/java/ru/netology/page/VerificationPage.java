package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeField = $("input[name=code]");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public VerificationPage invalidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return this;
    }

    public void shouldShowErrorNotification(String expectedText) {
        errorNotification.shouldBe(visible);
        errorNotification.$(".notification__content").shouldHave(
                com.codeborne.selenide.Condition.text(expectedText));
    }
}