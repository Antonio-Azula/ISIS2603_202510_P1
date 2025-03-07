package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Transactional
    public ConciertoEntity crearConcierto(ConciertoEntity conciertoEntity) throws IllegalOperationException
    {
        log.info("Creando concierto...");

        if(Duration.between(LocalDateTime.now(), conciertoEntity.getFecha()).toDays() < 0)
        {
            throw new IllegalOperationException("La fecha programada ya paso");
        }

        if(conciertoEntity.getAforo() < 10)
        {
            throw new IllegalOperationException("La capacidad minima es de 10 personas");
        }

        if(conciertoEntity.getPresupuesto() < 1000)
        {
            throw new IllegalOperationException("El presupuesto minimo debe ser de 1000 dolares");
        }

        log.info("Se a creado correctamente el concierto.");

        return conciertoRepository.save(conciertoEntity);
    }

}
