package uz.banking.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SelectItem implements Serializable {
    private UUID id;
    private String name;
    private String code;
    private String description;

    public SelectItem(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
