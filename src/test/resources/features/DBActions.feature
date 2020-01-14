Feature: test database 1
  As a qa
  I want check
  So that db data consistent

Scenario: 1 select *

Given execute query:
"""
select * from salarydetails where EmpID='24356A'
"""
Then [softcheck] number of lines is more than 0
  And [softcheck] number of lines is more than 1
  And [softcheck] number of lines is more than 0
  And [softcheck] number of lines is more than 2
  And [softcheck] number of lines is more than 3
  Then check soft validation

Scenario: 2 select *

Given execute query:
"""
select * from salarydetails
"""
Then number of lines is more than 1

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
