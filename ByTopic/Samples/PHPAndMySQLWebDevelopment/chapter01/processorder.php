<!DOCTYPE html>
<html>
<head>
<title>Bob's Auto Parts - Order Results</title>
</head>
<body>
<h1>Bob's Auto Parts</h1>
<h2>Order Results</h2>
<?php

define('TIRE_PRICE', 100);
define('OIL_PRICE', 10);
define('SPARK_PRICE', 4);

$tire_quantity = $_POST['tireqty'];
$oil_quantity = $_POST['oilqty'];
$spark_quantity = $_POST['sparkqty'];

$total_quantity = $tire_quantity + $oil_quantity + $spark_quantity;
$total_amount = $tire_quantity * TIRE_PRICE + $oil_quantity * OIL_PRICE + $spark_quantity * SPARK_PRICE;

$tax_rate = 0.10;
$total_amount *= (1 + $tax_rate);

echo 'Your order is follows: <br/>';
echo $tire_quantity . ' tires<br/>';
echo $oil_quantity . ' bottles of oil<br/>';
echo $spark_quantity . ' spark plugs<br/>';
echo '<br/>Items ordered: '.$total_quantity.'<br/>';
echo 'Subtotal: $'.number_format($total_amount, 2).'<br/>';
echo 'Total including tax: $'.number_format($total_amount, 2).'<br/>';
echo '<p>Order processed at '.date('H:i, jS F Y').'</p>';
?>
</body>
</html>
