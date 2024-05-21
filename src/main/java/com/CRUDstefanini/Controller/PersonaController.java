package com.CRUDstefanini.Controller;

import com.CRUDstefanini.Entity.PersonaEntity;
import com.CRUDstefanini.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<PersonaEntity> getPersonas(){
        return personaService.getAllPersonas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaEntity> getPersona(@PathVariable(name = "id") Long id){
        Optional<PersonaEntity> personaEntity = personaService.getPersonaById(id);
        return personaEntity.map(persona -> new ResponseEntity<>(persona, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PersonaEntity> createPersona(@RequestBody PersonaEntity personaEntity){
        try {
            PersonaEntity persona = personaService.savePersona(personaEntity);
            return new ResponseEntity<>(persona, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaEntity> updatePersona(@PathVariable(name = "id") Long id, @RequestBody PersonaEntity persona){
        try {
            PersonaEntity updatePersona = personaService.updatePersona(id, persona);
            return new ResponseEntity<>(updatePersona, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable(name = "id") Long id){
        personaService.deletePersona(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
