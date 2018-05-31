<?php
    /*
    $id = "";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "";
    $data["general"][$id]["description"] = "";
    $data["general"][$id]["type"] = "";
    $data["general"][$id]["slot"] = "";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 1;
    */
    
    $id = "bat-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "BAT-01 Akkumulátor";
    $data["general"][$id]["description"] = "Energiatároló a hajók rendszereinek ellátásához.";
    $data["general"][$id]["type"] = "battery";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 1;
    
    $id = "bat-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "BAT-02 Akkumulátor";
    $data["general"][$id]["description"] = "Energiatároló a hajók rendszereinek ellátásához.";
    $data["general"][$id]["type"] = "battery";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 2;
    
    $id = "bat-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "BAT-03 Akkumulátor";
    $data["general"][$id]["description"] = "Energiatároló a hajók rendszereinek ellátásához.";
    $data["general"][$id]["type"] = "battery";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 3;
    
    $id = "cex-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "CEX-01 Csatlakozó bővítő";
    $data["general"][$id]["description"] = "Bővítő egység, mely megnöveli a hajó csatlakozóinak számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 0;
    
    $id = "cex-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "CEX-02 Csatlakozó bővítő";
    $data["general"][$id]["description"] = "Bővítő egység, mely megnöveli a hajó csatlakozóinak számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 0;
    
    $id = "cex-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "CEX-03 Csatlakozó bővítő";
    $data["general"][$id]["description"] = "Bővítő egység, mely megnöveli a hajó csatlakozóinak számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 0;
    
    $id = "cor-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "COR-01 Magburkolat erősítés";
    $data["general"][$id]["description"] = "Növeli a hajó központi egységének védelmét.";
    $data["general"][$id]["type"] = "corehull";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 1;
    
    $id = "cor-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "COR-02 Magburkolat erősítés";
    $data["general"][$id]["description"] = "Növeli a hajó központi egységének védelmét.";
    $data["general"][$id]["type"] = "corehull";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 2;
    
    $id = "cor-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "COR-03 Magburkolat erősítés";
    $data["general"][$id]["description"] = "Növeli a hajó központi egységének védelmét.";
    $data["general"][$id]["type"] = "corehull";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 3;
    
    $id = "dex-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "DEX-01 Védelem bővítő";
    $data["general"][$id]["description"] = "Bővítő egység, mely megnöveli a hajó védelmi moduljainak számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 0;
    
    $id = "dex-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "DEX-02 Védelem bővítő";
    $data["general"][$id]["description"] = "Bővítő egység, mely megnöveli a hajó védelmi moduljainak számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 0;
    
    $id = "dex-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "DEX-03 Védelem bővítő";
    $data["general"][$id]["description"] = "Bővítő egység, mely megnöveli a hajó védelmi moduljainak számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 0;
    
    $id = "gen-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "GEN-01 Generátor";
    $data["general"][$id]["description"] = "Akkumulátorok töltéséhez szükséges energia biztosítása.";
    $data["general"][$id]["type"] = "generator";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 1;
    
    $id = "gen-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "GEN-02 Generátor";
    $data["general"][$id]["description"] = "Akkumulátorok töltéséhez szükséges energia biztosítása.";
    $data["general"][$id]["type"] = "generator";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 2;
    
    $id = "gen-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "GEN-03 Generátor";
    $data["general"][$id]["description"] = "Akkumulátorok töltéséhez szükséges energia biztosítása.";
    $data["general"][$id]["type"] = "generator";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 3;
    
    $id = "sto-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "STO-01 Raktér";
    $data["general"][$id]["description"] = "Konténer a csatatéren szerzett zsákmány elszállítására.";
    $data["general"][$id]["type"] = "storage";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 0;
    
    $id = "sto-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "STO-02 Raktér";
    $data["general"][$id]["description"] = "Konténer a csatatéren szerzett zsákmány elszállítására.";
    $data["general"][$id]["type"] = "storage";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 0;
    
    $id = "sto-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "STO-03 Raktér";
    $data["general"][$id]["description"] = "Konténer a csatatéren szerzett zsákmány elszállítására.";
    $data["general"][$id]["type"] = "storage";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 0;
    
    $id = "wex-01";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "WEX-01 Fegyver bővítő";
    $data["general"][$id]["description"] = "Növeli a hajóra szerelhető fegyverek számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 1;
    $data["general"][$id]["score"] = 0;
    
    $id = "wex-02";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "WEX-02 Fegyver bővítő";
    $data["general"][$id]["description"] = "Növeli a hajóra szerelhető fegyverek számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 2;
    $data["general"][$id]["score"] = 0;
    
    $id = "wex-03";
    $data["general"][$id]["id"] = $id;
    $data["general"][$id]["name"] = "WEX-03 Fegyver bővítő";
    $data["general"][$id]["description"] = "Növeli a hajóra szerelhető fegyverek számát.";
    $data["general"][$id]["type"] = "extender";
    $data["general"][$id]["slot"] = "connector";
    $data["general"][$id]["level"] = 3;
    $data["general"][$id]["score"] = 0;
?>