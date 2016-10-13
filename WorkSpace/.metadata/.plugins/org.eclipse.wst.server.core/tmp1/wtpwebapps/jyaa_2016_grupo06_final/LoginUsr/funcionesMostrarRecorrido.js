var map;
var myURI = "/jyaa_2016_grupo06_final/rest/rutas/3";
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
function initialize() 
{
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

	puntos[puntos.length] = position;
	
	if(cont==0)
	{
		mapProp = {
				center : new google.maps.LatLng(dato.lat, dato.lon, 18),
				zoom : 10,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
		cont++;
	}
	
	marker.setMap(map);
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

function dibujarRecorridoCircular() {

	markers = puntos;
	markers[markers.length] = puntos[0];

	var flightPath = new google.maps.Polyline({
		path : markers,
		strokeColor : "#0000FF",
		strokeOpacity : 0.8,
		strokeWeight : 2
	});

	flightPath.setMap(map);
}

