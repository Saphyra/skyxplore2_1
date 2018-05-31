<?php
    /*
    $id = "";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "";
    $data["material"][$id]["buildable"] = ;
    $data["material"][$id]["materials"][""] = "";
    */
    
    $id = "adamantium";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Adamantium";
    $data["material"][$id]["buildable"] = false;
    
    $id = "cable";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Kábel";
    $data["material"][$id]["buildable"] = true;
    $data["material"][$id]["materials"]["xebryum"] = 2;
    $data["material"][$id]["materials"]["mithril"] = 1;
    
    $id = "condensator";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Kondenzátor";
    $data["material"][$id]["buildable"] = true;
    $data["material"][$id]["materials"]["xebryum"] = 2;
    $data["material"][$id]["materials"]["mithril"] = 1;
    
    $id = "duranium";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Duranium";
    $data["material"][$id]["buildable"] = false;
    
    $id = "energycell";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Energiacella";
    $data["material"][$id]["buildable"] = true;
    $data["material"][$id]["materials"]["xebryum"] = 5;
    $data["material"][$id]["materials"]["mithril"] = 2;
    
    $id = "etherium";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Etherium";
    $data["material"][$id]["buildable"] = false;
    
    $id = "hullplate";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Burkolatlemez";
    $data["material"][$id]["buildable"] = true;
    $data["material"][$id]["materials"]["duranium"] = 2;
    $data["material"][$id]["materials"]["mithril"] = 2;
    
    $id = "mithril";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Mithril";
    $data["material"][$id]["buildable"] = false;
    
    $id = "uridium";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Uridium";
    $data["material"][$id]["buildable"] = false;
    
    $id = "xebryum";
    $data["material"][$id]["id"] = $id;
    $data["material"][$id]["name"] = "Xebryum";
    $data["material"][$id]["buildable"] = false;
    
?>