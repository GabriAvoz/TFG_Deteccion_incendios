<?PHP
$hostname_localhost ="localhost";
$database_localhost ="sensor_station";
$username_localhost ="root";
$password_localhost ="";

$json=array();
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		$consulta="SELECT * FROM `node1` ORDER BY measurement DESC LIMIT 50";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['node1'][]=$registro;
			//echo $registro['ID'].' - '.$registro['English'].'<br/>';
		}
		mysqli_close($conexion);
		echo json_encode($json);
?>