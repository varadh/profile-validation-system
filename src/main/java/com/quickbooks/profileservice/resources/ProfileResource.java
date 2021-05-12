package com.quickbooks.profileservice.resources;

import com.google.gson.Gson;
import com.quickbooks.profileservice.datastore.iProfileStore;
import com.quickbooks.profileservice.entity.ProfileStatus;
import com.quickbooks.profileservice.model.Message;
import com.quickbooks.profileservice.model.OperationType;
import com.quickbooks.profileservice.model.Profile;
import com.quickbooks.profileservice.producer.iDataPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;


@RestController
public class ProfileResource {

    Logger logger = LoggerFactory.getLogger(ProfileResource.class);

    @Resource(name="in-memory-queue-publisher")
    iDataPublisher dataPublisher;

    @Autowired
    private Gson gson;

    @Resource(name="h2db-profile-store")
    iProfileStore profileService;

    @PostMapping(path="/profile", consumes="application/json")
    public ResponseEntity<Object> create(@RequestBody Profile profile){
        logger.info("Request received for profile create. Company name is, {}", profile.getCompanyName());
        ResponseEntity<Object> response = publishMessage(profile, OperationType.CREATE);
        logger.info("Request acceded for profile create. Company name is, {}", profile.getCompanyName());
        return response;
    }

    @PutMapping(path="/profile/{id}", consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody Profile profile, @PathVariable String id){
        logger.info("Request received for profile update. Company name is, {}", profile.getCompanyName());
        ResponseEntity<Object> response = publishMessage(profile, OperationType.UPDATE);
        logger.info("Request acceded for profile update. Company name is, {}", profile.getCompanyName());
        return response;
    }

    @GetMapping(path="/status/{id}")
    public ResponseEntity<Object> getProfileStatusById(@PathVariable String id){
        ProfileStatus request = profileService.findProfileStatusById(id);
        if(request==null){
            String errorMsg = "Requested resource:"+id+" is not found";
            return ResponseEntity.ok().body(Collections.singletonMap("message", errorMsg));
        }else{
            return ResponseEntity.ok(gson.toJson(request));
        }
    }

    @DeleteMapping(path="/status/{taxIdentifier}")
    public ResponseEntity<Object> deleteChangeRequest(@PathVariable String taxIdentifier) throws RuntimeException{
        profileService.deleteMessageByTaxIdentifier(taxIdentifier);
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Deletion successful"));
    }

    @GetMapping(path="/profile/{taxIdentifier}")
    public ResponseEntity<Object> getProfileByTaxIdentifier(@PathVariable String taxIdentifier){
        Profile profile = profileService.findProfileByTaxIdentifier(taxIdentifier);
        return ResponseEntity.ok().body(gson.toJson(profile));
    }


    private ResponseEntity<Object> publishMessage(Profile profile, OperationType operationType){
        Message message = new Message();
        message.setMessageId(UUID.randomUUID().toString());
        message.setProfile(profile);
        message.setOperationType(OperationType.CREATE);
        dataPublisher.publish(message.getMessageId(), gson.toJson(message));
        return ResponseEntity.accepted().body(Collections.singletonMap("id", message.getMessageId()));
    }

}
