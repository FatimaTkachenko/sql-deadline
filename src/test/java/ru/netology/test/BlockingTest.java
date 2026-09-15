package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.EnvironmentCheck;
import ru.netology.data.SQLHelper;
import ru.netology.mode.User;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockingTest {

    @BeforeAll
    static void setUp() {
        Assumptions.assumeTrue(EnvironmentCheck.isReady(), EnvironmentCheck.hint());

        Configuration.headless = false;
        Configuration.browserSize = "1600x900";
    }

    @BeforeEach
    void openPage() {
        SQLHelper.clearAll();
        open("http://localhost:9999");
    }

    // Известный баг SUT: https://github.com/FatimaTkachenko/sql-deadline/issues/1
    // Тест намеренно падает, пока баг не исправлен.
    @Test
    @DisplayName("Блокировка пользователя после 3 неудачных попыток ввода пароля")
    void shouldBlockUserAfterThreeFailedAttempts() {
        var user = DataHelper.getValidUser();
        String wrongPassword = DataHelper.getInvalidPassword();

        LoginPage loginPage = new LoginPage();

        for (int i = 0; i < 3; i++) {
            loginPage.invalidLogin(user.getLogin(), wrongPassword);
            loginPage.shouldShowErrorNotification("Ошибка! Неверно указан логин или пароль");
        }

        User blockedUser = SQLHelper.getUserByLogin(user.getLogin());
        assertEquals("blocked", blockedUser.getStatus(),
                "Пользователь должен быть заблокирован после 3 неудачных попыток");
    }
}