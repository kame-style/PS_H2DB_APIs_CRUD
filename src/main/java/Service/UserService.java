package Service;


import Model.Users;
import Repository.UserRepository;
import dto.UserDTO;
import dto.UserResponse;
import exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${api.users.url}")
    private String usersApiUrl;

    @PostConstruct
    @Scheduled(cron = "${data.refresh.cron}")
    //@Retryable(value = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void loadUsersData() {
        log.info("Loading users data from external API");
        try {
            UserResponse response = restTemplate.getForObject(usersApiUrl, UserResponse.class);
            if (response != null && response.getUsers() != null) {
                userRepository.deleteAll();
                userRepository.saveAll(response.getUsers().stream()
                        .map(this::convertToEntity)
                        .toList());
                log.info("Successfully loaded {} users", response.getUsers().size());
            } else {
                log.warn("No users found in the API response");
            }
        } catch (Exception e) {
            log.error("Failed to load users data", e);
            throw new RestClientException("Failed to load users data: " + e.getMessage());
        }
    }

    private Users convertToEntity(UserDTO userDto) {
        if (userDto == null) {
            return null;
        }

        return Users.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .maidenName(userDto.getMaidenName())
                .age(userDto.getAge())
                .birthDate(userDto.getBirthDate())
                .gender(userDto.getGender())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .macAddress(userDto.getMacAddress())
                .university(userDto.getUniversity())
                .bank(userDto.getBank())
                .company(userDto.getCompany())
                .ein(userDto.getEin())
                .ssn(userDto.getSsn())
                .userAgent(userDto.getUserAgent())
                .build();
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Users> getUsersByRole(String role) {
        return userRepository.findByCompanyDepartment(role);
    }

    public List<Users> getUsersSortedByAge(String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return userRepository.findAll(Sort.by(sortDirection, "age"));
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public Users getUserBySsn(String ssn) {
        return userRepository.findBySsn(ssn)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with SSN: " + ssn, HttpStatus.NOT_FOUND));
    }
}
