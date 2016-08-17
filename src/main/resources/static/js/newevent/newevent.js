(function () {
    'use strict';
    angular.module('m3test.newevent', [
        'ui.router',
        'm3test.services'
    ]);
})();

(function () {
    'use strict';
    angular.module('m3test.newevent')
        .config(routeConfig);

    routeConfig.$inject = ['$stateProvider'];
    function routeConfig($stateProvider) {
        $stateProvider
            .state('newevent', {
                url: '/newevent',
                templateUrl: 'js/newevent/newevent.tpl.html',
                controller: 'NewEventCtrl',
                controllerAs: 'vm'
            });
    }
})();

(function () {
    'use strict';
    angular.module('m3test.newevent')
        .controller('NewEventCtrl', NewEventCtrl);

    NewEventCtrl.$inject = ['$log', 'eventsservice', '$location'];
    function NewEventCtrl($log, eventsservice, $location) {
        var vm = this;
        vm.create_event = create_event;

        vm.newEvent = {};

        function create_event() {
            vm.newEvent.finished = false;
            eventsservice.store_event(vm.newEvent, function (ok, message) {
                if (ok) {
                    vm.newEvent = {};
                    $location.url("/eventdetails/"+message);
                } else {
                    $log.error("Error while storing event", message);
                }
            });
        }
    }
})();