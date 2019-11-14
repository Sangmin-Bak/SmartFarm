<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");
	mysqli_set_charset($con, "utf8");
	$rs = mysqli_query($con, "SELECT * FROM weather_information");
	
	$weather_data = mysqli_fetch_array($rs);
//	echo $weather_data[0];
	
	
	$data = array("지역"=>$weather_data[0],
		"기온"=>$weather_data[1],
		"날씨"=>$weather_data[4]
	);	
	
	header('Content-Type: application/json; charset=utf8');
	$json = json_encode($data, JSON_UNESCAPED_UNICODE);
	echo $json;
	
	
	mysqli_close($con);
?>	
