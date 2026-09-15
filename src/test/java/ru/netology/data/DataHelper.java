package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import ru.netology.mode.User;

public class DataHelper {

    private static final Faker FAKER = new Faker();

    private DataHelper() {
    }

    /**
     * Демо-пользователь из SUT, заводится автоматически при старте приложения.
     * Пароль подтверждён вручную через UI.
     */
    public static User getValidUser() {
        return new User(null, "vasya", "qwerty123", "active");
    }

    /**
     * Неверный пароль для теста неуспешного входа.
     */
    public static String getInvalidPassword() {
        return "wrong_password";
    }

    /**
     * Свежий логин (Faker) — для сценариев с регистрацией новых пользователей.
     */
    public static String generateRandomLogin() {
        return FAKER.name().username();
    }

    /**
     * Свежий пароль (Faker).
     */
    public static String generateRandomPassword() {
        return FAKER.internet().password();
    }
}