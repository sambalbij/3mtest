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

    EventsService.$inject = ['$http', '$log', 'notificationService'];

    function EventsService($http, $log, notificationService) {
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
                    notificationService.add("info", "Great now you see all events!");
                }, function (error) {
                    $log.error("Error while obtaining events", error);
                    notificationService.add("error", "Oops, could not load all events!");
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
                    notificationService.add("error","Error while storing event");
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

(function () {
    'use strict';
    angular
        .module('m3test.services')
        .factory('notificationService', NotificationService);

    NotificationService.$inject = ['$rootScope'];

    function NotificationService($rootScope) {
        var service = {
            add: add
        };

        return service;

        // Implementations
        function add(type, message) {
            $rootScope.$broadcast('msg:notification', type, message);
        }
    }
})();
