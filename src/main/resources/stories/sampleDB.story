
Scenario: 1 select *

Given execute query select * from salarydetails where EmpID='24356A'
Then number of lines is more than 0

Scenario: 2 select *

Given execute query select * from salarydetails where EmpID='24356A'
Then number of lines is more than 1

Scenario: 3 select count(*)

Given execute query select count(*) from salarydetails where EmpID='24356A'
Then count is more than 0

Scenario: 4 select count(*)

Given execute query select count(*) from salarydetails where EmpID='24356A'
Then count is more than 1

