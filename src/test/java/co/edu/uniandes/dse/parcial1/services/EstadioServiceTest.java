package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Transactional
@Import(EstadioService.class)
public class EstadioServiceTest 
{
@Autowired
    private EstadioService estadioService;

    @Autowired
    private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

    private EstadioEntity estadioEntity;

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
	}

    private void insertData() {
		estadioEntity = factory.manufacturePojo(EstadioEntity.class);
        entityManager.persist(estadioEntity);
	}

    @Test
    void testCrearEstadioCorrecto() throws IllegalOperationException
    {
        estadioEntity.setNombreCiudad("Bogota");
        estadioEntity.setCapacidad(20000L);
        estadioEntity.setPrecioAlquiler(1000000L);

        EstadioEntity estadioFinal = estadioService.crearEstadio(estadioEntity);
        assertNotNull(estadioFinal);

        assertEquals("Bogota", estadioFinal.getNombreCiudad());
        assertEquals(20000L, estadioFinal.getCapacidad());
        assertEquals(1000000L, estadioFinal.getPrecioAlquiler());        
    }

    @Test
    void crearConciertoConAforoNegativo()
    {
        estadioEntity.setCapacidad(-20L);
        assertThrows(IllegalOperationException.class, () -> {
            estadioService.crearEstadio(estadioEntity);
        });
    }

}
