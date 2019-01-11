package vizir.telzir.falemais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vizir.telzir.falemais.model.entity.Rate;
import vizir.telzir.falemais.repository.RateRepository;

import java.util.List;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    public List<Rate> findAll() {
        return rateRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    public void saveOrUpdate(Rate rate) {
        rateRepository.saveAndFlush(rate);
    }

    public void delete(long id) {
        rateRepository.deleteById(id);
    }

}
