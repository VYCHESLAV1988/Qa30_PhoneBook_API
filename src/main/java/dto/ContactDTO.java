package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Added annotations lombok
@Getter
@Setter
@ToString
@Builder

//Contact DTO have to JSON format  == added comments /*CODE TEXT*/

    /*  {
        "id": "string",
            "name": "string",
            "lastName": "string",
            "email": "string",
            "phone": "1095576585",
            "address": "string",
            "description": "string"
    }                                */


public class ContactDTO {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String description;
}
