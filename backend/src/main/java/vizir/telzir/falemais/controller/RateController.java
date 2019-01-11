package vizir.telzir.falemais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vizir.telzir.falemais.model.entity.Rate;
import vizir.telzir.falemais.service.RateService;

import java.util.List;

@RestController
@RequestMapping("/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping
    List<Rate> findAll() {
        return this.rateService.findAll();
    }

    @PostMapping
    void create(@RequestBody Rate rate) {
        rateService.saveOrUpdate(rate);
    }

    @PutMapping
    void update(@RequestBody Rate rate) {
        rateService.saveOrUpdate(rate);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        rateService.delete(id);
    }

}
