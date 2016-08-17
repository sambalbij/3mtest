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
        vm.add_activity = add_activity;
        vm.set_activity_cost = set_activity_cost;
        vm.remove_participant_from_event = remove_participant_from_event;
        vm.add_participant_to_activity = add_participant_to_activity;
        vm.remove_participant_from_activity = remove_participant_from_activity;
        vm.add_participant_to_item = add_participant_to_item;
        vm.remove_participant_from_item = remove_participant_from_item;
        vm.remove_activity_from_event = remove_activity_from_event;
        vm.remove_item_from_activity = remove_item_from_activity;
        vm.add_item_to_activity = add_item_to_activity;

        vm.event = {};
        vm.eventID = $stateParams.nodeId;

        obtain_event();

        function obtain_event() {
            eventsservice.obtain_event(vm.eventID, function (event) {
                vm.event = event;
            });
        }

        function add_activity() {
            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl: 'js/eventdetails/addactivity.tpl.html',
                controller: 'AddActivityCtrl',
                controllerAs: 'aaVm'
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    eventsservice.add_activity(vm.eventID, result, function () {
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });
        }

        function remove_activity_from_event(activityId) {
            eventsservice.remove_activity_from_event(vm.eventID, activityId, function () {
                obtain_event();
            });
        }

        function set_activity_cost(activityId) {
            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl: 'js/eventdetails/setactivitycost.tpl.html',
                controller: 'SetActivityCostCtrl',
                controllerAs: 'sacVm'
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    eventsservice.set_activity_cost(vm.eventID, activityId,result, function() {
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });
        }

        function add_item_to_activity(activityId) {
            var opts = {
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                templateUrl: 'js/eventdetails/additemtoactivity.tpl.html',
                controller: 'AddItemToActivityCtrl',
                controllerAs: 'aitaVm'
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    eventsservice.add_item_to_activity(vm.eventID, activityId, result, function () {
                        console.log("added item to activity");
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });
        }

        function remove_item_from_activity(activityId, itemId) {
            eventsservice.remove_item_from_activity(vm.eventID, activityId, itemId, function () {
                obtain_event();
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
                    eventsservice.add_participant_to_event(vm.eventID, result, function () {
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });
        }

        function remove_participant_from_event(participantId) {
            eventsservice.remove_participant_from_event(vm.eventID, participantId, function () {
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
                    participants: function () {
                        return angular.copy(vm.event.participants);
                    }
                }
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    // TODO obtain participantId from result
                    console.log(result);
                    eventsservice.add_participant_to_activity(vm.eventID, activityId, result, function () {
                        console.log("Opgeslagen");
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });
        }

        function  remove_participant_from_activity(activityId, participantId) {
            eventsservice.remove_participant_from_activity(vm.eventID, activityId, participantId, function () {
                obtain_event();
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
                    participants: function () {
                        return angular.copy(vm.event.participants);
                    }
                }
            };
            var modalInstance = $uibModal.open(opts);
            modalInstance.result.then(function (result) {
                if (result) {
                    console.log(result);
                    eventsservice.add_participant_to_item(vm.eventID, activityId, itemId, result, function () {
                        console.log("Opgeslagen");
                        obtain_event();
                    });
                }
            }, function () {
                // Nothing to do here
            });

        }

        function  remove_participant_from_item(activityId, itemId, participantId) {
            eventsservice.remove_participant_from_item(vm.eventID, activityId, itemId, participantId, function () {
                obtain_event();
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
        .controller('AddActivityCtrl', AddActivityCtrl);

    AddActivityCtrl.$inject = ['$uibModalInstance'];

    function AddActivityCtrl($uibModalInstance) {
        var aaVm = this;
        aaVm.activity = {};

        aaVm.close = close;

        function close(result) {
            $uibModalInstance.close(result);
        }

    }
})();

(function () {
    'use strict';

    angular.module('m3test.eventdetails')
        .controller('SetActivityCostCtrl', SetActivityCostCtrl);

    SetActivityCostCtrl.$inject = ['$uibModalInstance'];

    function SetActivityCostCtrl($uibModalInstance) {
        var sacVm = this;
        sacVm.cost = {cost: 0.0};

        sacVm.close = close;

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

(function () {
    'use strict';

    angular.module('m3test.eventdetails')
        .controller('AddItemToActivityCtrl', AddItemToActivityCtrl);

    AddItemToActivityCtrl.$inject = ['$uibModalInstance'];

    function AddItemToActivityCtrl($uibModalInstance) {
        var aitaVm = this;
        aitaVm.item = {};

        console.log(aitaVm);

        aitaVm.close = close;

        function close(result) {
            $uibModalInstance.close(result);
        }

    }
})();