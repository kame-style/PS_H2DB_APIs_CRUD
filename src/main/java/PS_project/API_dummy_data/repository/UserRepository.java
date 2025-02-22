package PS_project.API_dummy_data.repository;

import PS_project.API_dummy_data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    List<Users> findByRole(String role);
    Optional<Users> findBySsn(String ssn);
}
