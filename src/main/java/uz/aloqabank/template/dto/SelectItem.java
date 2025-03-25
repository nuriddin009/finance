package uz.aloqabank.template.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SelectItem implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String description;

    public SelectItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
