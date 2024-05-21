package com.CRUDstefanini.Service;

import com.CRUDstefanini.Entity.PersonaEntity;
import com.CRUDstefanini.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<PersonaEntity> getAllPersonas(){
        return personaRepository.findAll();
    }

    public Optional<PersonaEntity> getPersonaById(Long id){
        return personaRepository.findById(id);
    }

    public PersonaEntity savePersona(PersonaEntity persona){

        validatePersona(persona);
        return personaRepository.save(persona);
    }

    public PersonaEntity updatePersona(Long id, PersonaEntity persona){
        Optional<PersonaEntity> personaEntity = personaRepository.findById(id);
        if (personaEntity.isPresent()){
            PersonaEntity personaUpdate = personaEntity.get();
            personaUpdate.setNombre(persona.getNombre());
            personaUpdate.setApellido(persona.getApellido());
            personaUpdate.setFechaNac(persona.getFechaNac());
            personaUpdate.setDni(persona.getDni());

            validatePersona(personaUpdate);
            return personaRepository.save(personaUpdate);
        }else{
            throw new RuntimeException("No se encontro la persona con id "+id);
        }
    }

    public void deletePersona(Long id){
        personaRepository.deleteById(id);
    }

    private void validatePersona(PersonaEntity persona) {
        if (persona.getNombre() == null || persona.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío");
        }
        if (persona.getApellido() == null || persona.getApellido().isEmpty()) {
            throw new RuntimeException("El apellido no puede estar vacío");
        }
        if (persona.getFechaNac() == null || persona.getFechaNac().isEmpty()) {
            throw new RuntimeException("La fecha de nacimiento no puede estar vacía");
        }
        if (!persona.getFechaNac().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new RuntimeException("La fecha de nacimiento debe tener el formato YYYY-MM-DD");
        }
        if (String.valueOf(persona.getDni()).length() != 8) {
            throw new RuntimeException("El DNI debe tener 8 dígitos");
        }
        // Validación adicional para el formato del DNI
        if (!String.valueOf(persona.getDni()).matches("^\\d{8}$")) {
            throw new RuntimeException("El DNI debe ser un número de 8 dígitos");
        }
    }

}
