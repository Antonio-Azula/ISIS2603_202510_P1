package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public EstadioEntity crearConcierto(EstadioEntity estadioEntity) throws IllegalOperationException
    {
        log.info("Creando estadio...");

        if(estadioEntity.getNombreCiudad().length() < 3)
        {
            throw new IllegalOperationException("El nombre de la ciudad debe ser de minimo tres letras");
        }

        if(estadioEntity.getCapacidad() < 1000)
        {
            throw new IllegalOperationException("La capacidad minima debe ser de 1000 personas");
        }

        if(estadioEntity.getPrecioAlquiler() < 100000)
        {
            throw new IllegalOperationException("El precio de alquiler minimo debe ser de 100000 dolares");
        }

        log.info("Se a creado correctamente el estadio.");

        return estadioRepository.save(estadioEntity);
    }
}
