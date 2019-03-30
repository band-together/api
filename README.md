# Band API

All the reads

All code should have JavaDoc, unit tests.


To start your Docker container with a mysql database, do the following:
1. Install and start Docker-  https://docs.docker.com/install/
2. Copy application.properties.example to an application.properties file in the same directory
3. Replace the password <Replace Me!> in both Dockerfile and the new application.properties file with any secure password of your choosing... or don't, it's just a dev environment.
4. Run these commands from the shell in the base api directory
    - docker build -t mysql .
    - docker run --detach --name=mysql -p 13306:3306 mysql
5. Verify by running:
    - docker exec -it mysql /bin/bash
    - mysql -u sqluser -p
    - show databases;
    - You should see 2 databases, 1 of which is 'band'