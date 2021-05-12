package com.quickbooks.profileservice.store;

import com.quickbooks.profileservice.datastore.ProfileStore;
import com.quickbooks.profileservice.entity.ProfileStatus;
import com.quickbooks.profileservice.exception.ResourceNotFoundException;
import com.quickbooks.profileservice.repository.ProfileStatusRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileStoreTest {

    ProfileStore profileStore ;

    @Mock
    ProfileStatusRepository repository;

    @Before
    public void setUp(){
        profileStore = new ProfileStore();
        profileStore.setProfileStatusRepository(repository);
    }


    @Test
    public void testFindIdByTaxIdentifier() {
        ProfileStatus status = new ProfileStatus();
        status.setTaxIdentifier("GSTIN123");
        status.setId("UIUID-UIDIDI-UIUIUD-KIUIU");
        status.setStatus("IN_PROGRESS");
        status.setAdditionalInformation("Validation in progress");
        status.setOperationType("CREATE");
        Mockito.doReturn(status).when(repository).findById(Mockito.anyString());
        ProfileStatus result= profileStore.findProfileStatusById("GSTIN123");
        Assert.assertNotNull(result);
        Assert.assertTrue(status.equals(result));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testInvalidTaxIdenfierFound() {
        Mockito.doReturn(null).when(repository).findById(Mockito.anyString());
        profileStore.findProfileStatusById("GSTIN123");
    }


}
