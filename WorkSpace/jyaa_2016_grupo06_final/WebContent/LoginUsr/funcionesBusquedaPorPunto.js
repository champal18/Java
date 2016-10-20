var map;
var myURI = "/jyaa_2016_grupo06_final/rest/rutas/2";
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
		}
	});
}

function dibujarMarker(dato)
{	
	if(dato != null)
	{
	
	var position = new google.maps.LatLng(dato.lat, dato.lon);

	var marker = new google.maps.Marker({
		position : position,
		icon: {
		      path: google.maps.SymbolPath.CIRCLE,
		      scale: 3
		    },
		id : dato.id
	});

	puntos[0] = position;

	
		mapProp = {
				center : new google.maps.LatLng(dato.lat, dato.lon),
				zoom : 5,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
		
		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	
	marker.setMap(map);
	}
}

function agregarMarker(latLng)
{	
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