package co.edu.uniandes.dse.parcial1.services;

import java.util.Optional;
import java.time.Duration;
import java.time.LocalDateTime;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService 
{   
    @Autowired
    private EstadioRepository estadioRepository;

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Transactional
    public ConciertoEntity agregarEstadio (Long conciertoId, Long estadioId) throws IllegalOperationException, EntityNotFoundException
    {
        log.info("Agregando Estadio");

        Optional<ConciertoEntity> conciertoOpt = conciertoRepository.findById(conciertoId);
		Optional<EstadioEntity> estadioOpt = estadioRepository.findById(estadioId);

        if(conciertoOpt.isEmpty())
        {
            throw new EntityNotFoundException("El concierto por buscar no se a encontrado");
        }

        ConciertoEntity conciertoEntity = conciertoOpt.get();
        
        if(estadioOpt.isEmpty())
        {
            throw new EntityNotFoundException("El estadio por buscar no se a encontrado");
        }

        EstadioEntity estadioEntity = estadioOpt.get();

        if(conciertoEntity.getAforo() > estadioEntity.getCapacidad())
        {
            throw new IllegalOperationException("El aforo del concierto no debe sobrepasar el del estadio");
        }

        if(estadioEntity.getPrecioAlquiler() > conciertoEntity.getPresupuesto())
        {
            throw new IllegalOperationException("El presupuesto del concierto es menor que el precio de alquiler");
        }

        if(Duration.between(LocalDateTime.now(), conciertoEntity.getFecha()).toDays() < 2)
        {
            throw new IllegalOperationException("El tiempo minimo para asociar un concierto a un estadio es de dos dias");
        }

        log.info("Se a asociado correctamente el estadio con ID " + estadioId + " al concierto con ID "+ conciertoId);

        return conciertoRepository.save(conciertoEntity);
    }
    
}
