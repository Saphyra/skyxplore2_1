<?php
    /*
    $id = "";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "";
    $data["ability"][$id]["description"] = "";
    $data["ability"][$id]["energyusage"] = ;
    $data["ability"][$id]["active"] = ;
    $data["ability"][$id]["reload"] = ;
    $data["ability"][$id]["effect"][] = "";
    */
    
    $id = "abilityblock";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Képességblokkolás";
    $data["ability"][$id]["description"] = "A megcélzott ellenséges hajó nem használhat képességeket.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 3;
    $data["ability"][$id]["reload"] = 10;
    $data["ability"][$id]["effect"]["range"] = 6;
    
    $id = "accuracy";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Precizitás";
    $data["ability"][$id]["description"] = "Növeli a fegyverek találati pontosságát.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["accuracy"] = 200;
    
    $id = "aftermath";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Utóhatás";
    $data["ability"][$id]["description"] = "Minden találat ideiglenesen növeli a célpont által elszenvedett sebzést. (Passzív)";
    $data["ability"][$id]["energyusage"] = 0;
    $data["ability"][$id]["active"] = -1;
    $data["ability"][$id]["reload"] = 0;
    $data["ability"][$id]["effect"]["damage"] = 1;
    
    $id = "blind";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Találati pontosság csökkenés";
    $data["ability"][$id]["description"] = "Csökkenti a kijelölt ellenséges hajó fegyvereinek találati pontosságát.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["range"] = 6;
    
    $id = "cloak";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Álcázó berendezés";
    $data["ability"][$id]["description"] = "Elrejti a hajót az ellenséges radarok elől";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 10;
    $data["ability"][$id]["reload"] = 30;
    $data["ability"][$id]["effect"]["activation"] = 3;
    
    $id = "corehullrepair";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Magburkolat javítás";
    $data["ability"][$id]["description"] = "Körönként javítja a magburkolat sérüléseit.";
    $data["ability"][$id]["energyusage"] = 0;
    $data["ability"][$id]["active"] = -1;
    $data["ability"][$id]["reload"] = 0;
    $data["ability"][$id]["effect"]["repair"] = 1;
    
    $id = "damageovertime";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Folyamatos sebzés";
    $data["ability"][$id]["description"] = "A célpont minden körben sebzést szenved el.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 10;
    $data["ability"][$id]["effect"]["damage"] = 5;
    $data["ability"][$id]["effect"]["wawes"] = 5;
    
    $id = "damagereduction";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Sebzéscsökentés";
    $data["ability"][$id]["description"] = "Csökkenti a hajót érő sebzés mértékét";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["reduction"] = 40;
    
    $id = "dimensiongate";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Dimenziókapu";
    $data["ability"][$id]["description"] = "Átmenetileg egy másik dimenzióba küld egy szövetséges vagy ellenséges hajót, így az se támadni, se sérülni nem tud.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 30;
    
    $id = "disableweapons";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Fegyverleállás";
    $data["ability"][$id]["description"] = "Kikapcsolja egy kijelölt ellenséges hajó fegyvereit.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 2;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["range"] = 6;
    
    $id = "energyrecharge";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Energiatöltés";
    $data["ability"][$id]["description"] = "Feltölti egy szövetséges hajó akkumulátorait.";
    $data["ability"][$id]["energyusage"] = 350;
    $data["ability"][$id]["active"] = 1;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["recharge"] = 350;
    
    $id = "energyregeneration";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Generátor túlhajtás";
    $data["ability"][$id]["description"] = "Növeli egy szövetséges hajó energiatermelését";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 10;
    $data["ability"][$id]["effect"]["staticregeneration"] = 20;
    $data["ability"][$id]["effect"]["relativeregeneration"] = 50;
    
    $id = "energyregenerationblock";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Energia regeneráció blokkolás";
    $data["ability"][$id]["description"] = "A kijelölt ellenséges hajónak nem töltődik az energiája.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 3;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["range"] = 6;
    
    $id = "evasion";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Kitérés";
    $data["ability"][$id]["description"] = "Növeli a hajó kitérési esélyét.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["evasion"] = 35;
    
    $id = "freeze";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Mozgáskorlátozás";
    $data["ability"][$id]["description"] = "Leállítja a kijelölt ellenséges hajó hajtóműveit.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 3;
    $data["ability"][$id]["reload"] = 20;
    $data["ability"][$id]["effect"]["range"] = 6;
    
    $id = "instantdamage";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Azonnali sebzés";
    $data["ability"][$id]["description"] = "Azonnali sebzést okoz a bemért ellenséges hajónak.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 1;
    $data["ability"][$id]["reload"] = 40;
    $data["ability"][$id]["effect"]["damage"] = 500000;
    
    $id = "invulnerable";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Sebezhetetlenség";
    $data["ability"][$id]["description"] = "Sebezhetetlenné teszi a hajót.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 3;
    $data["ability"][$id]["reload"] = 15;
    
    $id = "lock";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Célpont bemérve";
    $data["ability"][$id]["description"] = "A fegyverek a kijelölt ellenséget támadják, megkerülve a célpontot védő hajókat.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = -1;
    $data["ability"][$id]["reload"] = 20;
    
    $id = "penetration";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Pajzsátütés";
    $data["ability"][$id]["description"] = "A támadások figyelmen kívül hagyják a célpont pajzsát és közvetlenül a burkolatot sebzik.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 10;
    $data["ability"][$id]["reload"] = 30;
    $data["ability"][$id]["effect"]["chance"] = 35;
    
    $id = "phalanx";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Phalanx";
    $data["ability"][$id]["description"] = "A hajó kiterjeszti védelmét, és felfogja a szomszédos szövetségeseire érkező sebzést.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 10;
    
    $id = "quickrecharge";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Gyors újratöltés";
    $data["ability"][$id]["description"] = "Csökkenti a közelben tartózkodó szövetségesek képességeinek töltési idejét.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 10;
    $data["ability"][$id]["reload"] = 20;
    $data["ability"][$id]["effect"]["range"] = 4;
    $data["ability"][$id]["effect"]["rechargerate"] = 50;
    
    $id = "rapidfire";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Gyorstüzelés";
    $data["ability"][$id]["description"] = "Növeli a fegyverek támadási sebességét.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 20;
    $data["ability"][$id]["effect"]["attackspeed"] = 50;
    
    $id = "reflection";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Reflekció";
    $data["ability"][$id]["description"] = "A kapott sebzés egy részét a támadó fél is elszenvedi.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["reflectrate"] = 30;
    
    $id = "repair";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Burkolatjavítás";
    $data["ability"][$id]["description"] = "Megjavítja egy szövetséges hajó burkolatát.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 0;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["staticrepair"] = 20000;
    $data["ability"][$id]["effect"]["relativerepair"] = 10;
    
    $id = "shieldleech";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Pajzslopás";
    $data["ability"][$id]["description"] = "Az okozott sebzés egy részét pajzsenergiaként hasznosítja újra.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 10;
    $data["ability"][$id]["reload"] = 30;
    $data["ability"][$id]["effect"]["leechrate"] = 50;
    
    $id = "shieldovercharge";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Pajzsenergia növelés";
    $data["ability"][$id]["description"] = "Növeli a hajó pajzsenergiáját.";
    $data["ability"][$id]["energyusage"] = 0;
    $data["ability"][$id]["active"] = 0;
    $data["ability"][$id]["reload"] = -1;
    $data["ability"][$id]["effect"]["shield"] = 20;
    
    $id = "shieldrecharge";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Pajzstöltés";
    $data["ability"][$id]["description"] = "Visszatölti egy szövetséges hajó pajzsenergiáját.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 1;
    $data["ability"][$id]["reload"] = 20;
    $data["ability"][$id]["effect"]["staticrecharge"] = 10000;
    $data["ability"][$id]["effect"]["relativerecharge"] = 10;
    $data["ability"][$id]["effect"]["range"] = 4;
    
    $id = "shieldregeneration";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Pajzsregeneráció növelés";
    $data["ability"][$id]["description"] = "Növeli egy szövetséges hajó pajzsregenerációját.";
    $data["ability"][$id]["energyusage"] = 200;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 20;
    $data["ability"][$id]["effect"]["recharge"] = 5;
    
    $id = "shieldregenerationblock";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Pajzsregeneráció blokkolás";
    $data["ability"][$id]["description"] = "A kijelölt ellenséges hajó pajzsregenerációja megáll.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["range"] = 6;
    
    $id = "speed";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Gyorsítás";
    $data["ability"][$id]["description"] = "Megnöveli a hajó mozgási sebességét.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    
    $id = "splashdamage";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Repeszlövedék";
    $data["ability"][$id]["description"] = "Az okozott sebzés egy részét elszenvedik a környező ellenséges egységek is.";
    $data["ability"][$id]["energyusage"] = 300;
    $data["ability"][$id]["active"] = 5;
    $data["ability"][$id]["reload"] = 15;
    $data["ability"][$id]["effect"]["damage"] = 30;
    
    $id = "teleportation";
    $data["ability"][$id]["id"] = $id;
    $data["ability"][$id]["name"] = "Teleportáció";
    $data["ability"][$id]["description"] = "A hajó egy kis távolságon belül a kijelölt helyre teleportál.";
    $data["ability"][$id]["energyusage"] = 400;
    $data["ability"][$id]["active"] = 0;
    $data["ability"][$id]["reload"] = 20;
    $data["ability"][$id]["effect"]["range"] = 4;
?>