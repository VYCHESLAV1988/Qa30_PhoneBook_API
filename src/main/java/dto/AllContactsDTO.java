package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;


@Getter
@Setter
@ToString
@Builder

//Have collection contact format JSON
public class AllContactsDTO {

    //Chose to List ==> Import class ==> List (of java.util)
    private List<ContactDTO>contacts;

}
