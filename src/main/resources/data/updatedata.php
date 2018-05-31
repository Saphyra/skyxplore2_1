<?php
    $files = scandir("datafiles/");
    print "<H2>Loaded files:</H2>";
    foreach($files as $file){
        if(strstr($file, ".php")){
            print "<B>$file</B><BR>";
            include_once("datafiles/" . $file);
        }
    }
    
    foreach($data as $dataType=>$values){
        $dirName = "$dataType";
        if(!is_dir($dirName)){
            mkdir($dirName, 0777, true);
        }
        
        foreach($values as $id=>$value){
            $fileName = "$dirName/$id.json";
            $content = json_encode($value, JSON_PRETTY_PRINT);
            $file = fopen($fileName, "w");
            fwrite($file, $content);
            fclose($file);
        }
    }
    
    function write($element){
        //Writes content of an element recursive
        $type = gettype($element);
        if($type == "array"){
            print "<OL>";
                foreach($element as $name=>$value){
                    print "<LI>$name - ";
                    write($value);
                    print "</LI>";
                }
            print "</OL>";
        }else if($type == "object"){
            $props = get_object_vars($element);
            print "<OL>";
                foreach($props as $name=>$value){
                    print "<LI>$name - ";
                    write($value);
                    print "</LI>";
                }
            print "</OL>";
        }else{
            print $element;
        }
    }
?>