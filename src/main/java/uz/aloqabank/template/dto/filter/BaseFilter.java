package uz.aloqabank.template.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilter {

    private String searchKey;
    private Integer start = 0;
    private Integer limit = 50;
}
