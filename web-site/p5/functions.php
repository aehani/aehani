<?php
function currencyConverter($from_Currency,$to_Currency,$amount) {	
    $apikey = "e3aed929d48fb5f95b29";
	$from_Currency = urlencode($from_Currency);
	$to_Currency = urlencode($to_Currency);
    $query =  "{$from_Currency}_{$to_Currency}";
	$json = file_get_contents("https://free.currencyconverterapi.com/api/v6/convert?q={$query}&compact=ultra&apiKey={$apikey}");
    $obj = json_decode($json, true);
    $val = floatval($obj["$query"]);
    $total = $val * $amount;
    $total = number_format($total, 2, '.', '');
	$data = array( 'rate' => $val, 'converted_amount' =>$total, 'from_Currency' => strtoupper($from_Currency), 'to_Currency' => strtoupper($to_Currency));
	echo json_encode( $data );	
}
?> 

