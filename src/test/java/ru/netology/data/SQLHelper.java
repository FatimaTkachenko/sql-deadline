package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.mode.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    /**
     * Получить пользователя по логину.
     */
    @SneakyThrows
    public static User getUserByLogin(String login) {
        var sql = "SELECT * FROM users WHERE login = ?;";
        try (var conn = getConnection()) {
            return RUNNER.query(conn, sql, new BeanHandler<>(User.class), login);
        }
    }

    /**
     * Получить последний сгенерированный код подтверждения для пользователя.
     * SUT сохраняет несколько кодов — берём самый свежий.
     */
    @SneakyThrows
    public static String getVerificationCode(String login) {
        var sql = "SELECT code FROM auth_codes " +
                "WHERE user_id = (SELECT id FROM users WHERE login = ?) " +
                "ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return RUNNER.query(conn, sql, new ScalarHandler<>(), login);
        }
    }

    /**
     * Очистить все таблицы, кроме users.
     * users созданы SUT и содержат BCrypt-хеши паролей — их удалять нельзя.
     */
    @SneakyThrows
    public static void clearAll() {
        try (var conn = getConnection()) {
            RUNNER.update(conn, "DELETE FROM card_transactions;");
            RUNNER.update(conn, "DELETE FROM auth_codes;");
            RUNNER.update(conn, "DELETE FROM cards;");
        }
    }
}