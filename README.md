# Goblet: Death and Rebirth
Spel för kursen Projektuppgift i introduktion till datalogi DD1349 

##Beskrivning:
*Goblet: Death and Rebirth* är en top-down roguelite inspirerad av *The Binding of Isaac: Rebirth* och *Goblet Grotto*. I sin färdiga form är det tänkt att spelet ska innehålla följande features: 
* Slumpmässigt genererade nivåer, så att varje gång spelet startas ger en unik upplevelse.
* Ett enkelt combat-system där huvudkaraktären kan attackera i 4 eller 8 olika riktningar.
* Många olika fiender med olika attackmönster.
* Många olika objekt som kan plockas upp av huvudkaraktären och användas på olika sätt.
* En boss per nivå.

##För att bygga projektet:
För att bygga projektet behövs intellij och gradle.  
Ladda ned projektet som zip och extrahera eller använd git clone/git pull. Gå sedan in i mappen *Goblet-Death-and-Rebirth/Goblet-Death-and-Rebirth/* via terminalen, och använd kommandot **./gradlew idea**. Öppna intellij och importera mappen som du precis var i som ett gradleprojekt (*File > New > Project from existing sources* och sedan *Import project from external model > Gradle*). För att starta spelet används filen desktopLauncer.java som finns i *desktop/src/com.goblet.game.desktop*.

##Hur man spelar:
Använd piltangenterna för att röra på huvudkaraktären.  
Attackera med Z, du attackerar i den riktningen som spelaren rör sig åt då du trycker på Z, men riktningen hålls till samma även om du byter riktning när du väl har börjat attackera.  
Se upp för snabba spindlar!
