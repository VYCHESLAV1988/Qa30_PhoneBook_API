package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

//Method for JASON convert for JAVA == have import class
public class LoginTestsOkhttp {

    Gson gson = new Gson();

    //MediaType chose only OKhttp3 and CONSTANTA == JSON
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    //Created OkHttpClient for send responds
    OkHttpClient client = new OkHttpClient();


    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("margo@gmail.com")
                .password("Mmar123456$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()   //Create to Builder().build()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute(); //Add to expedition method .execute
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }

    // ===================== NEGATIVE TESTS ===========================

    @Test
    public void loginWrongEmail() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("margogmail.com") //margo@gmail.com
                .password("Mmar123456$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()   //Create to Builder().build()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute(); //Add to expedition method .execute
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(),401);
        Assert.assertEquals(errorDTO.getMessage(),"Login or password is incorrect");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/login/usernamepassword");
    }

    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("margo@gmail.com")
                .password("mar1").build(); //Mmar123456$
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()   //Create to Builder().build()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute(); //Add to expedition method .execute
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(),401);
        Assert.assertEquals(errorDTO.getMessage(),"Login or password is incorrect");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/login/usernamepassword");
    }

    @Test
    public void loginWrongUnregistered() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("margo1999999999999999@gmail.com")
                .password("Mmar123456$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()   //Create to Builder().build()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute(); //Add to expedition method .execute
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(),401);
        Assert.assertEquals(errorDTO.getMessage(),"Login or password is incorrect");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/login/usernamepassword");
    }
}
