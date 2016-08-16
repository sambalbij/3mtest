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
            obtain_event: obtain_event,
            add_participant_to_event: add_participant_to_event
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
                    $log.debug("Result from posting new event", results);
                    callback(true, results.data.id);
                }, function (error) {
                    $log.error("Error while storing event", theEvent, error);
                    notificationService.error("Error while storing event");
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

        function add_participant_to_event(eventId, participant, callback) {
            participant.id =
            $http.post('/event/' + eventId + '/participant', participant)
                .then(function (results) {
                    $log.debug("Result from posting new event", results);
                    notificationService.info("Added new participant '" + participant.name + "'to event");
                    callback();
                }, function (error) {
                    $log.error("Error while adding participant", participant, error);
                    notificationService.error("Error while adding participant '" + participant.name + "' to event");
                });

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
            info: info,
            error: error
        };

        return service;


        function info(message) {
            broadcast("success", message);
        }

        function error(message) {
            broadcast("danger", message);
        }

        function broadcast(type, message) {
            $rootScope.$broadcast('msg:notification', type, message);
        }
    }
})();
