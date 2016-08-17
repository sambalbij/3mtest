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
    function BillDetailsCtrl($log, eventsservice, $stateParams) {
        var vm = this;
        vm.obtain_event = obtain_event;
        vm.obtain_event_bill = obtain_event_bill;
        vm.show_participant_bill = show_participant_bill;
        vm.hide = hide;

        vm.event = {};
        vm.expanded = false;
        vm.eventID = $stateParams.nodeId;
        vm.bill={};
        vm.shown_participant = 1;

        obtain_event();
        obtain_event_bill();

        function obtain_event() {
            eventsservice.obtain_event(vm.eventID, function (event) {
                vm.event = event;
                console.log(event);
            });
        }

        function obtain_event_bill(){
            //if I do this it returns 'undefined'. If I remove bill from function (ie. function() instead), it gives an error: bill is undefined.
            eventsservice.obtain_event_bill(vm.eventID, function(bill) {
                vm.bill = bill;
                console.log(bill);
            })
        }

        function show_participant_bill(participantId){
            vm.shown_participant = participantId;
            vm.expanded = true;
            console.log("show participant "+ participantId);
        }

        function hide(){
            vm.expanded = false;
        }
    }
})();