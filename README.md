# Getting Started

- configure the app at `application.properties`, (path: `src/main/resources`) part of the file is a description individual options
- use Tomcat server 8+ to deploy the app as the ROOT webapp (**automatic deployment with IntelliJ Idea)
- by default, the app is running at: [http://localhost:8080](http://localhost:8080)

** If you are using Intellij Idea Tomcat Server plugin to run the app, use application context `/` instead of `/s-forms-manager`.
- Edit Configurations... -> Tomcat Server configuration -> Deployment -> Application context
- this way the app is deployed as the ROOT webapp and is accesible at the `localhost:port/` and not `localhost:port/context`
- otherwise you would have to adjust the frontend app to use the different endpoint  

### Configuration

Mandatory configuration properties:
- database repository URL: repositoryUrl=http://localhost:8090/rdf4j-server/repositories/sforms_manager

#### Trello connection
To set up Trello connection, you need to create a trello board and generate Trello Key + Trello Token. 
Please, follow the instructions at https://developer.atlassian.com/cloud/trello/guides/rest-api/api-introduction/.
You also need to get your trello board-id. You can find out your board-id (id) by using: 

`https://api.trello.com/1/boards/{TRELLO_BOARD_SHORT_ID}/?key=TRELLO_KEY&token=TRELLO_TOKEN`

You can extract TRELLO_BOARD_SHORT_ID from the url when you're in the browser at the Trello board detail.

Set up the `trello.key`, `trello.token` and `trello.board-id` in the configuration file.  


### System Requirements

* JDK 8 (newer or older versions are not supported at the moment)
* Apache Tomcat 8.5 or later (9.x is recommended) or any Servlet API 4-compatible application server
* RDF4J server is 2.5.5 (may work with other versions as well, but this one is recommended) 


### Project setup
It is possible to set up project in the user interface of the app. Example configuration of a project is: \

- App Repository URL: `http://onto.fel.cvut.cz/rdf4j-server/repositories/fss-study-dev-app` \
- FormGen Repository URL: `http://onto.fel.cvut.cz/rdf4j-server/repositories/fss-study-dev-formgen` \
- Service URL: `https://kbss.felk.cvut.cz/s-pipes-vfn/service?_pId=clone-fss-form` \
- Record Recognition SPARQL: if is left empty, content of the following file is used: 
`src\main\resources\templates\remote\recordSnapshotRemoteData.sparql` 
