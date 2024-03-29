
//현재 위치를 가져오는 함수
function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var userLocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var map = new google.maps.Map(document.getElementById('map'), {
                center: userLocation,
                zoom: 14 // Zoom level
            });
            var infowindow = new google.maps.InfoWindow();
            searchMedicalCenter(userLocation, map, infowindow);
        }, function () {
            handleLocationError(true);
        });
    } else {
        handleLocationError(false);
    }

}

//위치서비스 오류 처리 함수
function handleLocationError(browserHasGeolocation) {
    var userLocation = { lat: 0, lng: 0 };
    var map = new google.maps.Map(document.getElementById('map'), {
        center: userLocation,
        zoom: 14 // Default zoom level
    });
    var infowindow = new google.maps.InfoWindow();
    infowindow.setPosition(userLocation);
    infowindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    infowindow.open(map);
}

//사용자 주변의 병원 검색 함수
function searchMedicalCenter(location, map, infowindow) {
    var request = {
        location: location,
        rankBy: google.maps.places.RankBy.DISTANCE, // Sort by distance
        keyword: '안과'
    };

    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, function(results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            var medicalList = document.getElementById('medicalList');
            medicalList.innerHTML = '';

            // 최대 10개의 병원만 표시
            var resultsSlice = results.slice(0, 10);
            resultsSlice.forEach(function (place) {
                createMarker(place, map, infowindow);
                addListItem(place, medicalList, map, infowindow);
            });
        }
    });
}

//지도에 마커를 표시 함수
function createMarker(place, map, infowindow) {
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        title: place.name
    });

    var contentString = '<div><strong>' + place.name + '</strong></div>';
    var infoWindow = new google.maps.InfoWindow({
        content: contentString
    });

    marker.addListener('click', function () {
        infowindow.setContent(contentString);
        infowindow.open(map, marker);
    });

    // 마커 위에 정보창을 열어둠
    infoWindow.open(map, marker);

    return marker;
}


//병원 리스트 생성 함수
function addListItem(place, medicalList, map, infowindow) {
    var listItem = document.createElement('li');
    listItem.textContent = place.name;
    listItem.addEventListener('click', function () {
        map.setCenter(place.geometry.location);
        infowindow.setContent('<div><strong>' + place.name + '</strong></div>');
        infowindow.open(map);
    });
    medicalList.appendChild(listItem);
}
