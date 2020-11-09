
Hier ein Test, dass das Transfer Interface mit JAX-RS Annotationen auch von Projekten implementier werden kann, wo kein JAX-RS im classpath ist:

```powershell

cd .\client-openapi\
$classpath='.\target\client-openapi-1.0-SNAPSHOT.jar;..\transfer-openapi\target\transfer-openapi-1.0-SNAPSHOT.jar'
java -cp $classpath de.servicezombie.samples.xkcd_openapi.App

```