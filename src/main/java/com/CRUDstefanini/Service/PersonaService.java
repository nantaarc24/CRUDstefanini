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
            return personaRepository.save(personaUpdate);
        }else{
            throw new RuntimeException("No se encontro la persona con id "+id);
        }
    }

    public void deletePersona(Long id){
        personaRepository.deleteById(id);
    }
}
