package vizir.telzir.falemais.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import vizir.telzir.falemais.model.dto.Result;
import vizir.telzir.falemais.model.entity.Plan;
import vizir.telzir.falemais.model.entity.Rate;
import vizir.telzir.falemais.repository.PlanRepository;
import vizir.telzir.falemais.repository.RateRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class CalculatorServiceTest {

    @TestConfiguration
    static class CalculatorServiceImplTestContextConfiguration {

        @Bean
        public CalculatorService calculatorService() {
            return new CalculatorService();
        }
    }

    @Autowired
    private CalculatorService calculatorService;

    @MockBean
    private PlanRepository planRepository;

    @MockBean
    private RateRepository rateRepository;

    @Test
    public void Dado_QueNaoTemTarifa_Entao_PrecoZero() {
        // Given
        long originId = 1;
        long destinyId = 1;
        long planId = 1;
        int minutes = 120;
        Mockito.when(rateRepository.findOneByOrigin_IdAndDestiny_Id(originId, destinyId)).thenReturn(null);

        // When
        Result result = calculatorService.calc(originId, destinyId, planId, minutes);

        // Then
        Assert.assertEquals(0d, result.getWithPlan(), 0d);
        Assert.assertEquals(0d, result.getWithoutPlan(), 0d);
    }

    @Test
    public void Dado_QueTemTarifa_E_MinutosGastosSaoCobertosPeloPlano_Entao_ComPlanoPrecoZero_E_SemPlanoTarifado() {
        // Given
        // spentMinutes <= coveredMinutes = Minutos gastos sao cobertos pelo plano
        int coveredMinutes = 60;
        int spentMinutes = 20;

        double rateValue = 2;

        long originId = 1;
        long destinyId = 2;
        long planId = 3;

        Mockito.when(rateRepository.findOneByOrigin_IdAndDestiny_Id(originId, destinyId)).thenReturn(new Rate(rateValue));
        Mockito.when(planRepository.findById(planId)).thenReturn(Optional.of(new Plan(coveredMinutes)));

        // When
        Result result = calculatorService.calc(originId, destinyId, planId, spentMinutes);

        // Then
        int withPlan = 0;
        int withoutPlan = 40; // spentMinutes * rateValue
        Result expected = new Result(withPlan, withoutPlan);
        Assert.assertEquals(expected.getWithPlan(), result.getWithPlan(), 0d);
        Assert.assertEquals(expected.getWithoutPlan(), result.getWithoutPlan(), 0d);
    }

    @Test
    public void Dado_QueTemTarifa_E_NemTodosOsMinutosGastosSaoCobertosPeloPlano_Entao_ComPlanoPreco10Porcento_E_SemPlanoTarifado() {
        // Given
        // spentMinutes > coveredMinutes = Minutos gastos nao sao cobertos pelo plano
        int coveredMinutes = 60;
        int spentMinutes = 80;

        double rateValue = 2;

        long originId = 1;
        long destinyId = 2;
        long planId = 3;

        Mockito.when(rateRepository.findOneByOrigin_IdAndDestiny_Id(originId, destinyId)).thenReturn(new Rate(rateValue));
        Mockito.when(planRepository.findById(planId)).thenReturn(Optional.of(new Plan(coveredMinutes)));

        // When
        Result result = calculatorService.calc(originId, destinyId, planId, spentMinutes);

        // Then
        double withPlan = 44; // 1.1 * (spentMinutes - coveredMinutes) * rateValue
        int withoutPlan = 160; // spentMinutes * rateValue
        Result expected = new Result(withPlan, withoutPlan);
        Assert.assertEquals(expected.getWithPlan(), result.getWithPlan(), 0d);
        Assert.assertEquals(expected.getWithoutPlan(), result.getWithoutPlan(), 0d);
    }

}
