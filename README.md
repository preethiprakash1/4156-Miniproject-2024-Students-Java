# README

## Bug Fixing
For bug fixing, the static bug finder PMD was used. The following commands were run:
- pmd check -d Course.java -R rulesets/java/quickstart.xml -f text
- pmd check -d Department.java -R rulesets/java/quickstart.xml -f text
- pmd check -d IndividualProjectApplication.java -R rulesets/java/quickstart.xml -f text
- pmd check -d MyFileDatabase.java -R rulesets/java/quickstart.xml -f text
- pmd check -d RouteController.java -R rulesets/java/quickstart.xml -f text

And it was ensured that all bugs found by PMD and manually are now fixed.

## Style Checking
mvn checkstyle:check command was used to fix style issues. There are no more violations or warnings.

## Test Coverage
mvn clean test jacoco:report was used to run all the tests and generate the JaCoCo report. The report shows that the branch coverage is 83%, which passes the threshold for this assignment.