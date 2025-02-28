package PS_project.API_dummy_data.controller;

import PS_project.API_dummy_data.model.Users;
import PS_project.API_dummy_data.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management API", description = "APIs for managing users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/load")
    @Operation(summary = "Load users from external API", description = "Fetches users from the external API and loads them into the H2 database")
    @ApiResponse(responseCode = "200", description = "Users loaded successfully")
    @ApiResponse(responseCode = "500", description = "Failed to load users")
    public ResponseEntity<String> loadUsers() {
        log.info("Request received to load users data");
        userService.loadUsersData();
        return ResponseEntity.ok("Users data loaded successfully");
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    public ResponseEntity<List<Users>> getAllUsers() {
        log.info("Request received to get all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Returns a list of users with the specified role")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    public ResponseEntity<List<Users>> getUsersByRole(
            @Parameter(description = "Role/Department to filter users by", required = true)
            @PathVariable
            @NotBlank String role) {
        log.info("Request received to get users by role: {}", role);
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/sort")
    @Operation(summary = "Get users sorted by age", description = "Returns a list of users sorted by age in ascending or descending order")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    public ResponseEntity<List<Users>> getUsersSortedByAge(
            @Parameter(description = "Sort direction (asc or desc)", required = true)
            @RequestParam
            @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be 'asc' or 'desc'")
            String direction)
    {
        log.info("Request received to get users sorted by age in {} order", direction);
        return ResponseEntity.ok(userService.getUsersSortedByAge(direction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user with the specified ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Users> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id) {
        log.info("Request received to get user by ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/ssn/{ssn}")
    @Operation(summary = "Get user by SSN", description = "Returns a user with the specified SSN")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Users> getUserBySsn(
            @Parameter(description = "User SSN", required = true)
            @PathVariable
            @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$")
            String ssn) {
        log.info("Request received to get user by SSN: {}", ssn);
        return ResponseEntity.ok(userService.getUserBySsn(ssn));
    }
}