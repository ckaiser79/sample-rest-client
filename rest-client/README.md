
# About 

Dieses Projekt soll aufzeigen, wie der REST Client aus Sicht eines Anwendungsentwicklers genutzt werden soll. Dazu stehen dokumentierte  Testfälle und Beispiele für das Lesen von Konfigurationswerten zur Verfügung.

# Module

Es gibt drei Module:

transfer
: Das Modul ist weitgehend technologieunabhängig und dient dem Austausch von Datenobjekten.

client
:Dies ist die Implementierung. Hier ist die Wahl der Techhnologie freigestellt, allerdings sollten diese hinter den Transfer Dateien oder primitiven Typen wie String, Integer, Map, List Array, ... gekapselt sein. Durch letzteres kann die Client Technologie später ausgetauscht werden.

client-plain
:Eine Implementierung, die `transfer` nutzt, aber kein JAX-RS im CLASSPATH hat.

Basis für den Test ist der Webservice von xkcd:

* GET https://xkcd.com/info.0.json
* GET https://xkcd.com/{comicId}/info.0.json - comicId ist eine positive Integer
