let map;
const infoWindows = [];

function initMap() {
    // 로딩 화면을 표시합니다.
    showLoadingScreen();


    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12 // 초기 확대 수준 설정
    });



    // 사용자의 현재 위치를 가져옵니다.
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            const pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            // 지도의 중심을 사용자의 위치로 설정합니다.
            map.setCenter(pos);

            function getValueFromHiddenInput() {
                // hidden input 요소에서 값 가져오기
                var scriptOutput2Value = document.getElementById('scriptOutput2').value;
                console.log("scriptOutput2 값: " + scriptOutput2Value);

                // 이 값을 사용하여 원하는 작업 수행
                // 예를 들어, 가져온 값으로 다른 처리를 할 수 있습니다.
            }

            // 정형외과를 검색합니다.
            const request = {
                location: pos,
                radius: '5000', // 5km 반경 내에서 검색합니다.
                query: scriptOutput2Value
            };

            const service = new google.maps.places.PlacesService(map);
            service.textSearch(request, function(results, status) {
                if (status === google.maps.places.PlacesServiceStatus.OK) {
                    // 가장 가까운 10개의 결과만 사용합니다.
                    const nearbyResults = results.slice(0, 10);
                    nearbyResults.forEach(function(result) {
                        createMarker(result);
                        addToList(result);
                    });
                }

                // 로딩 화면을 숨깁니다.
                hideLoadingScreen();
            });
        }, function() {
            handleLocationError(true, map.getCenter());

            // 로딩 화면을 숨깁니다.
            hideLoadingScreen();
        });
    } else {
        // 브라우저가 Geolocation을 지원하지 않을 경우
        handleLocationError(false, map.getCenter());

        // 로딩 화면을 숨깁니다.
        hideLoadingScreen();
    }
}

function createMarker(place) {
    const marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        title: place.name
    });

    const infowindow = new google.maps.InfoWindow({
        content: '<div><strong>' + place.name + '</strong></div>'
    });

    infoWindows.push(infowindow);

    infowindow.open(map, marker);

    marker.addListener('click', function() {
        closeInfoWindows();
        infowindow.open(map, marker);
    });
}

function closeInfoWindows() {
    infoWindows.forEach(function(infowindow) {
        infowindow.close();
    });
}

function handleLocationError(browserHasGeolocation, pos) {
    const infoWindow = new google.maps.InfoWindow({ map: map });

    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Geolocation 서비스를 사용할 수 없습니다.' :
        '브라우저가 Geolocation을 지원하지 않습니다.');
}

// 병원을 리스트에 추가하는 함수
function addToList(place) {
    const hospitalList = document.getElementById('hospitals');
    const listItem = document.createElement('li');
    listItem.textContent = place.name;
    listItem.addEventListener('click', function() {
        // 클릭된 병원의 위치를 지도 중앙으로 설정합니다.
        map.setCenter(place.geometry.location);
        // 클릭된 병원의 정보 창을 엽니다.
        infoWindows.forEach(function(infowindow) {
            infowindow.close();
        });
        const infowindow = new google.maps.InfoWindow({
            content: '<div><strong>' + place.name + '</strong></div>'
        });
        infowindow.setPosition(place.geometry.location);
        infowindow.open(map);
        infoWindows.push(infowindow);
    });
    hospitalList.appendChild(listItem);
}

// 지도 로딩이 완료되면 로딩 화면을 숨깁니다.
google.maps.event.addDomListener(window, 'load', function() {
    var loadingScreen = document.getElementById('loading-screen');
    loadingScreen.style.display = 'none';
});
// 로딩 화면을 보여주는 함수
function showLoadingScreen() {
    document.getElementById('loading-screen').style.display = 'block';
}

// 로딩 화면을 숨기는 함수
function hideLoadingScreen() {
    document.getElementById('loading-screen').style.display = 'none';
}