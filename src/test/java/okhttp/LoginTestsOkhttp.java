package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {
    Gson gson = new Gson();  //Method for JASON convert for JAVA == have import class

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
//Create to Builder().build()
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
       Response response = client.newCall(request).execute(); //Add to expedition method .execute
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }
}
