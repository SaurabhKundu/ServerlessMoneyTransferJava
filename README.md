# ServerlessMoneyTransferJava
A simple Java based AWS serverless application to transfer money between accounts

## You can deploy the application directly in AWS Lambda with JAVA runtime.
[Lambda Handler](https://github.com/SaurabhKundu/ServerlessMoneyTransferJava/blob/main/src/main/java/com/mobiquity/transfer/LambdaHandler.java) 
will handle Json request as given in [MoneyTransferRequest.json](https://github.com/SaurabhKundu/ServerlessMoneyTransferJava/blob/main/MoneyTransferRequest.json)
and invoke the AWS handler function.

The lambda handler is tested locally using [unit](https://github.com/SaurabhKundu/ServerlessMoneyTransferJava/blob/main/src/test/java/com/mobiquity/transfer/function/service/DispatcherServiceTest.java) 
and [integration](https://github.com/SaurabhKundu/ServerlessMoneyTransferJava/blob/main/src/test/java/com/mobiquity/transfer/LambdaHandlerTest.java) tests. 


## Also created a Rest web service using SpringBoot

To build a docker image, do `mvn clean install -Pdocker` from root directory.
check the `target` folder and go to `docker -> image-name` to see 
the docker image name.

In the terminal, run `docker run -p 8080:8080 transfer:latest`.

Now hit the below endpoints using curl or postman

### The endpoints are:

    HTTP GET: http://localhost:8080/accounts
    HTTP GET: http://localhost:8080/accounts/{id}
    HTTP POST: http://localhost:8080/transfer