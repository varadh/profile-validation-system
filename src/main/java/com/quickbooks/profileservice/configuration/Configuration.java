package com.quickbooks.profileservice.configuration;

import com.quickbooks.profileservice.datastore.ProfileStore;
import com.quickbooks.profileservice.datastore.iProfileStore;
import com.quickbooks.profileservice.entity.Address;
import com.quickbooks.profileservice.entity.Profile;
import com.quickbooks.profileservice.entity.ProfileStatus;
import com.quickbooks.profileservice.producer.InMemoryQueuePublisher;
import com.quickbooks.profileservice.producer.iDataPublisher;
import com.quickbooks.profileservice.repository.ProfileRepository;
import com.quickbooks.profileservice.repository.ProfileStatusRepository;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean(name="h2db-profile-store")
    public iProfileStore createH2ProfileStore(){
        return new ProfileStore();
    }

    @Bean(name="in-memory-queue-publisher")
    public iDataPublisher createInMemoryQueue(){
        InMemoryQueuePublisher inMemoryQueueWriter = new InMemoryQueuePublisher();
        inMemoryQueueWriter.init();
        return inMemoryQueueWriter;
    }

    @Bean(name="in-memory-queue")
    public BlockingQueue createQueue(){
        return new LinkedBlockingDeque<>(15);
    }

    @Bean(name="testdata")
    public Profile createTestData(ProfileRepository repository, ProfileStatusRepository profileStatusRepository){
        Profile p = new Profile();
        p.setTaxIdentifier("GSTIN212");
        p.setCompanyName("Reliance Jio Store");
        Address address = new Address();
        address.setAddress1("1 first street");
        address.setAddress2("Mulund");
        address.setCity("Mumbai");
        address.setState("Maharashtra");
        address.setCountry("INDIA");
        address.setZip("230948");
        p.setBusinessAddress(address);
        p.setLegalAddress(address);
        p.setEmail("mail@reliance.com");
        p.setWebsite("www.reliance.com");
        p.setLegalName("Reliance Industries");
        repository.save(p);

        ProfileStatus profileStatus = new ProfileStatus();
        profileStatus.setId(UUID.randomUUID().toString());
        profileStatus.setTaxIdentifier("GSTIN212");
        profileStatus.setOperationType("CREATE");
        profileStatus.setStatus("IN_PROGRESS");
        profileStatusRepository.save(profileStatus);

        return p;
    }

}
