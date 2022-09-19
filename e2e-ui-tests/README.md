# Backbase-Automated-Tests

## Run E2E UI tests in Local

Prerequisites : Node.js 12 or 14 and above

1. Clone repository to your local

2. Navigate to backbase-tests/e2e-ui-tests in terminal

3. Run "npm install"


#### Run tests in headless mode with Chrome

npx cypress run --browser chrome

  
In the above command you can also change the browser you want to run the tests in with headless mode.

Ex:

* --browser firefox

* --browser edge

* --browser chromium


##### Reports :

After tests are executed, report can be found in "reports/html/index.html"


#### Run with cypress UI

npx cypress open

  
#### html report :

<img width="1650" alt="image" src="https://user-images.githubusercontent.com/32265029/191074688-a0f2a0b4-6d49-45b0-b772-42572024d3d6.png">


## Run E2E UI tests in CI/CD pipeline

Github actions workflow is configured to trigger once a change is pushed in to 'main' branch.

You can find the workflow setup file here :
 https://github.com/TheNiron/backbase-tests/blob/main/.github/workflows/ui-test-workflow.yml

Workflow excution of UI tests: 
<img width="1692" alt="image" src="https://user-images.githubusercontent.com/32265029/191075269-84a4c752-17dd-4311-ad0d-293f67c8ac04.png">