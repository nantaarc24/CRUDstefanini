
import com.CRUDstefanini.Controller.PersonaController;
import com.CRUDstefanini.Entity.PersonaEntity;
import com.CRUDstefanini.Service.PersonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PersonaControllerTest {

    @Mock
    private PersonaService personaService;

    @InjectMocks
    private PersonaController personaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personaController).build();
    }

    @Test
    public void testGetPersonas() throws Exception {
        PersonaEntity persona1 = new PersonaEntity(1L, "Juan", "Perez", "1990-01-01", 12345678);
        PersonaEntity persona2 = new PersonaEntity(2L, "Ana", "Lopez", "1992-02-02", 87654321);
        when(personaService.getAllPersonas()).thenReturn(Arrays.asList(persona1, persona2));

        mockMvc.perform(get("/personas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("Ana"));
    }

    @Test
    public void testGetPersonaById() throws Exception {
        Long id = 1L;
        PersonaEntity persona = new PersonaEntity(id, "Juan", "Perez", "1990-01-01", 12345678);
        when(personaService.getPersonaById(id)).thenReturn(Optional.of(persona));

        mockMvc.perform(get("/personas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testCreatePersona() throws Exception {
        PersonaEntity persona = new PersonaEntity(null, "Juan", "Perez", "1990-01-01", 12345678);
        when(personaService.savePersona(persona)).thenReturn(persona);

        mockMvc.perform(post("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"nombre\": \"Juan\", \"apellido\": \"Perez\", \"fechaNac\": \"1990-01-01\", \"dni\": 12345678 }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testUpdatePersona() throws Exception {
        Long id = 2L;
        PersonaEntity personaOriginal = new PersonaEntity(id, "Juan", "Perez", "1990-01-01", 12345678);

        // Creamos una segunda persona con el mismo id
        PersonaEntity personaModificada = new PersonaEntity(id, "Juan Modificado", "Perez Modificado", "1990-01-01", 12345678);

        // Configuramos el mock del servicio PersonaService
        when(personaService.updatePersona(eq(id), any(PersonaEntity.class))).thenReturn(personaOriginal);

        // Aquí simulamos que el servicio retorna una persona diferente si el id es 2L
       // when(personaService.updatePersona(eq(2L), any(PersonaEntity.class))).thenReturn(personaModificada);

        MvcResult result = mockMvc.perform(put("/personas/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{ \"nombre\": \"Juan\", \"apellido\": \"Perez\", \"fechaNac\": \"1990-01-01\", \"dni\": 12345678 }"))
                .andReturn();

        // Asegurémonos de que se realizó la solicitud con éxito (código de estado 200)
        assertEquals(200, result.getResponse().getStatus());

        // Imprimimos la respuesta y la solicitud
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getRequest().getContentAsString());
    }

    @Test
    public void testDeletePersona() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/personas/{id}", id))
                .andExpect(status().isNoContent());

        verify(personaService).deletePersona(id);
    }
}
