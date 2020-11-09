
# About 

Dieses Projekt ist ein Versuch, wie man einen Codegenerator verwenden kann. Ich denke, für unserern _Arbeitsprozess_ ist allerdings ein umgekehrter Prozess _Java First_ sinnvoller. Wir haben lediglich einen Java Client und mit Eclipse bekommen wir eine bessere Codevervollständigung, als in einer yaml Datei.

Der Vorteil der YAML Datei ist in erster Linie, dass sie kürzer ist. Ich denke das Generieren von Modellen ist hiermit prinzipiell gut umzusetzen.

Eventuell kann dieses Artefakt innerhalb der Firma nicht kompiliert werden.

# Module

Es gibt zwei Module:

transfer-openapi
: Das Modul generiert basierend auf der Datei `src/main/resources/api.yaml` Java Klassen mit Hilfe eines OpenAPI Mavenplugins umzusaetzen.

client-openapu
:Eine Implementierung, die `transfer-openapi` nutzt, aber kein JAX-RS oder andere Annotationen im CLASSPATH hat.

Basis für den Test ist der Webservice von xkcd:

* GET https://xkcd.com/info.0.json
* GET https://xkcd.com/{comicId}/info.0.json - comicId ist eine positive Integer
