Feature: test database 2
  As a qa
  I want check
  So that db data consistent

  Scenario: 3 select count(*)

Given execute query:
  """
  select count(*) from salarydetails where EmpID='24356A'
  """
    Then count is more than 0

Scenario: 4 select count(*)

Given execute query:
"""
select count(*) from salarydetails where EmpID='24356A'
"""
Then count is more than 1
