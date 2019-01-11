package vizir.telzir.falemais.repository;

import org.springframework.data.repository.CrudRepository;
import vizir.telzir.falemais.model.entity.Plan;

public interface PlanRepository extends CrudRepository<Plan, Long> {
}
