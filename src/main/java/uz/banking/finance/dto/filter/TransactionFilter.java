package uz.banking.finance.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class TransactionFilter extends BaseFilter {

    private LocalDate from;
    private LocalDate to;
    private UUID userId;

}
