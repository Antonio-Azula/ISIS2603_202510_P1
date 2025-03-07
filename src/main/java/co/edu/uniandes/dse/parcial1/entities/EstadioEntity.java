package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    private String nombreCiudad;
    private Long capacidad;

    @PodamExclude
    @OneToMany(fetch = FetchType.LAZY)
    private List<ConciertoEntity> conciertos;
    
}
