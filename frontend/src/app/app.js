define(
    [
        'angular',
        'pages/pages_index',
        'common/directives/directives'
    ],
    function(angular) {
        'use strict';
        return angular.module('TimeBilling', [
            'ngRoute',
            'TimeBilling.Pages'
        ]);
    }
);
