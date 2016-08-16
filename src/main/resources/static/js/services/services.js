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
            store_event: store_event,
            obtain_event: obtain_event
        };

        return service;

        function obtain_events(callback) {
            $http.get('/event')
                .then(function (results) {
                    $log.debug("Obtained events",results);
                    callback(results.data);
                }, function (error) {
                    $log.error("Error while obtaining events", error);
                    callback([]);
                });
        }

        function store_event(theEvent, callback) {
            // TODO: Think about using broadcasting for error handling
            $http.post('/event', theEvent)
                .then(function (results) {
                    $log.debug("Result from postinng new event", results);
                    callback(true);
                }, function (error) {
                    $log.error("Error while storing event", theEvent, error);
                    callback(false, "Error while storing event");
                });
        }

        function obtain_event(id, callback){
            $http.get('event/'+id)
                .then(function (result) {
                    $log.debug("Obtained event with id" + id, result);
                    callback(result.data);
                }, function (error){
                    $log.error("Error while obtaining event with id " + id, error);
                    callback({});
                })
        }
    }
})();
