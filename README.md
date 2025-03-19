# test app automation
#
## Running tests ##

Run all tests :
```bash
./gradlew clean test
```
Please note, tests configured to run in parallel on gradle task level. Currently configured for all tests, 
but can be applied for specific tests only. 

## Reporting ##
To generate and host reports locally run:
```bash
./gradlew allureReport allureServe
```

## Development tools
* [TestNG](https://testng.org) - test platform
* [AssertJ](https://assertj.github.io/doc/) - assertions library
* [Allure](https://docs.qameta.io/allure/) - reporting framework

## Found issues for Player controller: 
 * incorrect signature of createPlayer endpoint: instead of GET and query params POST with request body should be used
 * incorrect signature for getPlayer endpoint : instead of POST and request body, GET with {id} within path param should be used 
 * createPlayer endpoint returns createPlayerResponse with null values for all fields except id and name 
 * getAll endpoint returns playerItems with missing role field 
 * 