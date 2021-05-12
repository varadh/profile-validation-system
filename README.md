# profile-validation-system


**SYSTEM DESIGN:
**
![QB Validation System Design](https://user-images.githubusercontent.com/12950193/117947906-42789b80-b32e-11eb-89da-bf910d6c0b13.png)


**Interface Definitions:
**
#1 CREATE Profile:
HTTP POST
/profile
REQUEST:
{
	"companyName": "Reliance Industries",
	"legalName": "Reliance Industries",
	"businessAddress": {
		"address1": "1 First Street",
		"address2": "Mulund",
		"city": "Mumbai",
		"state": "Maharashtra",
		"zip": "2093402",
		"country": "INDIA"
	},
	"legalAddress": {
		"address1": "1 First Street",
		"address2": "Mulund",
		"city": "Mumbai",
		"state": "Maharashtra",
		"zip": "2093402",
		"country": "INDIA"
	},
	"pan": "ASDSD2323802",
	"email": "mail@reliance.com",
	"website": "www.reliance.com"
}

RESPONSE:
202 - Accepted 
404 - Resource not found
500 - Internal server error

#2 UPDATE PROFILE:
HTTP PUT
/profile/{id}
id here is the tax identifier. (More of this is discussed on the section 'how the flow works')
Rest of the request, response and error codes are the same as of CREATE API.

#3 FIND THE STATUS OF A VALIDATION REQUEST:
/status/{id}
HTTP GET
RESPONSE:
200 OK 
{
    "id": 2,
    "taxIdentifier": "GSTIN212",
    "operationType": "CREATE",
    "status": "IN_PROGRESS"
}

404 - Resource not found
500 - Internal server error

#4 DELETE A VALIDATION REQUEST:
HTTP DELETE
/status/{id}

RESPONSE:
200 OK 
{"message":"Deletion successful"}
404 - Resource not found
500 - Internal server error

#5 FIND A PROFILE BY ID(tax identifier)
HTTP GET
/profile/{id} (taxidentifier)

RESPONSE:
{
	"companyName": "Reliance Industries",
	"legalName": "Reliance Industries",
	"businessAddress": {
		"address1": "1 First Street",
		"address2": "Mulund",
		"city": "Mumbai",
		"state": "Maharashtra",
		"zip": "2093402",
		"country": "INDIA"
	},
	"legalAddress": {
		"address1": "1 First Street",
		"address2": "Mulund",
		"city": "Mumbai",
		"state": "Maharashtra",
		"zip": "2093402",
		"country": "INDIA"
	},
	"pan": "ASDSD2323802",
	"email": "mail@reliance.com",
	"website": "www.reliance.com"
}

Member-Subscription Service: 
HTTP GET
/subscription/{memberId}

RESPONSE:
200 OK - 
{
  "products":[ 
	{
        "productId":"238023480",
        "product_name":"QB Accounts",
        "validation_API":"https://quickbooks.accounts.com/validate/",
	"type":"GET"
	},
    {
        "productId":"09203840923",
        "product_name":"QB PayRoll",
        "validation_API":"https://quickbooks.payroll.com/validate/",
	"type":"GET"
	}
   ]
}

404 - Resource Not found
500 - Internal server error


Interfaces:
public interface iDataPublisher {
    public void init();
    public void publish(String messageId, String message);
}


public interface iProfileStore {
    public ProfileStatus findProfileStatusByTaxIdentifier(String taxIdentifier);
    public void deleteMessageByTaxIdentifier(String taxIdentifier);
    public Profile findProfileByTaxIdentifier(String taxIdentifier);
}

public interface iDataConsumer {
	public void init();
	public Object consume();
}




**HOW THE FLOW WORKS?**
#1 Create Profile Flow:
Business Profile Service:
a. client makes a call to /profile POST REST API, with the authentication token and the request payload
b. The api accepts the request, generates an unique id, publishes the data to the queue 
c. Returns 202 as the response code
Business Profile Processing Service:
a. the service consumes the data from the queue.
b. creates an entry in the profile_status table for the message with status "IN_PROGRESS"
c. uses the /subscription GET API to get the active subscriptions for the member
d. Iterates through the list of products
	a. hits the validation api 
	b. gets the result
e. Incase if all the product calls are successful, then the record gets updated as 'SUCCESS'
f. Incase of any service errors, the status is marked as 'REJECTED' with a reason
//TODO - expand further on this about circuit breaker
g. client can use /status GET Api to find out the status of the request. Should pass the uniqueId which /profile POST API generated as a parameter to the GET API.
h. Assumption - user can make only one profile change at a time. 
i. Until the existing request reaches a state of SUCCESS/REJECTED, no new profile modification can be done.

#2 Update Profile Flow:

a. client makes a call to /profile PUT REST API, with the authentication token and the request payload
b. The api accepts the request, generates an unique id, publishes the data to the queue 
c. Returns 202 as the response code
Business Profile Processing Service:
a. the service consumes the data from the queue.
b. check if there is an entry for the user already "IN_PROGRESS" or not.
c. if an entry is present, then it creates an entry for this message with the status "REJECTED" & additional info as "REQUEST ALREADY INPROGRESS"
d. If not present, then delete the entry for the taxIdentifier from the cache
e. creates an entry in the profile_status table for the message with status "IN_PROGRESS"
f. uses the /subscription GET API to get the active subscriptions for the member
g. Iterates through the list of products
	a. hits the validation api 
	b. gets the result
h. Incase if all the product calls are successful, then the record gets updated as 'SUCCESS'
i. Incase of any service errors, the status is marked as 'REJECTED' with a reason

	
#3 View the Status of a create/update request
a. client makes a call to /status GET API hosted as part of the business profile service
b. receives the profile model as a json with 200 ok
c. 404 incase if the id is not found
d. 500 for server errors

#4 Find the profile
a. client makes a call to /profile/{taxidentifier} GET API hosted as part of business profile service
b. BP service makes a call to cache. 
c. Cache store the taxIdentifier as key, profile object as value.
c. If taxIdentifier is present in cache, then return the profile.
d. If not present, then read from the database
e. insert into the cache 
f. return the profile





