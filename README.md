
Design a profile validation system:
Requirements:

SSE -
1. Design the profile validation system (HLD)
a. Consider using the “Store and Validate” approach. Ie., Accept the request and return
the response. Do validation with all products behind the scenes asynchronously.
b. Have an API to identify which state the validation is in at any point in time.
i. Like In_Progress, Rejected, Accepted, and so on.

2. Build a spring boot microservice with REST/GraphQL API’s for CRUD operation
. Have Well-defined contract
a. Have well-defined HTTP status code and error messages.
3. Use Dynamo or equivalent database for persistence (Well thought Schema design)
4. Use the Cache layer for achieving scalability, resiliency, and performance as needed.
Aim for:
1. Loose coupling between each system that needs to validate the data.
2. Use the Spring framework as much as possible.
3. Focus on resiliency scenarios as well (And not Happy Path alone)
4. Apply design patterns as much as possible (Like Hystrix for circuit-breaker, Factory pattern,
and so on)
5. Try following TDD or have good coverage for Unit Tests.
6. As a surprise, you can also build UI experience in React to update the profile fields.
7. List out the Non-Functional areas that you would consider to deploy this application to Prod
and how do you suggest to achieve the same.

Architecture Diagram:
![QB Validation System Design](https://user-images.githubusercontent.com/12950193/117947906-42789b80-b32e-11eb-89da-bf910d6c0b13.png)

Detailed Design:
https://docs.google.com/document/d/1MRGIdlDAhCqAIUjhQA2EemaFf4L8cC8NOObImbJ_aGQ/edit
