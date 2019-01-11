package vizir.telzir.falemais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vizir.telzir.falemais.model.dto.Result;
import vizir.telzir.falemais.model.entity.Plan;
import vizir.telzir.falemais.model.entity.Rate;
import vizir.telzir.falemais.repository.PlanRepository;
import vizir.telzir.falemais.repository.RateRepository;

@Service
public class CalculatorService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RateRepository rateRepository;

    public Result calc(long originId, long destinyId, long planId, int minutes) {
        Rate rate = rateRepository.findOneByOrigin_IdAndDestiny_Id(originId, destinyId);
        if (rate == null) {
            return new Result();
        }
        Plan plan = planRepository.findById(planId).get();
        double withPlan = withPlan(plan, rate, minutes);
        double withoutPlan = withoutPlan(rate, minutes);
        return new Result(withPlan, withoutPlan);
    }

    private double withPlan(Plan plan, Rate rate, int minutes) {
        int minutesNotCovered = minutes - plan.getMinutes();
        return minutesNotCovered > 0 ? minutesNotCovered * rate.getValue() * 1.1 : 0;
    }

    private double withoutPlan(Rate rate, int minutes) {
        return rate.getValue() * minutes;
    }

}
