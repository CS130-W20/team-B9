# Limelight

> Limelight is a web app that features one user at a time. Being “featured” means the user livestreams him/herself to all other users currently on the website. 

[![Build Status](https://travis-ci.org/CS130-W20/team-B9.svg?branch=master)](https://travis-ci.org/CS130-W20/team-B9)

## Directory Structure
- client: contains all frontend code
- src: contains all backend code and tests
  - main:
    - java/com/limelight/server: contains all backend Java classes
    - resources: contains application.properties file, which contains application-specific values
  - test/java/com/limelight/test:
    - contains all backend JUnit tests
 - docs: contains all project documentation, mostly in JavaDoc format

## Installation/Run instructions
You can access Limelight online [here](http://limelight-app.now.sh/). Alteratively, if you wish to run Limelight locally, please follow the instructions below.

### Backend
After cloning the Limelight repository, navigate to the project directory and execute `./gradlew bootRun`. The server should be running in a few seconds, and it can then be accessed at localhost:8080.

### Frontend
After cloning the limelight repository, navigate to the client subdirectory and execute `npm start`. The frontend will automatically open in a new tab at localhost:3000.

## Relevant Links 
- Documentation link: All documentation can be found [here](https://github.com/CS130-W20/team-B9/tree/master/docs), mostly in JavaDoc format.
- Working URL: Join the Limelight [here](http://limelight-app.now.sh/)!
- Video demo: Watch a video demo [here](https://youtu.be/wANkrY_gR0A)!

