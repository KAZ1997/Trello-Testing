# Trello-Testing
Testing the basic features in Trello APP

### Tools and Frameworks
Java 17, IntelliJ IDE, Maven, Testng, RestAssured, Selenium, Allure, TypeSfe Config, Web Driver Manager, Lombok.

### pre requests
* you should have an email and password for Trello account. Do not use third party to log in (eg, facebook, Microsoft, ).
  Also, do not use accounts that require 2 factors authentications.
* you should have an api and token for your trello account
* make sure that allure is installed in your environment. run "allure --version" in the command line.
  If it is not installed do the following:
  1- "Invoke-Expression (New-Object system.Net.WebClient).DownloadString('https://get.scoop.sh')" in the Windows powershell.
  if you get an error, execute "Set-ExecutionPolicy RemoteSigned -Scope CurrentUser" and execute the previous command again.
  2- scoop install allure.
* Open pom.xml file and make sure that all dependencies are loaded. if not, load Maven changes
* Make sure that lombok annotations are enabled. if not you can just enable it after the first (a popup that asks you to enable the lombok annotation will appear).

### How to run
* your credentials to src/main/resources/application.conf file. [email, password, apiKey, apiToken]
* Open testng.xml file and run the Tests. (if you want to skip one of the tests you can simply comment it).
* For some test cases like create a list , you have 2 options:
  1- add a parameter of the board id.
  2-keep the parameter value null and the TestDataGenerator Class will provide a board and provide its id to the test, so the list can be created!
* Note: for regular trello accounts, the maximum number of boards that can be created is 10, so if you create more than 10 it will result in error.
  it is recommended to always keep the DeleteAllBoards test at the end of the suite to delete the created boards.

### Review the results
* you can generate the allure results report by executing the following command in the terminal "allure serve allure-results".
  Note: it may result in error after the first execution because the allure-results folder may not be detected as it will be after
  the first run. In that case, restart the IDE and run again.
* For failed UI test cases, you can find a screenshots for them in the scr folder that will be generated after the first run.