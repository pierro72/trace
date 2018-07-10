package com.ex.trace.security.repository;

import com.ex.trace.domaine.security.AuthorityType;
import com.ex.trace.domaine.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by stephan on 20.03.16.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthorityType(AuthorityType authority);


}
