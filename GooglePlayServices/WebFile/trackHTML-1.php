
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=drawing"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<?php 
if(isset($_REQUEST["location"])){
$request = $_REQUEST["location"];
 file_put_contents("HTMLLatLong.txt",print_r($request,true));

}else{
	$fp = fopen("HTMLLatLong.txt","r") or die("can't open the file");
	$request = fread($fp,filesize("HTMLLatLong.txt"));
	print_r($request);
	?>
	 <script type="text/javascript">
	 google.maps.event.addDomListener(window, 'load', initialize);
	 </script>
<?php }?>

<!DOCTYPE html>
<html>
  <head>
    <title>Drawing tools</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style type="text/css">
    
    #map-canvas {
    position:absolute;
    top:6%;
    left:2%;
     height:90%;
	 width:95%;
       border: 2px solid green; }
       h2
       {
        position:absolute;
        left:30%;
       color:#87ceeb;
       }
    </style>
    <script type="text/javascript">
    var infowindow = new google.maps.InfoWindow();
    google.maps.visualRefresh=true;
    var map;
    
      function initialize() {
          var request = <?php echo $request;?>;
          //alert (JSON.stringify(request));
    	  //var latlngs = JSON.parse(request);
    	  var latlngs = request;
        var mapOptions = {
        		center : new google.maps.LatLng(
						parseFloat(latlngs[0].latitude),
						parseFloat(latlngs[0].longitude)),
          zoom: 19,
          
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
         map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
         var lat_lng = new google.maps.LatLng(parseFloat(latlngs[0].latitude), parseFloat(latlngs[0].longitude));
         createmarker(lat_lng);
      }

      function createmarker(lat_lng)
      {
      	var marker = new google.maps.Marker({
      		position : lat_lng,
      				map : map,
      				title: "marker"
      	});
      	var content_str = "<strong><span style='color:green'>"+" Marker At </span><br/>"+
		"<span style='color:green'>"+lat_lng+"</span></strong>";
      	google.maps.event.addListener(marker, 'click', function() {
      		infowindow.setContent(content_str);
      		infowindow.open(map, marker);
      	});
      }
google.maps.event.addDomListener(window, 'load', initialize);

    </script>
    <meta http-equiv="refresh" content="15">
  </head>
  <body>
<h5><?php ?></h5> 
    <div id="map-canvas"></div>
  </body>
</html>