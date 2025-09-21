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

//Have collection contact forma JSON
public class AllContactsDTO {
    private List<ContactDTO>contacts;
}
