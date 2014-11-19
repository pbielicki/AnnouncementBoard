google.load("maps", "2.x");
google.setOnLoadCallback(initialize);

var map = null;
var geocoder = null;
var marker = null;

function initialize() {
	var latitude = document.getElementById("latitude");
	var longitude = document.getElementById("longitude");
	if (GBrowserIsCompatible()) {
		var mapOptions = {mapTypes:[G_NORMAL_MAP, G_SATELLITE_MAP, G_HYBRID_MAP]};
		map = new GMap2(document.getElementById("mapDiv"), mapOptions);

		var center = new GLatLng(51.919438, 19.145136);
		var zoom = 5;
		if (latitude.value != null && parseFloat(latitude.value)!= 0.0 && isNaN(parseFloat(latitude.value)) == false
			&& longitude.value != null && parseFloat(longitude.value) != 0.0 && isNaN(parseFloat(longitude.value)) == false) {

			center = new GLatLng(parseFloat(latitude.value), parseFloat(longitude.value));
			zoom = 14;
		}
		
		map.setCenter(center, zoom);
		map.setMapType(G_NORMAL_MAP);
	  	map.enableDoubleClickZoom();
		map.enableContinuousZoom();
		map.enableScrollWheelZoom();
		map.addControl(new GScaleControl());
		map.addControl(new GSmallZoomControl());
		map.addControl(new GHierarchicalMapTypeControl());
		geocoder = new GClientGeocoder();

      	marker = new GMarker(center, {draggable: true});
  	
        GEvent.addListener(marker, "dragend", function() {
			latitude.value = marker.getLatLng().lat();
			longitude.value = marker.getLatLng().lng();
      	});
        map.addOverlay(marker);
	}
}

function extractAddress() {
	var streetAddress = document.getElementById("address").value;
	var postalCode = document.getElementById("postalCode").value;
	var city = document.getElementById("city").value;

	var address = "";
	if (streetAddress != null && streetAddress != "") {
		address += streetAddress + ", ";
	}
	if (postalCode != null && postalCode != "") {
		address += postalCode + ", ";
	}
	if (city != null && city != "") {
		address += city;
	}

	return address;
}

function showAddress() {
	var address = extractAddress();
    
	var latitude = document.getElementById("latitude");
	var longitude = document.getElementById("longitude");
	var countryCode = document.getElementById("countryCode");
	
	if (geocoder != null) {
		geocoder.getLocations(
			address,
			function(response) {
				if (!response || response.Status.code != 200) {
					alert('<s:text name="alert.address" /> \"' + address + '\" <s:text name="alert.notFound" />');
				} else {
					var place = response.Placemark[0];
					countryCode.value = place.AddressDetails.Country.CountryNameCode;
						
					var point = new GLatLng(place.Point.coordinates[1],
			                          place.Point.coordinates[0]);
					latitude.value = point.lat();
					longitude.value = point.lng();						

					if (marker != null) {
						map.removeOverlay(marker);
					}
					marker = new GMarker(point, {draggable: true});
					GEvent.addListener(marker, "dragend", function() {
						latitude.value = marker.getLatLng().lat();
						longitude.value = marker.getLatLng().lng();
					});

					map.addOverlay(marker);

					map.setCenter(point);
					var zoom = map.getZoom();
					switch (place.AddressDetails.Accuracy) {
						case 1:
							zoom = 5;
							break;
						case 2:
							zoom = 8;
							break;
						case 3:
							zoom = 10;
							break;
						case 4:
							zoom = 12;
							break;
						case 5:
							zoom = 14;
							break;
						case 6:
							zoom = 15;
							break;
						default:
							zoom = 16;
					}
					map.setZoom(zoom);
			    }
			}
		);
	}
}
    