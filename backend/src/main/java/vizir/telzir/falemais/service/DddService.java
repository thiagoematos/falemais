package vizir.telzir.falemais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vizir.telzir.falemais.model.entity.DDD;
import vizir.telzir.falemais.repository.DddJpaRepository;

import java.util.List;

@Service
public class DddService {

    @Autowired
    private DddJpaRepository dddJpaRepository;

    public List<DDD> findAll() {
        return dddJpaRepository.findAll();
    }

}
