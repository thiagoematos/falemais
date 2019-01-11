package vizir.telzir.falemais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vizir.telzir.falemais.model.entity.DDD;

public interface DddJpaRepository extends JpaRepository<DDD, Long> {
}
