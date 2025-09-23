package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdTestsOkHttp {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoic3ZuZ2R2QGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzU5MjMyNzMzLCJpYXQiOjE3NTg2MzI3MzN9._PWz41GL-u05sjquFlycWJh5Ny_-hsr_J-WbcHt9M34";
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    //MediaType chose only OKhttp3 and CONSTANTA == JSON
    String id;   //Created for @BeforeMethod



    //===========================BEFORE METHOD FOR CREATE CONTACT ===========================================


    @BeforeMethod
    public void preCondition() throws IOException {

        //Created random == new Random().nextInt(1000)+1000;
        int i = new Random().nextInt(1000)+1000;

        //Create contact ContactDTO.builder().build();
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Papa")
                .lastName("Aba")
                .email("papa"+i+"@gmail.com")  //random == +i+
                .phone("12345678"+i)
                .address("Haifa")
                .description("PapaAba")
                .build();

        //Method POST have to  RequestBody.create(gson.toJson(contactDTO),JSON)
        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body) //method
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class); //parsing
        String message = messageDTO.getMessage(); //get id from "message": "Contact was added! ID: a72596d5-4c61-40ce-a687-67ecffa2765d"
        System.out.println(message);

        //get id from message == id split
       String[]all = message.split(": ");
       id = all[1];
        System.out.println(id);
    }


    //=========================== POSITIVE TEST DELETE BY ID =================================================

    @Test
    public void DeleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)  //String id;
                .delete()  //method for test
                .addHeader("Authorization",token)
                .build();
        //Send to client.newCall()
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);
        //Parsing to DeleteByIdResponseDTO
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        //sout check to response message
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");
    }
}

