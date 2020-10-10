# Business Continuity Plan Calltree
This is a Calltree based communication application to provide a quick and easy way to broadcast important  
SMS messages and record stats such as when people have responded and how long it took them.  

[![CircleCI](https://circleci.com/gh/TeamWicket/Twilio-BCP-CallTree.svg?style=svg)](https://circleci.com/gh/TeamWicket/Twilio-BCP-CallTree)

## Motivation
During a crisis – what do you need to do?  
Inform your staff/volunteers/contacts – of what?  
Account for your staff/volunteers/contacts – how are you going to find out if everyone is OK?  
Respond to queries – how are you going to manage queries, what contact info will you give people?  
With a manual based calling tree, you can pick up the phone and have a conversation with someone, but what happens if they haven’t got all of the information they need? You will need to call them back and the calls can become challenging and time consuming.  
At what point do we say, we need a system in place?  

**Manual versus Automated**
Take a simple scenario with an incident and informing employees not to come to the office.  

**Manual**  
You have to inform your employees. Have you got all of their phone numbers?  
What happens if you have over 1000 employees – the process of contacting everyone will take even longer. Also the message may become lost in translation along the way.

**Automated**  
Using an automated tool, you can reach a large amount of people within a short space of time, and you can send the message exactly when it is needed. Employees can be kept informed and updated of the situation as the incident plays out. You can also get a response from people to show they have recieved the message.  

What we need to do is look at ways to communicate with people via a mass broadcast. In the first cycle of communications you want to try and reach everybody. Maybe you will get to 70 - 80% of people. The next step is to then focus on those people that didn’t respond the first time to check that everybody is OK. This focuses your attention on those people that might need help.

**Conclusion**  
When trying to reach more than a handful of people, gathering a response and reporting back using a manual calling tree really is an old fashioned way of doing things. In this day and age with the technologies available, utilising a notification system really is the only practical way of staying in command and controlling any issue.

## How it works

This application sends a broadcast message to a list of contacts, for example employees, to notify them quickly of critical business information. <br />
With a few clicks the administrator (BCP Champion) can initiate a call tree and collect statistical information such as overall
response time between outbound and inbound sms, % of replies within X number of minutes and also detailed stats for each recipient.

![flow](https://i.ibb.co/H4qZ1v4/Initiation.png)

There are four key roles in this Business Continuity Plan application: CHAMPION, MANAGER, LEADER, REPORTER. <br />
When the CHAMPION triggers a new event, the application will send an SMS from the top-level of the tree to all the
levels below, as shown in the diagram below.

![tree](https://i.ibb.co/kDyM1v6/role-tree.png)

The event is then considered closed when everyone responds to their SMS message, or when the CHAMPION terminates it manually. <br />
The system collects data throughout the active event(s) for statistical purposes. It is possible to have a general overview
of a particular event with the following information:
* response time average (in minutes) of all received SMS messages
* total number of outgoing SMS
* total number of incoming SMS
* percentage of replies within X minutes (defined by the CHAMPION)

## Tech Stack
- Backend in Java and Kotlin
- Spring Boot / Data JPA / WEB
- H2 enabled DB (also available for integration tests)
- PostgreSQL ready application for data persistence
- Modern Frontend in React (JavaScript / TypeScript)
- OpenAPI v3 (SpringDoc / Swagger) for full REST endpoint mapping - localhost:8080/swagger-ui.html
- Maven build tool
- Full suite of Automated tests and Integration tests
- CircleCI for continuous integration

## Requirements
* A Twilio account - [sign up](https://www.twilio.com/try-twilio)
* Java 11 or higher
* Kotlin 1.3.50 or higher
* Maven 3.5 or higher
* NPM, Node JS, Yarn to run the UI

## How to use the app:

### Twilio Setup
To test out events you are going to need a Twilio phone number setup - you can [get one here](https://www.twilio.com/console/phone-numbers/incoming).  Afterwards the Twilio phone number can be added into the application later using the UI.

***All the numbers in the applications must be in [E.164 format](https://en.wikipedia.org/wiki/E.164)***

#### Setting up a new trial account  

First create a new account and sign in, once you do you will be greeted with the following screen 

![twilio-get-trial-number](https://i.ibb.co/4ZSywfT/get-trial-number.png)  

Press the big red `Get a Trial Number` button  

![twilio-assign-a-number](https://i.ibb.co/vHdn2St/choose-number.png)  

Then either accept the offered number,  or press `Search for a different number`, to pick one in another country
  
***Make sure the number you pick can send and receive SMS***

Now you have a valid number to use during testing.  
 
However if you are planning on running this application in Localhost, then in order to get full functionality you must enable a way for Twilio to contact your localhost endpoint.

#### Localhost Twilio config

First lets create and setup `Programmable Messaging`  

Select `Programmable Messaging` from the menu, which can be accessed via the `...` icon  

![twilio-programmable-menu](https://i.ibb.co/SXWpgNb/programmable-messaging.png)  

Then pressing `Create Messaging Service`  

![twilio-create-message-service](https://i.ibb.co/LzdNQsd/Create-messaging-service.png)  

Then pressing `Add Senders` and set sender type to `Phone Number` 

![twilio-add-senders](https://i.ibb.co/z70ypTy/add-senders.png)  

Select the trial number you just created, and press `save` at the bottom of the screen   

![twilio-select-number](https://i.ibb.co/Sx6StYW/select-number.png)  

Now select the `Integration` sub menu  

Now you will need to download and run `ngrok` or an alternative tunnelling software.  

Download ngrok [here](https://ngrok.com/download) and extract the .exe somewhere locally  
In a command shell from the same directory as the .exe run `ngrok http 8080`  

Copy the https Forwarding parameter e.g. https://b6231be3.ngrok.io (this is mock data) and paste this into the request URL on Twilio.   Then append `/api/v1/events/twilio` to the end of the URL so Twilio can reach this endpoint locally to send stats when people reply to a message.  
  
***Make sure the behaviour is set to `Send a webhook`*** 
  
![ngrok](https://i.ibb.co/pWqyvPq/ngrok.png)  

![twilio-integration-setup](https://i.ibb.co/tPTb41K/integration-setup.png)  

### Twilio settings
In order for the app to connect to Twilio you will need to set up your twilio account information as shown below 
 
![twilio-account-details](https://i.ibb.co/1vpQJxP/auth-info.png)  

Change the default values in the file `calltree-core\src\main\resources\twilio.properties` to include your twilio account details  

| Config&nbsp;Value | Description                                                                                                                                                  |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Account&nbsp;Sid  | Your primary Twilio account identifier - find this [in the Console](https://www.twilio.com/console).                                                         |
| Auth&nbsp;Token   | Used to authenticate - [just like the above, you'll find this here](https://www.twilio.com/console).                                                         |


### Backend Setup
1. Clone the repo: `git clone https://github.com/TeamWicket/Twilio-BCP-CallTree.git`
2. Prepare a java executable using Maven: `mvn clean install` or using the wrapper `mvnw clean install`
3. From the command line, navigate to the .jar file at `calltree-core\target` and run it using: `java -jar calltree-core-0.0.1-SNAPSHOT.jar`
4. The backend will now be running as a standalone Spring Boot application  

### Front end setup
Make sure `yarn` is installed  via `yarn -version`, if it's missing it can be installed via `npm install -g yarn`.  
On Windows you will also need to add it to PATH, it's default installation is in `C:\Users\<user>\AppData\Roaming\npm`.
1. Navigate to the `calltree-ui` repo and use the commands:
* `yarn install`
* `yarn start`
2. This will then load the front end up in your browser  

### Running application details

| Service | Address |
|:--- | :--- |
| Backend | http://localhost:8080 |
| Frontend | http://localhost:3000 |

### UI Overview  

When you first load the application up you will get your high level dashboard that can display an overview of your application setup  
Note: If you wish to switch to a cool `dark mode` then click on the profile button in the top right and select configuration and change your theme.  
You can also `log out` of the app and it will return you to a login screen, this can be forked and your own auth implementation can be added in here, for the purposes of our demo its only there to show what can be done but you can `login` again by typing in anything here and hitting enter. For your own implementation you will need to define your own authorisation using the Auth component.  

![dashboard](https://i.ibb.co/zQnwfgQ/dashboard.png)  

If you then navigate to the `Contacts` menu option this will give you the ability to add contacts, the first contact must be marked as the `Champion`, you can then add more contacts. Please note whoever each persons `point of contact` is will be the name and number they are instructed to contact in the event they have any questions once they reply to the initial BCP event message.  

![contacts](https://i.ibb.co/SK0799Y/contacts.png)  

![create-contact](https://i.ibb.co/fD3tpxL/create-contact.png)  

The following screen is where you can add your Twilio number that you assigned earlier, note you must have an active twilio number available in order to create an event.  

![number](https://i.ibb.co/ZH0CKW7/number.png)  

You must setup your number as `Is available` for it to be used as part of your event.  

![add-number](https://i.ibb.co/PNZtCWh/new-number.png)  

Finally when you are ready to send some messages you can go to the `events` screen and create a new event  

![events](https://i.ibb.co/WWpDBcb/events.png)  

Enter the mesage you wish to be sent in the text field, Select which roles you wish to send this message to (note this is hierarchical so if you wish to send to EVERYONE then select `reporter` as this will go up the chain. If you wish for `Leaders` and everyone above then select this and so forth. The Champion will not recieve a message as they will kick off the event.  

![new-event](https://i.ibb.co/4T16zsc/new-event.png)  

Finally click the start event button and this will send your messages.  

If you wish to see the stats just now you can do so by using the Swagger endpoints for the Stats Controller or by looking in the database table for the call event information.  


### PostgreSQL settings

By default the application runs off an in memory H2 database, however it is compliant with an external PostgreSQL database to ensure persistance longer term and outside of a demo environment.  This can be done by:  

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

This template is open source and welcomes contributions from the community. All contributions are subject to our [Code of Conduct](https://github.com/TeamWicket/Twilio-BCP-CallTree/blob/master/CODE_OF_CONDUCT.md).

## Contributors
[Team Wicket](https://github.com/TeamWicket/Twilio-BCP-CallTree/graphs/contributors)
* Alessandro Arosio: [Github](https://github.com/AlessandroArosio) - [LinkedIn](https://www.linkedin.com/in/alessandroarosio-uk/)
* John Hanna: [Github](https://github.com/jhanna60) - [LinkedIn](https://www.linkedin.com/in/john-hanna-87123080/)
* Malcolm Watt: [Github](https://github.com/malc54)
* Alistair McKellar: [Github](https://github.com/CGAura)

[Visit the project on GitHub](https://github.com/TeamWicket/Twilio-BCP-CallTree)  


## Shoutouts

React-Admin - https://github.com/marmelab/react-admin

The amazing work done by https://github.com/marmelab has allowed us to build a very nice functional front end.  

## License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)  

## Disclaimer

No warranty expressed or implied. Software is as is.  
