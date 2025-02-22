package Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private String macAddress;
    private String university;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    private String ein;
    private String ssn;
    private String userAgent;
    }
