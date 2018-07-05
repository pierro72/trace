package com.ex.trace.repository;

import com.ex.trace.AuthorityType;
import com.ex.trace.domaine.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by stephan on 20.03.16.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthorityType(AuthorityType authority);


}
