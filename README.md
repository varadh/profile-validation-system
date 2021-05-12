# profile-validation-system



Interface Definitions:
#1 CREATE Profile:
HTTP POST
/profile
REQUEST:
{
	"companyName": "",
	"legalName": "",
	"businessAddress": {
		"address1": "",
		"address2": "",
		"city": "",
		"state": "",
		"zip": "",
		"country": ""
	},
	"legalAddress": {
		"address1": "",
		"address2": "",
		"city": "",
		"state": "",
		"zip": "",
		"country": ""
	},
	"pan": "",
	"email": "",
	"website": ""
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
   product1,
   product2,
   product3
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






