<?php
include('httpful.phar');
header("Content-type: application/json");
$json_str = file_get_contents('php://input');
$json_obj = (array)json_decode($json_str);

$dat0 = (array)$json_obj['queryResult'];
$dat1 = (array)$dat0['intent'];
$intentName = ($dat1['displayName']);

//$dat1 = (array)$dat0['parameters']; //result//originalRequest
//$poll = ($dat1['gas']);
//$arr = array('fulfillmentText' => $intentName);
//echo json_encode($arr);

$url_path1 = 'https://tutorial-a111.restdb.io/rest/alert'; ///5aed60d4874aa85a0000838b
$url_path2 = 'https://tutorial-a111.restdb.io/rest/assistant/5aed5862874aa85a0000834b';

$poll='';
if ($intentName === 'Pollution.check') {
	$url_path = $url_path1;
	$request = \Httpful\Request::get($url_path1);
	$poll = '1';
} else if($intentName === 'Due.date.service') {
	$url_path = $url_path1;
	$request = \Httpful\Request::get($url_path1);
	$poll = '2';
} else if ($intentName === 'Service.center') {
	$url_path = $url_path2;
	$request = \Httpful\Request::get($url_path2);
	$poll = '3';
} else if ($intentName === 'Last.service') {
	$url_path = $url_path2;
	$request = \Httpful\Request::get($url_path2);
	$poll = '4';
} else if ($intentName === 'Last.service.rating') {
	$url_path = $url_path2;
	$request = \Httpful\Request::get($url_path2);
	$poll = '5';
} else if ($intentName === 'Alternate.service.center') {
	$url_path = $url_path2;
	$request = \Httpful\Request::get($url_path2);
	$poll = '6';
} else if ($intentName === 'Service.reminder') {
	$url_path = $url_path2;
	$request = \Httpful\Request::get($url_path2);
	$poll = '7';
}  else if ($intentName === 'Feedback') {
	$url_path = $url_path2;
	$request = \Httpful\Request::get($url_path2);
	$poll = '8';
} 

//echo json_encode($dat['intent']);
//$url_path1 = 'https://gasstation-fd98.restdb.io/rest/gasstation/5adf8158b8552b5200003704?x-apikey=54cd27243fafb6350f8eccdb39447a9fb4573';

$request = $request->addHeader("apikey", "d426afcf53d499d49217ce81b06b7cb3bb559");
$request = $request->addHeader("Content-Type", "application/json");
$request = $request->addHeader("Access-Control-Allow-Origin", "*");
$response = $request->send();

$result = json_decode($response, true);

if($poll === '1') { //alert pollution check
	$resultarr = (array)$result;
	$result1 = $resultarr[0];
	$gas = $result1['CO_gas'];
	$co2 = $result1['CO2'];	
	$nox = $result1['NOX'];	
	$c6h6 = $result1['C6H6'];
	$nh4 = $result1['NH4'];
	$value1 = "Your vehicle has CO with $gas ppm Co2 with $co2 ppm NO2 with $nox ppm Benzene with $c6h6 ppm and Amonia with $nh4 ppm. It has violated Bharat stage IV threshold and your vehicle needs a service.";


} else if($poll === '2') {
	$resultarr = (array)$result;
	$result1 = $resultarr[0];
	$servicedate = $result1["DateTime"];
	$value1 = "You should visit Honda service center as service is due by 12 May 2018. Hurry Up";
} else if($poll === '3') { // Honda service center
	//$request = \Httpful\Request::get($url_path2);
	//$lastServiceAddress = (array)$result;
	//$lastServiceAddress1 = $lastServiceAddress['LastServiceAddress'];
	$lastServiceAddress = $result['LastServiceAddress'];
	$value1 = "Nearest Honda service center is at $lastServiceAddress.";
}  else if($poll === '4') { // last service
	$lastServiceAddress = $result['LastServiceAddress'];	
	$lastServiceCost = $result['LastServiceCost'];	
	$totalServiceCount = $result['TotalServiceCount'];	
	$value1 = "You had visited Honda service center at $lastServiceAddress on 10 Apr 2018. Service charges were Rupees $lastServiceCost. In last four months you had gone for service five times so you need to better change the vehicle or service station.";
}  else if($poll === '5') { // last service rating
	$lastServiceAddress = $result['LastServiceAddress'];	
	$lastServiceCost = $result['LastServiceCost'];	
	$value1 = "$lastServiceAddress Honda service center rating is 3 out of 5 as per 2017 Government Automobile Association records";
}   else if($poll === '6') { // Alternate.service.center
	$altServiceAddress = $result['AltServiceAddress'];	
	$value1 = "Best other Honda service center is at $altServiceAddress";
}   else if($poll === '7') { // service.reminder
	$busyDays = $result['BusyDays'];
	$availableDays = $result['AvailableDays'];
	$value1 = "You are busy on next $busyDays but available on $availableDays. Shall I set reminder for next Thursday?";
}  else if($poll === '8') { // feedback
	$value1 = "You are obsessed with environment, you should better change your city. I recommend go to Himalayas.";
} 

$arr = array('fulfillmentText' => $value1);
echo json_encode($arr);

/*
//$request = $request->addHeader("_csrfToken","ASIex4up-3VZpzTkPdPk7YsNYnreZtnnxGO4");
//$request = $request->addHeader("_ga","GA1.2.1666414233.1524596224");
//$request = $request->addHeader("JSessionId","s%3A1XCX9EIyeadReHZshQpHsmOb-W0DWNZN.0YtEnbPRxXdHX0eXStlenq1KiPBo0p%2Bf1R9fc%2FtB9y0");

$client = curl_init($url_path);
curl_setopt($client, CURLOPT_RETURNTRANSFER,true);
$response = curl_exec($client);
$result = json_decode($response);
echo $response; 
*/ 
/*
foreach (getallheaders() as $name => $value) {
    echo "$name: $value\n";
}
if(isset($_GET["pollution"])){
	$poll = $_GET["pollution"];
}*/

/*

$uri = "https://www.googleapis.com/freebase/v1/mqlread?query=%7B%22type%22:%22/music/artist%22%2C%22name%22:%22The%20Dead%20Weather%22%2C%22album%22:%5B%5D%7D";
//$response = \Httpful\Request::get($url_path)->send();

$url_path = 'https://gasstation-fd98.restdb.io/rest/gasstation/5adf8158b8552b5200003704?key=5ae3141325a622ae4d528663';
$restclient = new RestClient($url_path);

//https://gasstation-fd98.restdb.io/rest/gasstation/5adf8158b8552b5200003704?key=5ae3141325a622ae4d528663

// Sample GET
//$response = $restclient->execute(RestClient::REQUEST_TYPE_GET, '/api/path', array( 'q' => 'query', ));
$response = $restclient->execute(RestClient::REQUEST_TYPE_POST, $url_path , array('_id' =>'5adf8158b8552b5200003704', 'key' => '5ae3141325a622ae4d528663'), null, null, null);
$response1 = json_encode($response);
echo ( $response1 );

*/
/*
$headerKey = "HTTP_POLLUTION";
$test = getallheaders();
if ( array_key_exists($headerKey, $test) ) {
	$headerValue = $test[ $headerKey ];
}
*/
?>