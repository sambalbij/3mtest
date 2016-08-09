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
            obtain_events: obtain_events
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

    }
})();
