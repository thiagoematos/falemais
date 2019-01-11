package vizir.telzir.falemais.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Plan {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private int minutes;

    public Plan(int minutes) {
        this.minutes = minutes;
    }

}
