
Hier ein Test, dass das Transfer Interface mit JAX-RS Annotationen auch von Projekten implementier werden kann, wo kein JAX-RS im classpath ist:

```powershell
$classpath='.\target\client-plain-1.0-SNAPSHOT.jar;..\transfer\target\transfer-1.0-SNAPSHOT.jar'
java -cp $classpath de.servicezombie.samples.xkcd_tranfer.App
client-plain implements JAX-RS Interface successfully!
client-plain implements JAX-RS Interface successfully!

```