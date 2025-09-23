package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.ErrorDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestsOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;  //random

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("papa"+i+"@gmail.com")
                .password("A12345678a@")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
    }

    // ============================ NEGATIVE TESTS =====================================

    @Test
    public void registrationTestWrongEmail() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;  //random

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("svngdvgmail.com")
                .password("A12345678a@")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Bad Request");
        Assert.assertEquals(errorDTO.getStatus(),400);
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertEquals(errorDTO.getMessage().toString(),"{username=must be a well-formed email address}");

    }

    @Test
    public void registrationTestWrongPassword() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;  //random

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("svngdv@gmail.com")
                .password("123")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Bad Request");
        Assert.assertEquals(errorDTO.getStatus(),400);
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertEquals(errorDTO.getMessage().toString(),"{password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");

    }

    @Test
    public void registrationTestWrongDublicatUser() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;  //random

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("svngdv@gmail.com")
                .password("A12345678a@")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),409);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Conflict");
        Assert.assertEquals(errorDTO.getStatus(),409);
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertEquals(errorDTO.getMessage().toString(),"User already exists");

    }
}
