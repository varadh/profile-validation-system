package com.quickbooks.profileservice.datastore;

import com.quickbooks.profileservice.entity.ProfileStatus;
import com.quickbooks.profileservice.model.Profile;

public interface iProfileStore {
    public ProfileStatus findProfileStatusById(String id);
    public void deleteMessageByTaxIdentifier(String taxIdentifier);
    public Profile findProfileByTaxIdentifier(String taxIdentifier);
}
