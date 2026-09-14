package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeAll
    static void setUp() {
        Configuration.headless = false;
        Configuration.browserSize = "1600x900";
    }

    @BeforeEach
    void openPage() {
        SQLHelper.clearAll();
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Успешный вход в личный кабинет через код из БД")
    void shouldSuccessfullyLogin() {
        var user = DataHelper.getValidUser();

        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(user.getLogin(), user.getPassword());

        String code = SQLHelper.getVerificationCode(user.getLogin());
        DashboardPage dashboardPage = verificationPage.validVerify(code);
        dashboardPage.shouldHaveHeading("Личный кабинет");
    }
}