define(['app', 'angular-route'], function (app) {
    'use strict';

    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/billing', {
            templateUrl: 'app/pages/billing/billing_template.html',
            controller: 'BillingController'
        });
        $routeProvider.when('/reports', {
            templateUrl: 'app/pages/reports/reports_template.html',
            controller: 'ReportsController'
        });
        $routeProvider.otherwise({
            redirectTo: '/billing'
        });
    }]);
});

