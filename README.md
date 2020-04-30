# Business Continuity Plan Calltree
This is a Calltree based communication application to provide a quick and easy way  
to broadcast important SMS messages to contacts and also record stats such as when    
people have responded and how long it took them.

[![CircleCI](https://circleci.com/gh/TeamWicket/Twilio-BCP-CallTree.svg?style=svg)](https://circleci.com/gh/TeamWicket/Twilio-BCP-CallTree)

## Motivation
During a crisis – what do you need to do?
Inform your staff/volunteers/contacts – of what?
Account for your staff/volunteers/contacts – how are you going to find out if everyone is OK?
Respond to queries – how are you going to manage queries, what contact info will you give people?
With a manual based calling tree, you can pick up the phone and have a conversation with someone, but what happens if they haven’t got all of the information they need? You will need to call them back and the calls can become challenging and time consuming especially working your way through a big list.
At what point do we say, we need a system in place? At what point do we say a manual calling tree isn’t working anymore?
Manual versus Automated
Take a simple scenario with an incident and informing employees not to come to the office.  

Manual
You have to inform your employees. Have you got all of their phone numbers? You pick up the phone a couple of times, to get through to them which is time consuming. What happens if you have over 1000 employees – the process of contacting everyone will take even longer. Also the message may become lost in translation along the way.

Automated 
Using an automated tool, you can reach a large targeted amount of people within a short space of time, and you can send the message exactly when it is needed. Employees can be kept informed and updated of the situation as the incident plays out. You can also get a response from people to show they have recieved the message. Anyone not responding can have precious time and resource dedicated to them immediately to try and get in contact with them.

What we need to do is look at ways to communicate with people via multiple paths and with a mass broadcast. In the first cycle of communications you want to try and reach everybody. Maybe you will get to 80% of employees. The next step is to then focus on those people that didn’t respond the first time to check that everybody is OK. This focuses your attention on those people that might need help.

Conclusion
When trying to reach more than a handful of people, gathering a response and reporting back using a manual calling tree really is an old school way of doing things. In this day and age with the technologies available, utilising a notification system really is the only practical way of staying in command and controlling any issue.

## How it works

This application sends messages to a list of contacts, for example employees, to notify them quickly of critical business information. <br />
With few clicks the administrator (CHAMPION) can initiate a call tree and collect statistical information such as overall
response time between outbound and inbound sms, % of replies within X minutes, also detailed stats for each recipient.

![flow](https://i.ibb.co/H4qZ1v4/Initiation.png)

There are four key roles in this Business Continuity Plan: CHAMPION, MANAGER, LEADER, REPORTER. <br />
When the CHAMPION triggers a new event, the application will send an SMS from the top-level of the tree to all the
levels below, as shown in the below diagram.

![tree](https://i.ibb.co/kDyM1v6/role-tree.png)

The event is considered closed when the number of incoming SMS matches the outgoing SMS, or when the CHAMPION terminates it manually. <br />
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
* NPM, Node JS, Yarn

## How to use the app:

### Twilio settings
Change the default values in the file `calltree-core\src\main\resources\twilio.properties`

| Config&nbsp;Value | Description                                                                                                                                                  |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Account&nbsp;Sid  | Your primary Twilio account identifier - find this [in the Console](https://www.twilio.com/console).                                                         |
| Auth&nbsp;Token   | Used to authenticate - [just like the above, you'll find this here](https://www.twilio.com/console).                                                         |

You will need also a Twilio phone number - you can [get one here](https://www.twilio.com/console/phone-numbers/incoming).  Afterwards the Twilio phone number can be added into the application using the UI.

***All the numbers in the applications must be in [E.164 format](https://en.wikipedia.org/wiki/E.164)***

### Localhost Twilio config

Download ngrok here: https://ngrok.com/download and extract the exe
In a command shell run `ngrok http 8080`
Copy the https Forwarding parameter (not the localHost part) e.g. https://b6231be3.ngrok.io and paste this into the request URL on Twilio.  Append `/api/v1/events/twilio` to the end of the URL

![ngrok](https://i.ibb.co/pWqyvPq/ngrok.png)
![twilio-settings](https://i.ibb.co/KGBP6Pt/twilio-settings.png)



### Backend Setup
1. Clone the repo: `git clone https://github.com/TeamWicket/Twilio-BCP-CallTree.git`
2. Prepare a java executable using Maven: `mvn clean install` or using the wrapper `mvnw clean install`
3. From the command line, navigate to the .jar file at `calltree-core\target` and run it using: `java -jar calltree-core-0.0.1-SNAPSHOT.jar`

### Front end setup
1. Navigate to the `calltree-ui` repo and use the commands:
* `yarn install`
* `yarn start`

This will load the front end up in your browser
### Running application details

| Service | Address |
|:--- | :--- |
| Backend | http://localhost:8080 |
| Frontend | http://localhost:3000 |

### UI Overview

![dashboard](https://i.ibb.co/zQnwfgQ/dashboard.png)

![contacts](https://i.ibb.co/SK0799Y/contacts.png)

![create-contact](https://i.ibb.co/fD3tpxL/create-contact.png)

![number](https://i.ibb.co/ZH0CKW7/number.png)

![add-number](https://i.ibb.co/PNZtCWh/new-number.png)

![events](https://i.ibb.co/WWpDBcb/events.png)

![new-event](https://i.ibb.co/4T16zsc/new-event.png)


### PostgreSQL settings

By default the application runs off an in memory H2 database, however it is compliant with an external PostgreSQL database to ensure persistance.  This can be done by:

1.  Downloading a PostgreSQL Database here: https://www.postgresql.org/
2.  Modify the `application-live.properties` in the `calltree-core/src/main/resources/` folder
3.  Set the following parameters:
     *  `spring.datasource.url`:  set it to the url of your PostgreSQL server e.g. jdbc:postgresql://localhost:5432/postgres
     *  `spring.datasource.username`:  set this to the PostgreSQL user.  NOTE, the schema will be automatically created in this user's database
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


##Shoutouts

React-Admin - https://github.com/marmelab/react-admin

The amazing work done by https://github.com/marmelab has allowed us to build a very nice functional front end.
## License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Disclaimer

No warranty expressed or implied. Software is as is.
