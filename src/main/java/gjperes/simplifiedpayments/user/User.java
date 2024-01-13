package gjperes.simplifiedpayments.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

@Entity
@Table(name = User.TABLE_NAME, schema = "public")
@Data
public class User {
    public static final String TABLE_NAME = "payments_user";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = User.TABLE_NAME + "id_seq")
    @SequenceGenerator(name = User.TABLE_NAME + "id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false, unique = true) private String cpf;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) private UserRole role;

    @CreatedBy
    @Column(nullable = false, updatable = false) private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false) private ZonedDateTime createdAt;

    @LastModifiedBy
    @Column private String updatedBy;

    @LastModifiedDate
    @Column private ZonedDateTime updatedAt;
}
