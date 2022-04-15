<?php

$hostname_localhost ="localhost";
$database_localhost ="sensor_station";
$username_localhost ="root";
$password_localhost ="";

$nodes = 2;
$consulta = "SELECT * FROM `node#` ORDER BY measurement DESC LIMIT 1";

for($i = 1; $i <= $nodes; $i++){
    
    $query = str_replace('#', $i, $consulta);
    
    //conectamos
    $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

    //hacemos la consulta
    $resultado=mysqli_query($conexion,$query);

    //obtenemos el registro en forma de array []id, temp, hum, flame, time]
    $row = mysqli_fetch_row($resultado);
    echo "Temp = " . $row[1] . " ";
    echo "Humi = " . $row[2] . " ";
    echo "Fire = " . $row[3] . " ";
    echo "Time = " . $row[4] . " ";
    
    if((($row[1] >= 30) &&  ($row[2] <= 50)) || ($row[3] <= 200)){
        include ("alerta.php");
        darAlarma($i, $row[1], $row[2], $row[3], $row[4]);
    }else{
        echo "No hay de qué preocuparse
        ";
    }
}
?>