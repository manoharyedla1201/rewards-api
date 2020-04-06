# Retail Service
This is a Rest API for processing retail customer transactions and return reward points earned per month for each customer. 
The logic for generating the reward points is as follows:

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

URL:
/retail/customerRewards

Method:
POST

Sample Request Body:

{  
   "retailCustomers":[  
      {  
         "customerId":1234567,
         "transactionList":[  
            {  
               "transactionAmount":100.00,
               "timestamp":"2019-08-17T12:34.123"
            },
            {  
               "transactionAmount":0.00,
               "timestamp":"2019-09-27T16:34.123"
            },
            {  
               "transactionAmount":150.00,
               "timestamp":"2019-12-07T08:34.123"
            }
         ]
      },
      {  
         "customerId":9876544,
         "transactionList":[  
            {  
               "transactionAmount":140.00,
               "timestamp":"2019-04-21T08:34.123"
            },
            {  
               "transactionAmount":20.00,
               "timestamp":"2019-01-07T12:34.123"
            },
            {  
               "transactionAmount":80.00,
               "timestamp":"2019-02-07T16:34.123"
            }
            
         ]
      }
   ]
}

Success Response:

Code: 200
Content: 
{
    "customerRewardsList": [
        {
            "customerId": 1234567,
            "monthlyRewards": [
                {
                    "period": "August, 2019",
                    "rewardPoints": 50.0
                },
                {
                    "period": "September, 2019",
                    "rewardPoints": 0.0
                },
                {
                    "period": "December, 2019",
                    "rewardPoints": 150.0
                }
            ],
            "totalRewards": 200.0
        },
        {
            "customerId": 9876544,
            "monthlyRewards": [
                {
                    "period": "April, 2019",
                    "rewardPoints": 130.0
                },
                {
                    "period": "January, 2019",
                    "rewardPoints": 0.0
                },
                {
                    "period": "February, 2019",
                    "rewardPoints": 30.0
                }
            ],
            "totalRewards": 160.0
        }
    ]
}

Error Response:

<Most endpoints will have many ways they can fail. From unauthorized access, to wrongful parameters etc. All of those should be liste d here. It might seem repetitive, but it helps prevent assumptions from being made where they should be.>

Code: 400 Bad Request
Content: 
{
    "httpStatus": "BAD_REQUEST",
    "messaage": " Error processing retail document - No Customer Transactions found: {} For Customer Number:1234567"
}
Sample Request Body:
{  
   "retailCustomers":[  
      {  
         "customerId":1234567,
         "transactionList":[  
            
         ]
      }
     
   ]
}

Code: 500 Internal Server Error
Content: 
{
    "httpStatus": "INTERNAL_SERVER_ERROR",
    "messaage": "java.lang.NullPointerException"
}
Sample Request Body:
{  
   "retailCustomers":[  
      {  
         "customerId":1234567,
         "transactionList":[  
            {  
               "transactionAmount":100.00
          
            },
            {  
               "transactionAmount":0.00,
               "timestamp":"2019-09-27T16:34.123"
            },
            {  
               "transactionAmount":150.00,
               "timestamp":"2019-12-07T08:34.123"
            }
         ]
      }
     
   ]
}

Notes:
This API runs on PORT 8000 which is specified in the application.properties file. 
And also we have to specify the Content-Type as application/json as part of the request headers. 
On a side note, I use POSTMAN rest client to test the API.