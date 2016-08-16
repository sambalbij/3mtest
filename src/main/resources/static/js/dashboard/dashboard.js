(function () {
    'use strict';
    angular.module('m3test.dashboard', [
        'ui.router',
        'm3test.services'
    ]);
})();

(function () {
    'use strict';
    angular.module('m3test.dashboard')
        .config(routeConfig);

    routeConfig.$inject = ['$stateProvider'];
    function routeConfig($stateProvider) {
        $stateProvider
            .state('dashboard', {
                url: '/dashboard',
                templateUrl: 'js/dashboard/dashboard.tpl.html',
                controller: 'DashboardCtrl',
                controllerAs: 'vm'
            });
    }
})();

(function () {
    'use strict';
    angular.module('m3test.dashboard')
        .controller('DashboardCtrl', DashboardCtrl);

    DashboardCtrl.$inject = ['$log', 'eventsservice', '$location'];
    function DashboardCtrl($log, eventsservice, $location) {
        var vm = this;
        vm.obtain_events = obtain_events;
        vm.create_event = create_event;

        vm.events = [];
        vm.newEvent = {};

        obtain_events();

        function obtain_events() {
            eventsservice.obtain_events(function (events) {
                vm.events = events;
            });
        }

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