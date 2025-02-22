package PS_project.API_dummy_data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
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

    private String image;
    private String bloodGroup;

    private Float weight;
    private String eyeColor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hair_id")
    private Hair hair;
    private String ip;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crypto_id")
    private Crypto crypto;

    private String role;
    }
