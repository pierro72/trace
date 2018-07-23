package com.ex.trace.repository;

import com.ex.trace.domaine.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Spring Data JPA repository for the Tuile entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findById(Long id);

}
