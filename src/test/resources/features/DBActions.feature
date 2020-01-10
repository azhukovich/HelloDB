Feature: test database 1
  As a qa
  I want check
  So that db data consistent

Scenario: 1 select *

Given execute query:
"""
select * from salarydetails where EmpID='24356A'
"""
Then number of lines is more than 0

Scenario: 2 select *

Given execute query:
"""
select * from salarydetails where EmpID='24356A'
"""
Then number of lines is more than 1

  Scenario: 3 select *

    Given execute query:
"""
select * from salarydetails where EmpID='24356A'
"""
    Then number of lines is more than 3
