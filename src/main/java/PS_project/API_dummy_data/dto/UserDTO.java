package PS_project.API_dummy_data.dto;


import PS_project.API_dummy_data.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Integer age;
    private String birthDate;

    public LocalDate getBirthDateAsLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(this.birthDate, formatter);
    }
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
    private String role;
    private String image;
    private String bloodGroup;

    private Float weight;
    private String eyeColor;
    private String ip;
    private Crypto crypto;
}