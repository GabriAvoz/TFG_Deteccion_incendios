<?PHP
$hostname_localhost = "localhost";
$database_localhost = "sensor_station";
$username_localhost = "root";
$password_localhost = "";

$json=array();

	if(isset($_GET["temperature"]) && isset($_GET["humidity"]) && isset($_GET["flame"])){
		$temperature = $_GET['temperature'];
		$humidity = $_GET['humidity'];
        $flame = $_GET['flame'];
		
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		
        $insert = "INSERT INTO `node2`(`measurement`, `temperature`, `humidity`, `flame`, `time`) VALUES 
        (NULL,'{$temperature}','{$humidity}', '{$flame}',NULL)";
		$resultado_insert = mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$consulta = "SELECT * FROM `node1` WHERE `temperature` = '{$temperature}'";
			$resultado = mysqli_query($conexion,$consulta);
			
			if($registro = mysqli_fetch_array($resultado)){
				$json['node2'][]=$registro;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			$resulta["temperature"]='No Registra';
			$json['node2'][]=$resulta;
			echo json_encode($json);
		}
		
	}
	else{
			$resulta["temperature"]='WS No retorna';
			$json['node2'][]=$resulta;
			echo json_encode($json);
		}
?>