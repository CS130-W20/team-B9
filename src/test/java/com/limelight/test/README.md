## Description of test cases:
There are 24 test cases in total, so in the interest of brevity, 5 distinguished test cases are selected and described below in more depth:

* MainControllerTest.java: testLogin()
  * This test performs a POST request to the server with an existing username and password, and verifies that the user credentials are accepted. 
  * Expected outcome: request is accepted
  * Test success indicator #1: the user’s session key is returned
  * Test success indicator #2: the returned HTTP status code is 200 (OK)
  * Test failure indicator #1: anything else is returned
  * Test failure indicator #2: any other HTTP status code is returned


* MainControllerTest.java: testEditProfile()
  * This test performs a POST request to the server with a user and their user key and attempts to edit a particular attribute of the user.
  * Expected outcome: request is accepted
  * Test success indicator #1: “true” is returned
  * Test success indicator #2: the returned HTTP status code is 200 (OK)
  * Test failure indicator #1: “false” is returned
  * Test failure indicator #2: any other HTTP status code is returned


* MainControllerTest.java: testGetUser()
  * This test performs a GET request to the server with a particular username and verifies that the given user’s information is returned.
  * Expected outcome: given user’s information is returned
  * Test success indicator #1: JSON representation of given user is returned
  * Test success indicator #2: the returned HTTP status code is 200 (OK)
  * Test failure indicator #1: null is returned
  * Test failure indicator #2: any other HTTP status code is returned

* UserRepositoryTest.java: testUserSave()
  * This test creates a temporary user and calls upon the UserRepository to persist it to the database, then verifies that it was indeed persisted successfully.
  * Expected outcome: new user is persisted successfully
  * Test success indicator #1: new user can be found in database afterwards
  * Test failure indicator #1: new user cannot be found in database afterwards

* StreamQueueTest.java: testJoin()
  * This test creates a StreamQueue and adds several users to it, then verifies that the queue is property set up. This includes adding a user that is already in the queue.
  * Expected outcome: adding user that is not in the queue succeeds, adding user that is already in the queue fails
  * Test success indicator #1: adding user that is not in the queue returns true
  * Test success indicator #2: adding user that is already in the queue returns false
  * Test failure indicator #1: adding user that is not in the queue returns false
  * Test failure indicator #2: adding user that is already in the queue returns true
