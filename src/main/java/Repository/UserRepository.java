package Repository;

import Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findByCompanyDepartment(String role);
    Optional<Users> findBySsn(String ssn);
}
