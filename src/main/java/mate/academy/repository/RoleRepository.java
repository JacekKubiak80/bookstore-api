package mate.academy.repository;

import java.util.Optional;
import mate.academy.model.Role;
import mate.academy.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
