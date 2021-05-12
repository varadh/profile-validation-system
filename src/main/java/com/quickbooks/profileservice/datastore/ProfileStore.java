package com.quickbooks.profileservice.datastore;

import com.quickbooks.profileservice.entity.Profile;
import com.quickbooks.profileservice.entity.ProfileStatus;
import com.quickbooks.profileservice.exception.ResourceNotFoundException;
import com.quickbooks.profileservice.producer.InMemoryQueuePublisher;
import com.quickbooks.profileservice.repository.ProfileRepository;
import com.quickbooks.profileservice.repository.ProfileStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileStore implements iProfileStore{

    @Autowired
    ProfileStatusRepository profileStatusRepository;

    @Autowired
    ProfileRepository profileRepository;

    Logger logger = LoggerFactory.getLogger(InMemoryQueuePublisher.class);

    @Override
    public ProfileStatus findProfileStatusById(String id) {
        Optional<ProfileStatus> request = profileStatusRepository.findById(id);
        logger.info("Profile validation request by messageId:{} retrieved successfully",id);
        if(request.isPresent()){
            return request.get();
        }else{
            String msg = "Could not find record with taxIdentifier:"+id;
            logger.error(msg);
            throw new ResourceNotFoundException(msg);
        }
    }

    @Override
    public void deleteMessageByTaxIdentifier(String taxIdentifier){
        ProfileStatus profileStatus = findProfileStatusById(taxIdentifier);
        if(profileStatus!=null){
            profileStatusRepository.deleteByTaxIdentifier(taxIdentifier);
        }else{
            String msg = "Deletion failed. Could not find record with id:"+taxIdentifier;
            logger.error(msg);
            throw new ResourceNotFoundException(msg);
        }
    }

    @Override
    public com.quickbooks.profileservice.model.Profile findProfileByTaxIdentifier(String taxIdentifier){
        Profile profileFromDB = profileRepository.findByTaxIdentifier(taxIdentifier);
        if(profileFromDB!=null){
            com.quickbooks.profileservice.model.Profile profileModel = new com.quickbooks.profileservice.model.Profile();
            profileModel.setCompanyName(profileFromDB.getCompanyName());
            profileModel.setLegalName(profileFromDB.getLegalName());
            profileModel.setTaxIdentifier(profileModel.getTaxIdentifier());
            profileModel.setLegalAddress(profileModel.getLegalAddress());
            profileModel.setBusinessAddress(profileModel.getBusinessAddress());
            profileModel.setEmail(profileModel.getEmail());
            profileModel.setWebsite(profileModel.getWebsite());
            return profileModel;
        }else{
            String msg = "Deletion failed. Could not find record with id:"+taxIdentifier;
            logger.error(msg);
            throw new ResourceNotFoundException(msg);
        }
    }

    public void setProfileStatusRepository(ProfileStatusRepository profileStatusRepository){
        this.profileStatusRepository = profileStatusRepository;
    }
}
