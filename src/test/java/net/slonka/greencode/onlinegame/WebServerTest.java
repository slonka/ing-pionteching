package net.slonka.greencode.onlinegame;

import com.alibaba.fastjson2.JSON;
import net.slonka.greencode.WebServer;
import net.slonka.greencode.atmservice.domain.ATM;
import net.slonka.greencode.onlinegame.domain.Group;
import net.slonka.greencode.transactions.domain.Account;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebServerTest {
    private static final int SERVER_PORT = 8080;
    private static final String SERVER_URL = "http://localhost:" + SERVER_PORT;
    private static WebServer webServer;
    private static Thread serverThread;

    @BeforeAll
    public static void setUp() {
        serverThread = new Thread(() -> {
            try {
                webServer = new WebServer(SERVER_PORT);
                webServer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @Test
    public void testOnlineGame() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL + "/onlinegame/calculate"))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(readFileFromResources("onlinegame/example_request.json")))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        var body = response.body();
        Group[] actualResponse = JSON.parseObject(body, Group[].class);
        Group[] expectedResponse = JSON.parseObject(readFileFromResources("onlinegame/example_response.json"), Group[].class);

        assertEquals(200, response.statusCode());
        assertArrayEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testAtmService1() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL + "/atms/calculateOrder"))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(readFileFromResources("atmservice/example_1_request.json")))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        var body = response.body();
        ATM[] actualResponse = JSON.parseObject(body, ATM[].class);
        ATM[] expectedResponse = JSON.parseObject(readFileFromResources("atmservice/example_1_response.json"), ATM[].class);

        assertEquals(200, response.statusCode());
        assertArrayEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testAtmService2() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL + "/atms/calculateOrder"))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(readFileFromResources("atmservice/example_2_request.json")))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        var body = response.body();
        ATM[] actualResponse = JSON.parseObject(body, ATM[].class);
        ATM[] expectedResponse = JSON.parseObject(readFileFromResources("atmservice/example_2_response.json"), ATM[].class);

        assertEquals(200, response.statusCode());
        assertArrayEquals(expectedResponse, actualResponse);
    }

    @AfterAll
    public static void tearDown() {
        webServer.stop();
        serverThread.interrupt();
    }

    private String readFileFromResources(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("Resource file not found: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }
    }
}
