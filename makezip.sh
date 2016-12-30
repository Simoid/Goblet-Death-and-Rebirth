#!/bin/bash
cd Runnable\ jar
mkdir Goblet-Death-and-Rebirth
cp ../Goblet-Death-and-Rebirth/build/classes/artifacts/Goblet_Death_and_Rebirth_jar/Goblet-Death-and-Rebirth.jar Goblet-Death-and-Rebirth
cp -r ../Goblet-Death-and-Rebirth/assets Goblet-Death-and-Rebirth
cp -r META-INF Goblet-Death-and-Rebirth
zip -r Goblet-Death-and-Rebirth.zip Goblet-Death-and-Rebirth
rm -r Goblet-Death-and-Rebirth
cd ..
