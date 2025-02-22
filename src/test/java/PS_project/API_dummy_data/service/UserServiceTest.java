package PS_project.API_dummy_data.service;

import PS_project.API_dummy_data.dto.UserDTO;
import PS_project.API_dummy_data.dto.UserResponse;
import PS_project.API_dummy_data.exception.ResourceNotFoundException;
import PS_project.API_dummy_data.model.Users;
import PS_project.API_dummy_data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUsersData_Success() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(Arrays.asList(userDTO));

        String jsonResponse = "{\"users\":[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}]}";
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(jsonResponse));

        // Act
        userService.loadUsersData();

        // Assert
        verify(userRepository).deleteAll();
        verify(userRepository).saveAll(anyList());
    }

    @Test
    void loadUsersData_WhenAPIFails() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RestClientException("API Error"));

        // Act & Assert
        assertThrows(RestClientException.class, () -> userService.loadUsersData());
    }

    @Test
    void getAllUsers_Success() {
        // Arrange
        List<Users> expectedUsers = Arrays.asList(
                Users.builder().id(1L).firstName("John").build(),
                Users.builder().id(2L).firstName("Jane").build()
        );
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<Users> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll();
    }

    @Test
    void getUsersByRole_Success() {
        // Arrange
        String role = "admin";
        List<Users> expectedUsers = Arrays.asList(
                Users.builder().id(1L).role("admin").build()
        );
        when(userRepository.findByRole(role)).thenReturn(expectedUsers);

        // Act
        List<Users> actualUsers = userService.getUsersByRole(role);

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findByRole(role);
    }

    @Test
    void getUsersSortedByAge_AscendingOrder() {
        // Arrange
        List<Users> expectedUsers = Arrays.asList(
                Users.builder().id(1L).age(25).build(),
                Users.builder().id(2L).age(30).build()
        );
        when(userRepository.findAll(any(Sort.class))).thenReturn(expectedUsers);

        // Act
        List<Users> actualUsers = userService.getUsersSortedByAge("asc");

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll(Sort.by(Sort.Direction.ASC, "age"));
    }

    @Test
    void getUserById_Success() {
        // Arrange
        Long userId = 1L;
        Users expectedUser = Users.builder().id(userId).firstName("John").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        Users actualUser = userService.getUserById(userId);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_NotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserBySsn_Success() {
        // Arrange
        String ssn = "123-45-6789";
        Users expectedUser = Users.builder().ssn(ssn).firstName("John").build();
        when(userRepository.findBySsn(ssn)).thenReturn(Optional.of(expectedUser));

        // Act
        Users actualUser = userService.getUserBySsn(ssn);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findBySsn(ssn);
    }

    @Test
    void getUserBySsn_NotFound() {
        // Arrange
        String ssn = "999-99-9999";
        when(userRepository.findBySsn(ssn)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserBySsn(ssn));
        verify(userRepository).findBySsn(ssn);
    }
}