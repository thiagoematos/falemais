package vizir.telzir.falemais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vizir.telzir.falemais.model.entity.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findOneByOrigin_IdAndDestiny_Id(long originId, long destinyId);

}
