package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.InsuranceEnvironment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "insurance_configs")
public class InsuranceConfig extends BaseEntity {

    @Column(nullable = false)
    private String operatorName;

    @Column(length = 50)
    private String ansCode;

    private String wsUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InsuranceEnvironment environment;

    @Column(columnDefinition = "TEXT")
    private String credentialsEncrypted;

    @Column(length = 20)
    private String tissVersion;

    private String certPath;

    @Column(columnDefinition = "TEXT")
    private String certPasswordEncrypted;
}
