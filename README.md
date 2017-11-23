

# rg-archetype-j2ee-myfaces

See base project [rg-archetype-j2ee-login](https://github.com/gwnio/rg-archetype-j2ee-login/blob/master/README.md).
* Note replace **rg-archetype-j2ee-login** with **rg-archetype-j2ee-myfaces**.
* Includes MyFaces JSF implementation.

## Technologies (@Override)
* Apache MyFaces

## Highlights (@Override)
* Wildfly configuration instructions
  * Adding MyFaces JSF implementation to Wildfly JSF subsystem 

## Setup (@Override)

### Configure WildFly

#### Wildfly MyFaces Configuration

See the [following link](http://gwnio.github.io/gwnio-page/tech/2017/06/14/wildfly-jsf-configuration-myfaces.html) for adding MyFaces JSF implementation to Wildfly JSF subsystem.

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

### Try it
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces/SignUp.jsp
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces/secure/SecurePage.html
* http://127.0.0.1:8080/rg-archetype-j2ee-myfaces/jsf-example/RequestName.xhtml