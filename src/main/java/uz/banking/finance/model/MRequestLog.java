package uz.banking.finance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.banking.finance.model.template.MBaseObject;

@Setter
@Getter
@Entity
@Table(name = "request_log")
public class MRequestLog extends MBaseObject {

    private String requestId;

    @Column(columnDefinition = "TEXT")
    private String request;

    @Column(columnDefinition = "TEXT")
    private String response;

    private String path;

    private int duration;

    private String token;
}
