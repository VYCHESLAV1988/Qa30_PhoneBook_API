package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Create to annotation lombok

@Getter
@Setter
@ToString
@Builder

public class AuthResponseDTO {
    private String token;
}
