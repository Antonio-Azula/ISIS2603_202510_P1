package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    private LocalDateTime fecha;
    private String nombreArtista;
    private Long aforo;

    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadioEntity estadio;
}
