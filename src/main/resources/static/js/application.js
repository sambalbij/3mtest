// app-module
(function () {
    'use strict';
    angular.module('m3testApp', [
        'ui.router',
        'm3test.dashboard',
        'm3test.eventdetails'
    ]);
})();

// app-config
(function () {
    'use strict';
    angular.module('m3testApp')
        .config(defaultRouteConfig);

    defaultRouteConfig.$inject = ['$urlRouterProvider','$logProvider'];

    function defaultRouteConfig($urlRouterProvider,$logProvider) {
        $urlRouterProvider.otherwise("/dashboard");
        $logProvider.debugEnabled(true);
    }
})();