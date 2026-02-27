package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.UserRole;
import br.com.raac_system.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_email_per_tenant", columnNames = { "tenant_id", "email" })
})
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 14)
    private String cpf;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(length = 20)
    private String phone;

    private String photoUrl;

    private String twoFactorSecret;
}
