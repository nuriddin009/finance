package uz.banking.finance.model.template;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.UUID;


@Setter
@Getter
@MappedSuperclass
public abstract class MBaseObject extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted = false;

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_By", length = 50)
    private String modifiedBy;

}
