package uz.banking.finance.dto.filter;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilter extends BaseFilter {
    private Boolean active;
    @Positive
    private Long raspberrypiId;

}
