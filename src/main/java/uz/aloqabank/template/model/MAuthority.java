package uz.aloqabank.template.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "authority")
public class MAuthority implements Serializable {
    @Id
    private String code;

    private String name;
}
