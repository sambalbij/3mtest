(function () {
    'use strict';
    angular.module('m3test.eventdetails', [
        'ui.router',
        'm3test.services'
    ]);
})();

(function () {
    'use strict';
    angular.module('m3test.eventdetails')
        .config(routeConfig);

    routeConfig.$inject = ['$stateProvider'];
    function routeConfig($stateProvider) {
        $stateProvider
            .state('eventdetails', {
                url: '/eventdetails/:nodeId',
                templateUrl: 'js/eventdetails/eventdetails.tpl.html',
                controller: 'EventDetailsCtrl',
                controllerAs: 'vm'
            });
    }
})();

(function () {
    'use strict';
    angular.module('m3test.eventdetails')
        .controller('EventDetailsCtrl', EventDetailsCtrl);

    EventDetailsCtrl.$inject = ['$log', 'eventsservice', '$stateParams'];
    function EventDetailsCtrl($log, eventsservice,$stateParams) {
        var vm = this;
        vm.obtain_event = obtain_event;

        vm.event = {};
        vm.eventID = $stateParams.nodeId;

        obtain_event();

        function obtain_event() {
            eventsservice.obtain_event(vm.eventID, function (event) {
                vm.event = event;
                console.log(event);
            });
        }
    }
})();