package vizir.telzir.falemais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vizir.telzir.falemais.model.entity.DDD;
import vizir.telzir.falemais.service.DddService;

import java.util.List;

@RestController
@RequestMapping("/dDDs")
public class DddCcontroller {

    @Autowired
    private DddService dddService;

    @GetMapping("/no-link")
    List<DDD> findAll() {
        return dddService.findAll();
    }

}
