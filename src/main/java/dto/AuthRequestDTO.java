package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
//API User for JSON converted for JAVA
public class AuthRequestDTO {

    private String username;
    private String password;
}
