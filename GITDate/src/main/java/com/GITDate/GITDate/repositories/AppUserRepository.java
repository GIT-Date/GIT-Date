package com.GITDate.GITDate.repositories;

import com.GITDate.GITDate.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository <AppUser, Long> {
    public AppUser findByUsername(String username);
}
