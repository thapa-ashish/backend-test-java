package app.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "app_user", schema = "PUBLIC")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "timestamp_created")
    private Timestamp timestampCreated;

    protected AppUser() {

    }

    public AppUser(
            String username,
            String password
    ) {
        this.username = username;
        this.password = password;
        this.timestampCreated = new Timestamp(System.currentTimeMillis());
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
