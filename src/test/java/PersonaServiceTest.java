
import com.CRUDstefanini.Entity.PersonaEntity;
import com.CRUDstefanini.Repository.PersonaRepository;
import com.CRUDstefanini.Service.PersonaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PersonaService personaService;

    @Test
    public void testGetAllPersonas() {
        PersonaEntity persona1 = new PersonaEntity(1L, "Juan", "Perez", "1990-01-01", 1234567867);
        PersonaEntity persona2 = new PersonaEntity(2L, "Ana", "Lopez", "1992-02-02", 87654321);
        when(personaRepository.findAll()).thenReturn(Arrays.asList(persona1, persona2));

        List<PersonaEntity> personas = personaService.getAllPersonas();
        assertEquals(2, personas.size());
        verify(personaRepository, times(1)).findAll();
    }

    @Test
    public void testGetPersonaById() {
        PersonaEntity persona = new PersonaEntity(1L, "Juan", "Perez", "1990-01-01", 12345678);
        when(personaRepository.findById(1L)).thenReturn(Optional.of(persona));

        Optional<PersonaEntity> foundPersona = personaService.getPersonaById(1L);
        assertTrue(foundPersona.isPresent());
        assertEquals(1L, foundPersona.get().getIdPersona());
        verify(personaRepository, times(1)).findById(1L);
    }

    @Test
    public void testSavePersona() {
        PersonaEntity persona = new PersonaEntity(1L, "Juan", "Perez", "1990-01-01", 12345678);
        PersonaEntity persona2 = new PersonaEntity(2L, "Ana", "Lopez", "1992-02-02", 87654321);
        when(personaRepository.save(persona)).thenReturn(persona);

        PersonaEntity savedPersona = personaService.savePersona(persona);
        assertEquals(persona.getNombre(), savedPersona.getNombre());
        verify(personaRepository, times(1)).save(persona);
    }

    @Test
    public void testDeletePersona() {
        Long id = 5L;
        personaService.deletePersona(id);
        verify(personaRepository, times(1)).deleteById(id);
    }
}
