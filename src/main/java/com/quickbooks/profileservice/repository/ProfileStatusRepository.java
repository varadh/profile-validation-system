package com.quickbooks.profileservice.repository;

import com.quickbooks.profileservice.entity.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileStatusRepository extends JpaRepository<ProfileStatus, String> {

    void deleteByTaxIdentifier(String messageId);

}
