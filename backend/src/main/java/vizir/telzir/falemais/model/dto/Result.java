package vizir.telzir.falemais.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Result {

    private double withPlan;
    private double withoutPlan;

    public Result(double withPlan, double withoutPlan) {
        this.withPlan = withPlan;
        this.withoutPlan = withoutPlan;
    }

}
