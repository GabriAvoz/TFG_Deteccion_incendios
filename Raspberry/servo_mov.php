<?php
if(isset($_GET["mov"])){
	$mov = $_GET["mov"];
	$orden = "python /home/pi/Documents/servo_mov.py ";
	$cad = $orden . $mov;
	echo $cad;
	system($cad);
}
else{
	echo "Error";
}
?>
