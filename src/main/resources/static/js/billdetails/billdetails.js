(function () {
    'use strict';
    angular.module('m3test.billdetails', [
        'ui.router',
        'm3test.services',
        'ui.bootstrap'
    ]);
})();

(function () {
    'use strict';
    angular.module('m3test.billdetails')
        .config(routeConfig);

    routeConfig.$inject = ['$stateProvider'];
    function routeConfig($stateProvider) {
        $stateProvider
            .state('billdetails', {
                url: '/billdetails/:nodeId',
                templateUrl: 'js/billdetails/billdetails.tpl.html',
                controller: 'BillDetailsCtrl',
                controllerAs: 'vm'
            });
    }
})();

(function () {
    'use strict';
    angular.module('m3test.billdetails')
        .controller('BillDetailsCtrl', BillDetailsCtrl);

    BillDetailsCtrl.$inject = ['$log', 'eventsservice', '$stateParams'];
    function BillDetailsCtrl($log, eventsservice,$stateParams) {
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
})()