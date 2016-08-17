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
        vm.remove_participant_from_event = remove_participant_from_event;
        vm.add_participant_to_activity = add_participant_to_activity;
        vm.add_participant_to_item = add_participant_to_item;

        vm.event = {};
        vm.eventID = $stateParams.nodeId;

        obtain_event();

        function obtain_event() {
            eventsservice.obtain_event(vm.eventID, function (event) {
                vm.event = event;
            });
        }

        function add_participant() {
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

        function remove_participant_from_event(participantID){
            eventsservice.remove_participant_from_event(vm.eventID,participantID,function() {
                obtain_event();
            });
        }

        function add_participant_to_activity(activityId) {
            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl: 'js/eventdetails/addparticipanttoactivity.tpl.html',
                controller: 'AddParticipantToActivityCtrl',
                controllerAs: 'aptaVm',
                resolve: {
                    participants: function() {
                        return angular.copy(vm.event.participants);
                    }
                }
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    // TODO obtain participantId from result
                    console.log(result);
                    eventsservice.add_participant_to_activity(vm.eventID,activityId,result, function() {
                        console.log("Opgeslagen");
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });

        }

        function add_participant_to_item(activityId, itemId) {
            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl: 'js/eventdetails/addparticipanttoitem.tpl.html',
                controller: 'AddParticipantToItemCtrl',
                controllerAs: 'aptiVm',
                resolve: {
                    participants: function() {
                        return angular.copy(vm.event.participants);
                    }
                }
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    console.log(result);
                    eventsservice.add_participant_to_item(vm.eventID, activityId, itemId, result, function() {
                        console.log("Opgeslagen");
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

(function () {
    'use strict';

    angular.module('m3test.eventdetails')
        .controller('AddParticipantToActivityCtrl', AddParticipantToActivityCtrl);

    AddParticipantToActivityCtrl.$inject = ['$uibModalInstance', 'participants'];

    function AddParticipantToActivityCtrl($uibModalInstance, participants) {
        var aptaVm = this;
        aptaVm.participants = participants;

        console.log(aptaVm);

        aptaVm.close = close;

        function close(result) {
            $uibModalInstance.close(result);
        }

    }
})();

(function () {
    'use strict';

    angular.module('m3test.eventdetails')
        .controller('AddParticipantToItemCtrl', AddParticipantToItemCtrl);

    AddParticipantToItemCtrl.$inject = ['$uibModalInstance', 'participants'];

    function AddParticipantToItemCtrl($uibModalInstance, participants) {
        var aptiVm = this;
        aptiVm.participants = participants;

        console.log(aptiVm);

        aptiVm.close = close;

        function close(result) {
            $uibModalInstance.close(result);
        }

    }
})();