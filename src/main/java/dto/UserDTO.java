package dto;


import Model.Address;
import Model.Bank;
import Model.Company;
import Model.Hair;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Integer age;
    private LocalDate birthDate;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;
    private Address address;
    private String macAddress;
    private String university;
    private Bank bank;
    private Company company;
    private String ein;
    private String ssn;
    private String userAgent;
    private Hair hair;
}