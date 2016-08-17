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
            add_activity: add_activity,
            set_activity_cost: set_activity_cost,
            add_participant_to_event: add_participant_to_event,
            remove_participant_from_event:remove_participant_from_event,
            add_participant_to_activity: add_participant_to_activity,
            add_participant_to_item: add_participant_to_item,
            obtain_event_bill: obtain_event_bill,
            add_item_to_activity: add_item_to_activity,
            remove_activity_from_event: remove_activity_from_event,
            remove_item_from_activity:remove_item_from_activity
        };

        return service;

        function obtain_events(callback) {
            $http.get('/event')
                .then(function (results) {
                    $log.debug("Obtained events",results);
                    callback(results.data);
                    notificationService.info("Great now you see all events!");
                }, function (error) {
                    $log.error("Error while obtaining events", error);
                    notificationService.error("Oops, could not load all events!");
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

        function obtain_event_bill(eventId, callback) {
            $http.get('/event/' + eventId + '/bill')
                .then(function (result) {
                    $log.debug("Got bill for event "+eventId);
                    callback(result.data);
                }, function (error) {
                    $log.error("Error while obtaining event bill", eventId, error);
                    notificationService.error("Error obtaining bill for event " + eventId);
                });
        }

        function add_activity(eventId, activity, callback ){
            $http.post('/event/'+eventId+'/activity',activity)
                .then(function(results){
                    $log.debug("Added activity to event " + eventId, results);
                    notificationService.info("Added new activity");
                    callback();
                }, function(error){
                    $log.error("Error while adding activity",activity,error);
                    notificationService.error("Error while adding activity " + activity.name);
                });
        }

        function add_participant_to_event(eventId, participant, callback) {
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
        
        function remove_participant_from_event(eventId, participantId, callback) {
            $http.delete('/event/'+eventId+'/participant/'+participantId)
                .then(function (results) {
                    $log.debug("Result from removing participant", results);
                    notificationService.info("Removed participant '" + participantId + "'from event");
                    callback();
                }, function (error) {
                    $log.error("Error while removing participant", participantId, error);
                    notificationService.error("Error while removing participant '" + participantId + "' from event");
                });
            
        }

        function add_participant_to_activity(eventId, activityId, participantId, callback) {
            $http.post('/event/' + eventId + '/activity/'+ activityId + '/participant/'+participantId)
                .then(function (results) {
                    notificationService.info("Added new participant '" + participantId + "'to activity");
                    callback();
                }, function (error) {
                    $log.error("Error while adding participant to activity", participantId, error);
                    notificationService.error("Error while adding participant '" + participantId + "' to activity");
                });

        }

        function set_activity_cost(eventId, activityId, cost, callback) {

            $http.post('/event/' + eventId + '/activity/'+ activityId + '/cost',cost)
                .then(function (results) {

                    notificationService.info("Set cost for activity '" + activityId + "' to " + cost.cost);
                    callback();
                }, function (error) {

                    $log.error("Error setting activity cost", activityId, error);
                    notificationService.error("Error while setting cost for activity");
                });

        }

        function add_participant_to_item(eventId, activityId, itemId, participantId, callback) {
            $http.post('/event/' + eventId + '/activity/'+ activityId +'/item/' + itemId + '/participant/'+participantId)
                .then(function (results) {
                    notificationService.info("Added new participant '" + participantId + "'to item");
                    callback();
                }, function (error) {
                    $log.error("Error while adding participant to item", participantId, error);
                    notificationService.error("Error while adding participant '" + participantId + "' to item");
                });

        }

        function add_item_to_activity(eventId, activityId, item, callback) {
            $http.post('/event/' + eventId + '/activity/'+ activityId +'/item', item)
                .then(function (results) {
                    notificationService.info("Added " + item.name + "to activity " + activityId);
                    callback();
                }, function(error){
                    $log.error("Error adding item " + item.name, item, error);
                    notificationService.error("Error adding item to activity");
                });
        }

        function remove_activity_from_event(eventId, activityId, callback){
            $http.delete('/event/'+eventId+'/activity/'+activityId)
                .then(function (results) {
                    $log.debug("Result from removing activity", results);
                    notificationService.info("Removed activity '" + activityId + "'from event");
                    callback();
                }, function (error) {
                    $log.error("Error while removing activity", activityId, error);
                    notificationService.error("Error while removing activity '" + activityId + "' from event");
                });
        }

        function remove_item_from_activity(eventId, activityId, itemId, callback){
            $http.delete('/event/'+eventId+'/activity/'+activityId+'/item/'+itemId)
                .then(function (results) {
                    $log.debug("Result from removing item", results);
                    notificationService.info("Removed item '" + itemId + "' from activity");
                    callback();
                }, function (error) {
                    $log.error("Error while removing item", activityId, error);
                    notificationService.error("Error while removing item '" + activityId + "' from activity");
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
