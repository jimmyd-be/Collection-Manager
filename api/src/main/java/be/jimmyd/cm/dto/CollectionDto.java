package be.jimmyd.cm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private String type;
    private List<String> members;
    private List<FieldDto> fields;
}
