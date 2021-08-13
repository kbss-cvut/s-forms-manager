# Getting Started

- configure the app at `application.properties`, part of the file is a description individual options
- use Tomcat server 8+ to deploy the app as the ROOT webapp (**automatic deployment with IntelliJ Idea)
- by default, the app is running at: [http://localhost:8080](http://localhost:8080)

** If you are using Intellij Idea Tomcat Server plugin to run the app, use application context `/` instead of `/s-forms-manager`.
- Edit Configurations... -> Tomcat Server configuration -> Deployment -> Application context
- this way the app is deployed as the ROOT webapp and is accesible at the `localhost:port/` and not `localhost:port/context`
- otherwise you would have to adjust the frontend app to use the different endpoint  