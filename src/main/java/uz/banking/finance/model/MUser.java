package uz.banking.finance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.banking.finance.model.template.MBaseObject;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "t_user", indexes = {
        @Index(name = "username_idx", columnList = "username")
})
public class MUser extends MBaseObject {

    private String username;
    private String password;

    private String firstName;
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role"))
    private List<MAuthority> roles;
}
