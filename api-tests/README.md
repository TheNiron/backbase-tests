
# Backbase-Automated-API-Tests

## Description

The API test automation framework is developed with TestNG and Rest-assured. Scope of the current automated API tests cover the following functionalities,

1. Article creation

2. Posting comments

## Run API tests in Local


Prerequisites : Java, Maven

1. Clone repository to your local

2. Navigate to backbase-tests/api-tests in terminal

3. Run "mvn clean test"

  

##### Reports :

After tests are executed, surefire-reports can be found in "target/surefire-reports/emailable-report.html"

You can also generate Allure reports by executing following command:

    allure serve 
    
#### Allure report :


<img width="1707" alt="image" src="https://user-images.githubusercontent.com/32265029/191080146-527489fe-2f70-4268-8c59-156722112d60.png">

<img width="1707" alt="image" src="https://user-images.githubusercontent.com/32265029/191080292-e559aa49-500c-4e72-be0e-4b5b0446aa86.png">

## Run API tests in CI/CD pipeline

Github actions workflow is configured to trigger once a change is pushed in to 'main' branch.
  
You can find the workflow setup file here :

https://github.com/TheNiron/backbase-tests/blob/main/.github/workflows/api-test-workflow.yml

Workflow excution of API tests:
![image](https://user-images.githubusercontent.com/32265029/191080986-2d7ce75f-cee2-4355-afcf-c72bb57f6736.png)

After the workflow is executed an allure report will be generated and deployed in to github pages. 
You can the deployed reports in here : [https://theniron.github.io/backbase-tests/](https://theniron.github.io/backbase-tests/)

![image](https://user-images.githubusercontent.com/32265029/191087219-30c9555d-08dc-48d3-8bd4-5204887e0652.png)
