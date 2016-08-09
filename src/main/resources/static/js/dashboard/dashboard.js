// bar-module
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

    DashboardCtrl.$inject = ['$http', 'eventsservice'];
    function DashboardCtrl($http, eventsservice) {
        var vm = this;
        vm.obtain_events = obtain_events;

        vm.welcome = "Hello to you from the Angular world!";
        vm.events = [];

        obtain_events();

        function obtain_events() {
            eventsservice.obtain_events(function (events) {
                vm.events = events;
            });
        }
    }
})();