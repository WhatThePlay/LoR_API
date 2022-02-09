# LoR API Projektarbeit

## Abstract

Meine API ermöglicht die Abfrage verschiedenster Werte, um anhand dieser eine gesuchte Karte ausfindig zu machen.
Diese Funktion stellt klar die Hauptfunktion meiner API dar.
Dazu existieren drei verschiedene URIs, um die Karten zu suchen.
Man kann sowohl nach Units, Spells sowie auch nach Karten allgemein suchen.
Dies bietet die Möglichkeit nach Attributen der spezifischen Kartentypen zu suchen.

Zusätzlich zu der Kartensuche besteht die Möglichkeit sowohl Keywords, Regions und Rarities abzufragen.
Dies dient vor allem dem Zweck, wenn man sich bei einigen Grundlagen nicht mehr sicher ist, diese nachschauen zu können.

Die API ermöglicht natürlich auch das editieren, erstellen und löschen von Daten.

## Fazit

### Datenbank befüllen

Bereits zu Beginn des Projektes bin ich auf Probleme gestossen.
Zunächst musste ich die Daten, welche ich aus dem Internet geholt habe, anpassen um diese anschliessend in die Datenbank abzufüllen.
Dies stellte sich als schwerer dar als erwartet, da die Konversion von Excel zu csv die Daten so veränderte, dass ich sie nicht in die Datenbank füllen konnte.
Die Spalte, welche Probleme gemacht hat, habe ich im Endeffekt einfach entfernt.
Das Ganze hat mich allerdings den ganzen ersten Tag gekostet und mich damit etwas in Zeitdruck versetzt.

### API Methoden und Queries schreiben
Das Erstellen der Methoden in Java lief grösstenteils gut.
Zunächst war das Schreiben der Queries noch eine kleine Hürde.
Doch nachdem ich einige Queries geschrieben habe lief auch das grösstenteils problemlos.
Die if Verschachtelung bezüglich der suchbaren Attribute für die Karten war etwas aufwändig aber dennoch machbar.
Die verschiedenen Attribute habe ich unterschiedlich kategorisiert und diese auf unterschiedliche URIs aufgeteilt, damit die Verschachtelung nicht zu gross wird.

Zusätzlich stellte sich der Fakt, dass die ids der Karten ein String sind, ein kleines Problem dar, da ich für diese die id spezifischen methoden selbst als Query schreiben musste für die delete Funktionalität die Annotation "@Transactional" nötig ist.

### Testing
Die grössten Probleme bereitete mir allerdings das Testing. 
Bereits im Vorhinein war mir bewusst, dass ich beim Testing das wenigste Verständnis habe.
Dies hat sich beim Schreiben der Tests verbessert.
Dennoch stiess ich beim Testing auf eine Wand.
Das Schreiben der Tests, welche überprüfen, ob die richtige Exception geworfen wird, funktionierte nicht und ich verstand auch nicht warum.
Das Testing liefert mir weiterhin Probleme.
Den genauen Unterschied und die verschiedenen Ergebnisse, wenn ich Repo/Controller oder Service benutze, sind mir weiterhin nicht ganz klar.

### Offene Punkte

Der grösste offene Punkt bleibt für mich die Kartensuche.
Die momentane Funktionalität bietet eine gute Grundlage.
Dennoch würde ich die Suche gerne noch weiter ausbauen und die verschiedenen Suchattribute in eine URI zusammenfassen.
Jedoch möchte ich dazu nicht eine grosse if Verschachtelung machen und müsste daher eine bessere Lösung als bisher finden.

Ausserdem bleibt das Testing natürlich ein offener Punkt, da ich dieses nie vervollständigen konnte.
Auch wenn das Testing nicht zu meinen Lieblingstätigkeiten gehört würde ich diese trotzdem gerne vervollständigen.