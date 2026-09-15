package ru.netology.data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EnvironmentCheck {

    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;
    private static final String SUT_HOST = "localhost";
    private static final int SUT_PORT = 9999;

    private EnvironmentCheck() {
    }

    public static boolean isDatabaseAvailable() {
        return isTcpPortOpen(DB_HOST, DB_PORT);
    }

    public static boolean isSutAvailable() {
        return isTcpPortOpen(SUT_HOST, SUT_PORT);
    }

    public static boolean isReady() {
        return isDatabaseAvailable() && isSutAvailable();
    }

    public static String hint() {
        return "Окружение не готово для запуска автотестов.\n" +
                "Проверьте, что:\n" +
                "  1. Запущен Docker Desktop (Engine running).\n" +
                "  2. Поднят контейнер MySQL: docker compose up -d\n" +
                "  3. Запущен SUT в отдельном окне: java -jar artifacts/app/app-deadline.jar\n" +
                "  4. SUT отвечает на http://localhost:9999\n" +
                "Подробности см. в README.md, раздел «Как запустить».";
    }

    private static boolean isTcpPortOpen(String host, int port) {
        try (var socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}