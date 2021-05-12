package com.quickbooks.profileservice.repository;

import com.quickbooks.profileservice.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository  extends JpaRepository<Profile, String> {
    public Profile findByTaxIdentifier(String taxIdentifier);
}
