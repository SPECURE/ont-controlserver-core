**Nettest control server core**

Nettest open-source library released under the Apache License, Version 2.0.

Core library for control server to store/process measurements and communicate with control servers and clients.

It's possible to use this library in 2 ways. Like a part of some application or like a separate Java app:

**Installation:**

 To use it as a library compile and add to maven:

     <dependency>
        <groupId>com.specure</groupId>
        <artifactId>core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
     </dependency>

To run it as an app:

1. Add SpringBoot runners to pom.xml
2. Add <APP_NAME>Application.jar file to com.specure.core folder
3. Fill out the application.yaml file.



