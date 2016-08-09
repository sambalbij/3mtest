(function () {
    'use strict';
    angular
        .module('m3test.services', []);
})();

(function () {
    'use strict';
    angular
        .module('m3test.services')
        .factory('eventsservice', EventsService);

    EventsService.$inject = ['$http', '$log'];

    function EventsService($http, $log) {
        var service = {
            obtain_events: obtain_events,
            store_event: store_event
        };

        return service;

        function obtain_events(callback) {
            $http.get('/event')
                .then(function (results) {
                    callback(results.data);
                }, function (error) {
                    $log.error("Error while obtaining events", error);
                    callback([]);
                });
        }

        function store_event(theEvent, callback) {
            $http.post('/event', theEvent)
                .then(function (results) {
                    callback("New event is stored");
                }, function (error) {
                    $log.error("Error while storing event", theEvent, error);
                    callback("Error while storing event");
                });
        }
    }
})();
