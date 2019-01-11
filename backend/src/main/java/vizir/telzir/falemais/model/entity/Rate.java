package vizir.telzir.falemais.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Rate {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "origin", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private DDD origin;

    @JoinColumn(name="destiny", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private DDD destiny;

    @Column(nullable = false)
    private Double value;

    public Rate(double value) {
        this.value = value;
    }

}
