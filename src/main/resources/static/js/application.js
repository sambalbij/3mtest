// app-module
(function () {
    'use strict';
    angular.module('m3testApp', [
        'ui.router',
        'm3test.dashboard'
    ]);
})();

// app-config
(function () {
    'use strict';
    angular.module('m3testApp')
        .config(defaultRouteConfig);

    defaultRouteConfig.$inject = ['$urlRouterProvider'];

    function defaultRouteConfig($urlRouterProvider) {
        $urlRouterProvider.otherwise("/dashboard");
    }
})();