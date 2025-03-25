package uz.aloqabank.template.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;


@Setter
@Getter
@MappedSuperclass
public abstract class MBaseObject extends AuditModel {

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted = false;

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_By", length = 50)
    private String modifiedBy;

    public Long getId() {
        return null;
    }
}
