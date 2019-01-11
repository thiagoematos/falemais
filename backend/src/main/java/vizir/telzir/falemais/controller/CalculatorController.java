package vizir.telzir.falemais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vizir.telzir.falemais.model.dto.Result;
import vizir.telzir.falemais.service.CalculatorService;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/{origin}/{destiny}/{plan}/{minutes}")
    Result calc(@PathVariable long origin,
                @PathVariable long destiny,
                @PathVariable long plan,
                @PathVariable int minutes) {
        return calculatorService.calc(origin, destiny, plan, minutes);
    }

}
