var map;
var myURI = "/jyaa_2016_grupo06_final/rest/rutas/1";
var mapProp = {
	center : new google.maps.LatLng(-34.9038055, -57.9392111, 18),
	zoom : 5,
	mapTypeId : google.maps.MapTypeId.ROADMAP
};

var puntos = [];

// Evento
google.maps.event.addDomListener(window, 'load', initialize);

/**
 * Inicializa el mapa
 */
function initialize() {
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

	map.addListener('click', function(e) {
		agregarMarker(e.latLng, map);

	});

	puntos = [];
	obtenerMarkers();
}

// Obtiene markers y los dibuja
function obtenerMarkers(dibujar) {

	$.ajax({
		dataType : "json",
		url : myURI,

		success : function(result) {
			console.log("result " + result);
			puntos = [];
			$.each(result, function(i, dato) {
				console.log("dato" + i + dato);
				dibujarMarker(dato);

			});
			dibujarRecorrido();
		}
	});
}

var cont = 0;

function dibujarMarker(dato) {

	var position = new google.maps.LatLng(dato.lat, dato.lon);

	var marker = new google.maps.Marker({
		position : position,
		icon: {
		      path: google.maps.SymbolPath.CIRCLE,
		      scale: 3
		    },
		id : dato.id
	});

	marker.addListener("rightclick", function(point) {
		console.log("rigthclick");
		borrarMarker(dato.id);
		marker.setMap(null);
	});

	puntos[puntos.length] = position;

	if(cont==0)
	{
			
		map.setZoom(10);
		map.setCenter({lat: dato.lat, lng: dato.lon});
		cont++;
		latp=dato.lat;
		lonp=dato.lon;
	}
	
	
	marker.setMap(map);
}

function agregarMarker(latLng) {

	var punto = {
		lat : latLng.lat(),
		lon : latLng.lng()
	};

	$.ajax({

		data : punto,
		url : myURI,
		type : "POST",
		success : function(result) {
			obtenerMarkers();

		}
	});

}

function dibujarRecorrido() {

	var flightPath = new google.maps.Polyline({
		path : puntos,
		strokeColor : "#0000FF",
		strokeOpacity : 0.8,
		strokeWeight : 2
	});

	flightPath.setMap(map);
}

//function dibujarRecorridoCircular() {
//
//	markers = puntos;
//	markers[markers.length] = puntos[0];
//
//	var flightPath = new google.maps.Polyline({
//		path : markers,
//		strokeColor : "#0000FF",
//		strokeOpacity : 0.8,
//		strokeWeight : 2
//	});
//
//	flightPath.setMap(map);
//}

function limpiarMapa() {

	punto = {
		id : null
	};
	$.ajax({
		data : punto,
		url : myURI,
		type : "DELETE",
		success : function(result) {
			initialize();
		}
	});

}

function borrarMarker(id) {
	console.log("borrar marker " + id);
	punto = {
		id : id
	};
	$.ajax({
		data : punto,
		url : myURI+"/"+id ,
		type : "DELETE",
		success : function(result) {
			initialize2();
		}
	});
	
}

var latp;
var lonp;

function initialize2() {
	
	var mapProp2 = {
			center : new google.maps.LatLng(latp, lonp, 18),
			zoom : 10,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
	
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp2);

	map.addListener('click', function(e) {
		agregarMarker(e.latLng, map);

	});

	puntos = [];
	obtenerMarkers();
}
