package com.example.emailnotify.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
    Optional<AppUser> findByEmailid(String email);
    

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.emailid = ?1")
    int enableAppUser(String email);
}


