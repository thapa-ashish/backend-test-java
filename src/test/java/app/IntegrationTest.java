package app;

import app.controller.AuthController;
import app.dto.LoginRequest;
import app.dto.Response;
import com.google.gson.Gson;
import io.jooby.JoobyTest;
import io.jooby.StatusCode;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JoobyTest(App.class)
class IntegrationTest {

    static OkHttpClient client = new OkHttpClient();
    static Gson gson = new Gson();

    @Test
    void login(int serverPort) throws IOException {
        LoginRequest loginRequest = new LoginRequest("username", "password");

        Request req = new Request.Builder()
                .addHeader("Content-Type", io.jooby.MediaType.JSON)
                .addHeader("Accept", io.jooby.MediaType.JSON)
                .url("http://localhost:" + serverPort + "/auth/login")
                .post(RequestBody.create(gson.toJson(loginRequest), MediaType.parse(io.jooby.MediaType.JSON)))
                .build();

        try (okhttp3.Response rsp = client.newCall(req).execute()) {
            assertEquals(StatusCode.OK.value(), rsp.code());
            Response response = gson.fromJson(rsp.body().string(), Response.class);
            assertEquals("login success", response.getMessage());
        }
    }

    @Test
    void logout(int serverPort) throws IOException {
        RequestBody logoutRequest = RequestBody.create(new byte[]{}, null);

        Request req = new Request.Builder()
                .addHeader("Accept", io.jooby.MediaType.JSON)
                .url("http://localhost:" + serverPort + "/auth/logout")
                .method("POST", logoutRequest)
                .build();

        try (okhttp3.Response rsp = client.newCall(req).execute()) {
            assertEquals(StatusCode.OK.value(), rsp.code());
            Response response = gson.fromJson(rsp.body().string(), Response.class);
            assertEquals("logout success", response.getMessage());
        }
    }
}
