
Very simple:
grails create-app example
grails create-domain-class Document
grails run-app

Document.groovy
resources.groovy
BootStrap.groovy
BuildConfig.groovy
DataSource.groovy
Config.groovy

Grails impressed me with how easy it was to setup and get everything working, took under an hour. You dont even need to 
create a controller. You just define your domain class as a resource and it generates a rest api for it. Hooking it up to 
postgres was just a few simple changes to config files. Very impressed with how simple it was. The downside is there is a lot 
of boilerplate and scaffolding that is out generated. Groovy as a language is nice, it is like Java except simpler. Overall 
the experience is reminiscent of spring boot, except easier. Definitely good for RAD, no dealing with sql or creating service, 
just create domain and configure db and you are up and running.
