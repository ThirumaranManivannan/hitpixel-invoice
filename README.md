Payment Invoice Service

Overview:

	This service will be used to receive transactions from the internal system and create invoice respectively for each client.

Services Offered:
CRUD(create, update, list, delete) Client.
Send transactions. (No cruds provided refer transaction section further)
Generate invoice(API, email)

Note:
API’s are created based on the documents shared
I noticed all the request body json have string values, so created request object accordingly
API will be accessible inside UAE only.
Added screenshots and json to show some samples. 

Models:
Client
TransactionRecord
Invoice

Models Relationship:
Client - One to Many with TransactionRecord
Client - One to Many with Invoice
TransactionRecord - Many to One with Client
Invoice - Many to One with Client
Invoice - One to Many with TransactionRecord

Models ID Strategy:
	All Models ID’s are strings, created by uuid2 strategy.

How to run the application/service

Run the below command
​​java -Dspring.profiles.active=prod -Dspring.datasource.url=jdbc:mysql://localhost:3306/invoice_test -Dspring.datasource.username=username -Dspring.datasource.password=password -jar payment-gateway-invoice-service-0.0.1-SNAPSHOT.jar

Replace Highlighted values with your actual values. Provide a valid jdbc url with database and username and password.

Jar has been attached with this email. 
Application will run on 8765 Port. 
Access them via http://localhost:8765 
Due to time constraints I provided high level use cases to try to check api with multiple validation error scenarios to see the response. 

Client:

	Client is the customer of HitPixel who is using the payment gateway. They have status active or disabled. Client Model has been created on the service to manage clients information.

Transactions:
	
	The Transaction Model has been created to manage transactions. No crud operation provided. The /send API will handle all the transaction status for existing clients. For example if orderid = “1234” sent with approval status then it will be approved and added to the client. On the next call you can only do a refund by sending the same orderid. This is a valid scenario. Because you can’t delete an approved transaction. Only you can change the status of it. If a transaction is declined you are not allowed to make any modifications into it. 

Transactions won’t be accepted for disabled clients. 

Invoice:
	
	Invoice model has been created to create invoice for clients. When the first transaction is received for a particular client the invoice has been created. And subsequent transactions will be added to the current invoice to avoid calculating all at once. For example if a client has more than million transactions then calculation at the end will consume more time and resources. 


API Details:

	API’s are versioned, current API’s are v1, so prefix v1 in all API’s. 

Client: 

Get Client
This APi will provide client details for the given client id
Uri = /v1/client/get/{{clientId}}
Request Method = GET

Response:
	If a client exists, returns 200 and client.
	If not exist then 204 will be returned

List All Clients
This API will list all the available clients in the system. It is paginated pass query parameters ?pageNo=0&pageSize=1  and you can send sortBy as well.
Uri = /v1/client/list
Request Method = GET
Response:
	Returns List of clients and 200
	No clients returns 204



With Pagination



Sort by client name



add
Uri = /v1/client/add
Request Method = POST
Request Body: 
{
   "client": "Test Store",
   "status": "active",
   "billing-interval": "daily",
   "email":"maran786347@gmail.com",
   "fees-type": "flat-fee",
   "fees": "2"
}

Response:
	{
   "status": true,
   "clientId": "36359115-e15e-4075-a2d1-5143b834f742",
   "message": "CREATED"
}

200 If successful otherwise status will be false and 500
If there are any validation failure validation response thrown with 400

Validation error:
	{
   "error_message": "validation failed for request data. Please check below error details.",
   "error_details": {
       "status": "status is mandatory or should be active or disabled"
   },
   "error_code": 4000
}

Update
This api used to update existing client information
Uri = /v1/client/update
Id param mandatory
Request Body
{
   "id": "36359115-e15e-4075-a2d1-5143b834f742",
   "client": "Test updated Store",
   "status": "active",
   "billing-interval": "daily",
   "email":"maran786347@gmail.com",
   "fees-type": "flat-fee",
   "fees": "2"
}


		Response
			200 with status true
			{
   "status": true,
   "clientId": "36359115-e15e-4075-a2d1-5143b834f742",
   "message": "UPDATED"
}
	
	Validation error response will be thrown if any field error

Delete
This API is used to delete existing client
Uri = /v1/client/delete/{{clientId}}
Pass client id in url
Request Method = DELETE



Response 200
{
   "status": true,
   "message": "success"
}

If client not exist 500

{
   "status": false,
   "message": "client does not exist"
}

Get Transactions of a client
This api will list transactions of the given client
Uri = /v1/client/{{clientId}}/transactions
Request Method = GET

Response 200


If client not exist

{
   "status": false,
   "message": "client not available",
   "client": null,
   "transactions": null
}


Transaction
Send
This API will handle and accept transactions and based on the status it will handle accordingly. Client name should match with existing client. 
Uri = /v1/txn/send
Request Method = POST
Request Body
	{
   "orderid": "12363",
   "datetime": "09-July-2022 12:30 pm",
   "ordername": "Cheese Pizza",
   "amount": "10",
   "currency": "AED",
   "cardtype": "visa",
   "status": "approved",
   "client": "Maran Store"
}

Response 200
	{
   "status": true,
   "invoiceTxnId": "993f9aed-a8c0-4520-9b27-5ff78b79c64e",
   "orderId": "12363",
   "message": "created",
   "description": "transaction approved"
}

You will get internal txnId and status true it will be added to the respective client. 

Refund the same above txn
Request body
	{
   "orderid": "12363",
   "datetime": "09-July-2022 12:30 pm",
   "ordername": "Cheese Pizza",
   "amount": "10",
   "currency": "AED",
   "cardtype": "visa",
   "status": "refunded",
   "client": "Maran Store"
}

Response
{
   "status": true,
   "invoiceTxnId": "993f9aed-a8c0-4520-9b27-5ff78b79c64e",
   "orderId": "12363",
   "message": "created",
   "description": "refund success"
}

If you try to use same orderid for again consider following scenario’s:
If a transaction is already approved you can’t approve it again. You will receive respective error message
{
   "status": false,
   "invoiceTxnId": "cfa5e46f-3c6a-40b6-9566-dae01404be60",
   "orderId": "12364",
   "message": "failed",
   "description": "could not approve already processed order, only you can request for refund if it is not billed and if approved txn."
}
			
If the transaction is approved then you can only do a refund for that transaction.
If the transaction declined you can’t do any status update for this transaction.

Invoice
Generate for a client
This api will be used to generate invoices for a given client. If email enabled email will be sent in Asynchronous. 
Uri = /v1/invoice/generate/{{clientId}}
ClientId mandatory
Response 200

If already Generated
{
   "status": false,
   "message": "please make atleast one transaction",
   "invoice": null
}

You can other get invoice by invoiceId API to get the latest invoice. But this API generates only one time.

Generate For All
This API will be used to generate invoice for all clients. If any client doesn’t have any transaction it won’t be added to the list.
Uri = /v1/invoice/generate
Request Method = POST
Response 200 List of invoices with client information.

Get Invoice
This API used to get invoice by using invoice id
Uri = /v1/invoice/get/{{invoiceId}}
Request Method = GET
Response 200 with invoice


Full Response
{
   "id": "da593658-d11a-46bf-bee1-ea7d07330b53",
   "totalTransactions": 3,
   "approvedTransactions": 2,
   "declinedTransactions": 0,
   "refundedTransactions": 1,
   "totalAmount": 1.0,
   "approvedAmount": 2.0,
   "refundedAmount": 1.0,
   "client": {
       "id": "0aa3a590-0a11-437f-b875-8d65ceb2f597",
       "client": "Maran Store",
       "status": "ACTIVE",
       "billingInterval": "DAILY",
       "email": "maran786347@gmail.com",
       "feesType": "FLAT_FEE",
       "fees": "1",
       "transactions": [],
       "invoices": [],
       "currentInvoiceId": "da593658-d11a-46bf-bee1-ea7d07330b53"
   },
   "transactions": [
       {
           "id": "993f9aed-a8c0-4520-9b27-5ff78b79c64e",
           "orderId": "12363",
           "dateTime": "09-July-2022 12:30 pm",
           "orderName": "Cheese Pizza",
           "amount": "10",
           "currency": "AED",
           "cardType": "VISA",
           "transactionStatus": "REFUNDED"
       },
       {
           "id": "cfa5e46f-3c6a-40b6-9566-dae01404be60",
           "orderId": "12364",
           "dateTime": "09-July-2022 12:30 pm",
           "orderName": "Cheese Pizza",
           "amount": "10",
           "currency": "AED",
           "cardType": "VISA",
           "transactionStatus": "APPROVED"
       }
   ],
   "generated": true
}

