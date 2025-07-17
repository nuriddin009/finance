package uz.banking.finance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.banking.finance.constants.TransactionType;
import uz.banking.finance.model.template.MBaseObject;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class MTransaction extends MBaseObject {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MUser user;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    private TransactionType transactionType;

    @Column(name = "description")
    private String description;
}
