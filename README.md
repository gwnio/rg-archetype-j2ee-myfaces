

# rg-archetype-j2ee-myfaces

J2EE project configured with form authentication and MyFaces JSF implementation.

## Technologies
* Eclipse 4.5.2 Mars
* Jdk1.8.0_77
* MySql 5.1.38
* Wildfly 10
* Java EE 7
* Hibernate 5.1
* Apache MyFaces
* Other 3rd party libraries (i.e. Apache commons lang)

## Highlights
* Instructions for server configuration in Eclipse
* Wildfly configuration instructions
  * Adding MyFaces JSF implementation to Wildfly JSF subsystem 
* Database data definition scripts
* Declarative form-based authentication
* Custom login module that extends jboss class **AbstractServerLoginModule**
* Password digest
* Hibernate configuration
  * **HibernateTxRequestFilter** class for session-per-request unit-of-work
  * **HibernateUtil** class for SessionFactory singleton
  * User entity and DAO classes
* Web content includes sign up and login page
* Example rest service

## Setup

### MySql
* Create new database schema.  For this project call it 'boilerplate'
* Create user table
```
CREATE TABLE `user` (`id` int(11) NOT NULL AUTO_INCREMENT, `username` varchar(45) NOT NULL, `password` longtext NOT NULL, `role` varchar(45) DEFAULT NULL, `email` varchar(100) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `username_UNIQUE` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
```

### Java EE Server - WildFly
Download and extract WildFly to {wildfly_home}

### Eclipse setup server runtime
* Go to Window -> Preferences -> Server -> Runtime Environments
* Select Add...
* Choose **WildFly 10.0 Runtime**
* Name: **WildFly 10.0 Boilerplate**
* Home Directory: browse to {wildfly_home} location and select
* Runtime JRE -> Alternate JRE: select jdk1.8.0_77

### Import project from Git repository and configure project
* Right click on rg-archetype-j2ee-myfaces -> Build Path -> Configure Build Path...
* Remove JRE System Library
* Select Add Library...
* Select JRE System Library
* Choose Alternate JRE -> jdk1.8.0_77

### Eclipse setup server
* Window -> Show View -> Servers
* New -> Server
* Server type: **WildFly 10.0**
* Name: **WildFly 10.0 Boilerplate**
* Next
* Choose runtime server **WildFly 10.0 Boilerplate**
* Next
* Skip adding the application to the server configuration, you will do that later
* Start the server
  * You may run into the following error on startup.  I used a program called Tcpview to find the port that was being used.  In my case it was a NVIDIA process, NvNetworkService.exe.  Upon ending this process, the wildfly server started with any problems.

```
Failed to start service jboss.serverManagement.controller.management.http: org.jboss.msc.service.StartException in service jboss.serverManagement.controller.management.http: WFLYSRV0083: Failed to start the http-interface service
...
Caused by: java.net.BindException: Address already in use: bind
```

* open a command prompt and navigate to {wildfly_home}\bin
* run add-user.bat to setup a Management user
* Access management console http://127.0.0.1:9990
* Notice the user/password was added in {wildfly_home}\standalone\configuration\mgmt-users.properties

### Configure WildFly

#### Add jdbc module
* update {rg-archetype-j2ee-myfaces home}\Resources\jboss-add-jdbc-module.cli if needed
* open command prompt
* navigate to {wildfly_home}\bin and run the following...

```
jboss-cli.bat --file={rg-archetype-j2ee-myfaces home}\Resources\jboss-add-jdbc-module.cli
```
* creates mysql jdbc module in {wildfly_home}\modules\com\mysql\jdbc

#### Create datasource
* update {rg-archetype-j2ee-myfaces home}\Resources\jboss-add-datasource-module.cli if needed.  If you change the datasource name you will need to update the following:
  * {rg-archetype-j2ee-myfaces home}\src\org\rg\archetype\data\hibernate\hibernate.cfg.xml
* open command prompt
* navigate to {wildfly_home}\bin and run the following...

```
jboss-cli.bat --file={rg-archetype-j2ee-myfaces home}\Resources\jboss-add-datasource-module.cli
```

* creates datasource {wildfly_home}\standalone\configuration\standalone.xml
* also in the admin console -> Configuration -> Subsystems -> Datasources -> Non-XA - you should see MySqlBoilerplateDS

#### Create security domain
* update {rg-archetype-j2ee-myfaces home}\Resources\jboss-add-security-domain.cli if needed.  If you change the security-domain name you will need to update the following:
  * {rg-archetype-j2ee-myfaces home}\WebContent\WEB-INF\jboss-web.xml
* open command prompt
* navigate to {wildfly_home}\bin and run the following...

```
jboss-cli.bat --file={rg-archetype-j2ee-myfaces home}\Resources\jboss-add-security-domain.cli
```

#### Wildfly MyFaces Configuration

See the [following link](http://gwnio.github.io/gwnio-page/tech/2017/06/14/wildfly-jsf-configuration-myfaces.html){:target="_blank"} for adding MyFaces JSF implementation to Wildfly JSF subsystem.

Note for this project you do not need to change Wildfly's default JSF implementation because the web.xml specifies the JSF implementation.

To see your default JSF implementation in Wildfly you can run the cli gui.

```
jboss-cli.bat --gui
```

Scroll down and look for subsystem=jsf

If you forget to perform this step you may receive the following errors on server startup:

```
ERROR [org.jboss.msc.service.fail] (ServerService Thread Pool -- 74) MSC000001: Failed to start service jboss.undertow.deployment.default-server.default-host./rg-archetype-j2ee-myfaces: org.jboss.msc.service.StartException in service jboss.undertow.deployment.default-server.default-host./rg-archetype-j2ee-myfaces: java.lang.RuntimeException: java.lang.IllegalStateException: Could not find backup for factory javax.faces.application.ApplicationFactory. 
	at org.wildfly.extension.undertow.deployment.UndertowDeploymentService$1.run(UndertowDeploymentService.java:85)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
	at org.jboss.threads.JBossThread.run(JBossThread.java:320)
Caused by: java.lang.RuntimeException: java.lang.IllegalStateException: Could not find backup for factory javax.faces.application.ApplicationFactory. 
	at io.undertow.servlet.core.DeploymentManagerImpl.deploy(DeploymentManagerImpl.java:231)
	at org.wildfly.extension.undertow.deployment.UndertowDeploymentService.startContext(UndertowDeploymentService.java:100)
	at org.wildfly.extension.undertow.deployment.UndertowDeploymentService$1.run(UndertowDeploymentService.java:82)
	... 6 more
Caused by: java.lang.IllegalStateException: Could not find backup for factory javax.faces.application.ApplicationFactory. 
	at javax.faces.FactoryFinderInstance.getFactory(FactoryFinderInstance.java:555)
	at javax.faces.FactoryFinder.getFactory(FactoryFinder.java:283)
	at org.apache.myfaces.context.servlet.FacesContextImplBase.getApplication(FacesContextImplBase.java:169)
	at org.apache.myfaces.context.servlet.FacesContextImplBase.getELContext(FacesContextImplBase.java:231)
	at javax.faces.component.UIViewRoot.setLocale(UIViewRoot.java:1488)
	at org.apache.myfaces.webapp.AbstractFacesInitializer._createFacesContext(AbstractFacesInitializer.java:529)
	at org.apache.myfaces.webapp.AbstractFacesInitializer.initStartupFacesContext(AbstractFacesInitializer.java:501)
	at org.apache.myfaces.webapp.StartupServletContextListener.contextInitialized(StartupServletContextListener.java:115)
	at io.undertow.servlet.core.ApplicationListeners.contextInitialized(ApplicationListeners.java:187)
	at io.undertow.servlet.core.DeploymentManagerImpl.deploy(DeploymentManagerImpl.java:198)
	... 8 more
```

### In Eclipses servers view
* Right-click **WildFly 10.0 Boilerplate** -> Stop
* Right-click **WildFly 10.0 Boilerplate** -> Add and Remove...
* Add **rg-archetype-j2ee-myfaces** project
* Right-click **WildFly 10.0 Boilerplate** -> Publish
* Right-click **WildFly 10.0 Boilerplate** -> Start

### Try it
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces/SignUp.jsp
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces/secure/SecurePage.html
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces/jsf-example/RequestName.xhtml

## Things to notice
* Vendor specific security realm configuration for the Java EE server.  See ``rg-archetype-j2ee-myfaces\WebContent\WEB-INF\jboss-web.xml``
* Notice the Hibernate filter which creates a Hibernate session transaction for each incoming request and commits the transaction if not error occurs.  This unit-of-work is know as a session-per-request.
* If you change the location of your hibernate.cfg.xml you will need to update the HibernateUtil class
* hibernate.cfg.xml is using the datasource created in ``rg-archetype-j2ee-myfaces\Resources\jboss-add-datasource-module.cli``
* Passwords are encrypted using Jasypt **StringPasswordEncryptor**
* {rg-archetype-j2ee-myfaces home}\WebContent\WEB-INF\lib\javaee-api-7.0.jar
  * Java EE 7 Specification API
  * The implementation can be found in the server runtime **WildFly 10.0 Boilerplate**
* {rg-archetype-j2ee-myfaces home}\WebContent\WEB-INF\lib\picketbox-4.9.4.Final.jar is needed for **AbstractServerLoginModule**

### The custom login module
* class name is **org.rg.archetype.jboss.auth.LoginModule** and is referenced in the security domain.  See ```rg-archetype-j2ee-myfaces\Resources\jboss-add-security-domain.cli```
* The security domain uses a custom login module which extends jboss class **AbstractServerLoginModule**
* Should be placed in 1 of the following locations:
  * ``{wildfly_home}\standalone\deployments\rg-archetype-j2ee-myfaces.war\WEB-INF\classes``
  * or as a jar file in ``{wildfly_home}\standalone\deployments\rg-archetype-j2ee-myfaces.war\WEB-INF\lib``
* A custom Principal is used called **UserPrincipal**.  This custom principal includes the user id, username, and role.  The role in this custom principal corresponds to the <security-role> in the deployment descriptor.
* Notice the method **#getRoleSets**, the role in my custom principal gets added to the caller principal group