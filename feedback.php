<?php
// Specify domains from which requests are allowed
header('Access-Control-Allow-Origin: *');

// Specify which request methods are allowed
header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');

// Additional headers which may be sent along with the CORS request
header('Access-Control-Allow-Headers: X-Requested-With,Authorization,Content-Type');

// Set the age to 1 day to improve speed/caching.
header('Access-Control-Max-Age: 86400');
ini_set( 'display_errors', 1 );
error_reporting( E_ALL );

function optionalParam($key) {
    if (!isset($_GET[$key])) {
        return '';
    }

    return trim((string) $_GET[$key]);
}

function sendFeedBack() {
    if(!isset($_GET['message'])){
        http_response_code(400);
        exit();
    }
    $from = "feedback@gumsiz.xyz";
    $to = "gumsiz@yandex.com,fason@gumsiz.xyz";
    $senderMail = str_replace(array("\r", "\n"), '', optionalParam('email'));
    $feedbackMessage = optionalParam('message');

    // Optional app metadata for shared feedback endpoint across multiple apps.
    $appMetadata = array(
        'App ID' => optionalParam('app_id'),
        'App Name' => optionalParam('app_name'),
        'App Version' => optionalParam('app_version'),
        'Platform' => optionalParam('platform'),
        'Build Number' => optionalParam('build_number'),
        'Bundle ID' => optionalParam('bundle_id'),
        'App Data' => optionalParam('app_data'),
    );

    $metadataLines = array();
    foreach ($appMetadata as $label => $value) {
        if ($value !== '') {
            $metadataLines[] = $label . ': ' . $value;
        }
    }

    $subject = "Feedback From User";
    $mailInfo = ($senderMail !== '') ? $senderMail : 'Not provided (Anonymous)';
    $message = $feedbackMessage . ".\n My mail adress: ". $mailInfo;
    if (!empty($metadataLines)) {
        $message .= "\n\nApp Metadata:\n" . implode("\n", $metadataLines);
    }
    $headers = "From:" . $from;
    if(empty($to) || empty($subject) || empty($message)){
        http_response_code(400);
        exit();
    }
    if(mail($to,$subject,$message, $headers)) {
        http_response_code(200);
    } else {
        http_response_code(400);
    }
}

//function sendReportMarket($description, $date, $address, $details) {
function sendReportMarket() {
    if(!isset($_GET['description'],$_GET['date'],$_GET['address'])){
    http_response_code(400);
    exit();
    }
    $from = "feedback@gumsiz.xyz";
    $to = "gumsiz@yandex.com,fason@gumsiz.xyz";
    $subject = "Market From User";
    $message = "Description: " . $_GET['description'] . ". \n Date: ". $_GET['date'] . ". \n Address: ". $_GET['address'] . ". \n Details: ". $_GET['details'];
    $headers = "From:" . $from;
    if(empty($message)){
        http_response_code(400);
        exit();
    }
    if(mail($to,$subject,$message, $headers)) {
        http_response_code(200);
    } else {
        http_response_code(400);
    }
}

    if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
        http_response_code(200);
        exit();
    }

    $type = isset($_GET['type']) ? $_GET['type'] : '';

    switch ($type) {
        case 'feedback':
            sendFeedBack();
            break;
        case 'market':
            sendReportMarket();
            break;
        default:
            http_response_code(400);
    }
?>