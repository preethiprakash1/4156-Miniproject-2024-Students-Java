# README

## Bug Fixing
For bug fixing, the static bug finder PMD was used. The following command was run:

pmd check -d Course.java, Department.java, MyFileDatabase.java, IndividualProjectApplication.java, RouteController.java -R rulesets/java/quickstart.xml -f text

All bugs found by PMD and manually are now fixed for Course.java, Department.java, MyFileDatabase.java, IndividualProjectApplication.java, and RouteController.java.

## Style Checking
mvn checkstyle:check command was used to fix style issues. There are no more violations or warnings.

## Test Coverage
mvn clean test jacoco:report was used to run all the tests and generate the JaCoCo report. The report shows that the branch coverage is 83%, which passes the threshold for this assignment.