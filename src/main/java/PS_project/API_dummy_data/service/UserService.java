package PS_project.API_dummy_data.service;

import PS_project.API_dummy_data.dto.UserDTO;
import PS_project.API_dummy_data.dto.UserResponse;
import PS_project.API_dummy_data.exception.ResourceNotFoundException;
import PS_project.API_dummy_data.model.Users;
import PS_project.API_dummy_data.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${api.users.url}")
    private String usersApiUrl;

    @PostConstruct
    //@Scheduled(cron = "${data.refresh.cron}")
    //@Retryable(value = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void loadUsersData() {
        log.info("Loading users data from external API");
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(usersApiUrl, String.class);
            if (response != null) {
            UserResponse usersResponse = objectMapper.readValue(response.getBody(), UserResponse.class);
                userRepository.deleteAll();
                userRepository.saveAll(usersResponse.getUsers().stream()
                        .map(this::convertToEntity)
                        .toList());
                log.info("Successfully loaded {} users", usersResponse.getUsers().size());
            }
            else{
                log.info("API response returned null!!");
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
                .birthDate(userDto.getBirthDateAsLocalDate())
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
                .role(userDto.getRole())
                .image(userDto.getImage())
                .eyeColor(userDto.getEyeColor())
                .hair(userDto.getHair())
                .ip(userDto.getIp())
                .crypto(userDto.getCrypto())
                .build();
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Users> getUsersByRole(String role) {
        return userRepository.findByRole(role);
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

