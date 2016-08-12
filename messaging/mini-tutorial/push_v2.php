<?php

$notification['title'] = "Economiza Club";
$notification['icon']  = "ic_notifi";
$notification['sound'] = "defaultSoundUri";
$notification['body'] = "R$ 3.89 FRUKI GUARANA GARRAFA PET 2 L";

$ar1['NOMELOCAL'] = "COMPANHIA ZAFFARI COM\u00c9RCIO E IND\u00daSTRIA";
$ar1['ENDERECO'] = "AVENIDA OTTO NIEMEYER, 601";
$ar1['BAIRRO'] = "TRISTEZA";
$ar1['CIDADE'] = "PORTO ALEGRE";
$ar1['UF'] = "RS";
$ar1['VALOR'] = "3.69";

$ar2['NOMELOCAL'] = "BOURBON IPIRANGA";
$ar2['ENDERECO'] = "AVENIDA IPIRANGA, 5200";
$ar2['BAIRRO'] = "AZENHA";
$ar2['CIDADE'] = "PORTO ALEGRE";
$ar2['UF'] = "RS";
$ar2['VALOR'] = "3.79";

$ar3['NOMELOCAL'] = "COMPANHIA ZAFFARI COM\u00c9RCIO E IND\u00daSTRIA";
$ar3['ENDERECO'] = "RUA GENERAL LIMAESILVA, 606\u00a0";
$ar3['BAIRRO'] = "CENTRO HISTORICO";
$ar3['CIDADE'] = "PORTO ALEGRE";
$ar3['UF'] = "RS";
$ar3['VALOR'] = "3.89";

$data['tipo'] = "1";
$data['idNotif'] = "1";
$data['imagem'] = "http:\/\/economiza.club\/app\/application\/arqimgprdt\/7896436100062.jpg";
$data['descPrdt'] = "FRUKI GUARAN\u00c1 GARRAFA PET 2 L";
$data['valorDe'] = "3.69";
$data['valorAte'] = "5.00";
$data['dados'] = [$ar1,$ar2,$ar3];

if(!empty($_GET["push"])) {
		$gcmRegID  = file_get_contents("GCMRegId.txt");
		$pushMessage = $_POST["message"];
		if (isset($gcmRegID) && isset($pushMessage)) {
			$gcmRegIds = array($gcmRegID);
			$message = array("message" => $pushMessage);
			$pushStatus = sendPushNotificationToGCM($gcmRegIds, $message);
		}
	}

// API access key from Google API's Console

define('API_ACCESS_KEY','AIzaSyCSY-EXEMPLO-YYYY7Q0hJ-A'); /// econ firebase

$registrationIds = array($_GET["token"]);
// prep the bundle
$fields = array
(
	'registration_ids' 	=> $registrationIds,
	'notification'		=> $notification,
	'data'		=> $data
);

$headers = array
(
	'Authorization: key=' . API_ACCESS_KEY,
	'Content-Type: application/json'
);

$ch = curl_init();

curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );

$result = curl_exec($ch );
curl_close( $ch );
echo $result;
echo "<br>";
echo json_encode( $headers );
echo "<br>";
echo json_encode( $fields );

?>