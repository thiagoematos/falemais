package vizir.telzir.falemais.model.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Getter
@Entity
public class DDD {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 3)
    private String code;

}
