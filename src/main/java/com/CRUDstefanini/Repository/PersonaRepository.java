package com.CRUDstefanini.Repository;

import com.CRUDstefanini.Entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity,Long> {
}
