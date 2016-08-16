(function () {
    'use strict';
    angular.module('m3test.eventdetails', [
        'ui.router',
        'm3test.services',
        'ui.bootstrap'
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

    EventDetailsCtrl.$inject = ['$log', 'eventsservice', '$stateParams', '$uibModal'];
    function EventDetailsCtrl($log, eventsservice, $stateParams, $uibModal) {
        var vm = this;
        vm.obtain_event = obtain_event;
        vm.add_participant = add_participant;

        vm.event = {};
        vm.eventID = $stateParams.nodeId;

        obtain_event();

        function obtain_event() {
            eventsservice.obtain_event(vm.eventID, function (event) {
                vm.event = event;
                console.log(event);
            });
        }

        function add_participant(eventId) {
            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl: 'js/eventdetails/addparticipant.tpl.html',
                controller: 'AddParticipantCtrl',
                controllerAs: 'apVm'
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    eventsservice.add_participant_to_event(vm.eventID,result, function() {
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });

        }
    }
})();

(function () {
    'use strict';
    angular.module('m3test.eventdetails')
        .controller('AddParticipantCtrl', AddParticipantCtrl);

    AddParticipantCtrl.$inject = ['$log'];
    function AddParticipantCtrl($log) {

    }
})();

(function () {
    'use strict';

    angular.module('m3test.eventdetails')
        .controller('AddParticipantCtrl', AddParticipantCtrl);

    AddParticipantCtrl.$inject = ['$uibModalInstance'];

    function AddParticipantCtrl($uibModalInstance) {
        var apVm = this;
        apVm.participant = {};

        apVm.close = close;

        function close(result) {
            $uibModalInstance.close(result);
        }

    }
})();