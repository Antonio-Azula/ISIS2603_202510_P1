package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest 
{
    @Autowired
    private ConciertoService conciertoService;

    @Autowired
    private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

    private ConciertoEntity conciertoEntity;

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
	}

    private void insertData() {
		conciertoEntity = factory.manufacturePojo(ConciertoEntity.class);
        entityManager.persist(conciertoEntity);
	}

    @Test
    void testCrearConciertoCorrecto() throws IllegalOperationException
    {
        conciertoEntity.setFecha(LocalDateTime.now().plusDays(40));
        conciertoEntity.setAforo(20L);
        conciertoEntity.setPresupuesto(1000000L);

        ConciertoEntity conciertoFinal = conciertoService.crearConcierto(conciertoEntity);
        assertNotNull(conciertoFinal);

        assertEquals(40, Duration.between(LocalDateTime.now(), conciertoFinal.getFecha()).toDays());
        assertEquals(20L, conciertoFinal.getAforo());
        assertEquals(1000000L, conciertoFinal.getPresupuesto());        
    }

    @Test
    void crearConciertoConAforoNegativo()
    {
        conciertoEntity.setAforo(-20L);
        assertThrows(IllegalOperationException.class, () -> {
            conciertoService.crearConcierto(conciertoEntity);
        });
    }


}
