package PS_project.API_dummy_data.controller;

import PS_project.API_dummy_data.model.Users;
import PS_project.API_dummy_data.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Users user;

    @BeforeEach
    void setUp() {
        user = Users.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role("admin")
                .age(30)
                .ssn("123-459-678")
                .build();
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        when(userService.getAllUsers()).thenReturn(List.of(user));
        ResponseEntity<List<Users>> response = userController.getAllUsers();
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUsersByRole_ShouldReturnFilteredUsers() {
        when(userService.getUsersByRole("admin")).thenReturn(List.of(user));
        ResponseEntity<List<Users>> response = userController.getUsersByRole("admin");
        assertEquals(1, response.getBody().size());
        assertEquals("admin", response.getBody().get(0).getRole());
    }

    @Test
    void getUsersSortedByAge_ShouldReturnSortedUsers() {
        when(userService.getUsersSortedByAge("asc")).thenReturn(List.of(user));
        ResponseEntity<List<Users>> response = userController.getUsersSortedByAge("asc");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userService.getUserById(1L)).thenReturn(user);
        ResponseEntity<Users> response = userController.getUserById(1L);
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getUserBySsn_ShouldReturnUser_WhenUserExists() {
        when(userService.getUserBySsn("123-459-678")).thenReturn(user);
        ResponseEntity<Users> response = userController.getUserBySsn("123-459-678");
        assertNotNull(response.getBody());
        assertEquals("123-459-678", response.getBody().getSsn());
    }

    @Test
    void loadUsers_ShouldCallServiceMethod() {
        doNothing().when(userService).loadUsersData();
        ResponseEntity<String> response = userController.loadUsers();
        assertEquals("Users data loaded successfully", response.getBody());
        verify(userService, times(1)).loadUsersData();
    }
}
