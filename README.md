# BCP Calltree

[![CircleCI](https://circleci.com/gh/TeamWicket/Twilio-BCP-CallTree.svg?style=svg)](https://circleci.com/gh/TeamWicket/Twilio-BCP-CallTree)

## How it works

This application sends messages to a list of contacts, for example employees, to notify them quickly of critical business information. <br />
With few clicks the administrator (CHAMPION) can initiate a call tree and collect statistical information such as overall
response time between outbound and inbound sms, % of replies within X minutes, also detailed stats for each recipient.

![flow](https://i.ibb.co/H4qZ1v4/Initiation.png)

There are four key roles in this Business Continuity Plan: CHAMPION, MANAGER, LEADER, REPORTER. <br />
When the CHAMPION triggers a new event, the application will send SMS from the top-level of the tree to all the
leaves below, as shown in the below diagram.

![tree](https://i.ibb.co/kDyM1v6/role-tree.png)

The event is considered close when the number of incoming SMS matches the outgoing SMS, or when the CHAMPION terminates it manually. <br />
The system collects data throughout the active event(s) for statistical purposes. It is possible to have a general overview
of a particular event with the following information:
* response time average (in minutes) of all received SMS
* total number of outgoing SMS
* total number of incoming SMS
* percentage of replies within X minutes (defined by the CHAMPION)

## Features
- Backend in Java and Kotlin
- Spring Boot / Data JPA / WEB
- H2 enabled (also available for integration tests)
- PostgreSQL ready
- Frontend in React (JavaScript / TypeScript)
- OpenAPI v3 (SpringDoc / Swagger)
- Maven building tool
- CircleCI for continuous integration

## Requirements
* A Twilio account - [sign up](https://www.twilio.com/try-twilio)
* Java 11 or higher
* Kotlin 1.3.50 or higher
* Maven 3.5 or higher
* [TODO frontend]

## How to use it

1. Clone the repo: `git clone https://github.com/TeamWicket/Twilio-BCP-CallTree.git`
2. Prepare a java executable using Maven: `mvn clean install` or using the wrapper `mvnw clean install`
3. From the command line, navigate to the .jar file and run it using: `java -jar calltree-core-0.0.1-SNAPSHOT.jar`
4. [TODO frontend]

| Service | Address |
|:--- | :--- |
| Backend | http://localhost:8080 |
| Frontend | http://localhost:3000 |

### Twilio settings
Change the default values in the file `twilio.properties`

| Config&nbsp;Value | Description                                                                                                                                                  |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Account&nbsp;Sid  | Your primary Twilio account identifier - find this [in the Console](https://www.twilio.com/console).                                                         |
| Auth&nbsp;Token   | Used to authenticate - [just like the above, you'll find this here](https://www.twilio.com/console).                                                         |

You will need also a Twilio phone number - you can [get one here](https://www.twilio.com/console/phone-numbers/incoming)

***All the numbers in the applications must be in [E.164 format](https://en.wikipedia.org/wiki/E.164)***
### Postgre settings

By default the application runs off an in memory H2 database, however it is compliant with an external Postgre database to ensure persistance.  This can be done by:

1.  Downloading a Postgre Database here: https://www.postgresql.org/
2.  Modify the application-live.properties in the `calltree-core/src/main/resources/` folder
3.  Set the following parameters:
     *  `spring.datasource.url`:  set it to the url of your Postgre server e.g. jdbc:postgresql://localhost:5432/postgres
     *  `spring.datasource.username`:  set this to the Postgre user.  NOTE, the schema will be automatically created in this user's database
     *  `spring.datasource.password`:  the user's password
4.  Start the application with the extra parameter `-Dspring.profiles.active=live` so the full command would be:  
   `java -jar calltree-core-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=live`

When starting, the application will check whether the schema and tables exist and if not automatically create these in the provided user's database under the schema `call_tree`. 

## Contributing

This template is open source and welcomes contributions. All contributions are subject to our [Code of Conduct](https://github.com/TeamWicket/Twilio-BCP-CallTree/blob/master/CODE_OF_CONDUCT.md).

## Contributors
[Team Wicket](https://github.com/TeamWicket/Twilio-BCP-CallTree/graphs/contributors)
* Alessandro Arosio: [Github](https://github.com/AlessandroArosio) - [LinkedIn](https://www.linkedin.com/in/alessandroarosio-uk/)
* John Hanna: [Github](https://github.com/jhanna60) - [LinkedIn](https://www.linkedin.com/in/john-hanna-87123080/)
* Malcolm Watt: [Github](https://github.com/malc54)

[Visit the project on GitHub](https://github.com/TeamWicket/Twilio-BCP-CallTree)

## License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Disclaimer

No warranty expressed or implied. Software is as is.