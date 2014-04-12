// Modules configuration
requirejs.config({
    // Lib paths
    paths: {
        // Common libs
        'jquery': '../lib/jquery',
        'underscore': '../lib/underscore',
        'less': '../lib/less',

        // RequireJS plugins
        'text': '../require/text',
        'domReady': '../require/domReady',

        // AngularJS with plugins
        'angular': '../lib/angular/angular',
        'angular-resource': '../lib/angular/angular-resource',
        'angular-route': '../lib/angular/angular-route',
        'angular-animate': '../lib/angular/angular-animate',
        'angular-cookies': '../lib/angular/angular-cookies'
    },

    // Non-AMD libs exports and deps
    shim: {
        'jquery': {exports: ['$', 'jQuery']},
        'underscore': {exports: '_'},
        'less': {exports: 'less'},

        'angular': {exports: 'angular', deps: ['jquery']},
        'angular-resource': {deps: ['angular']},
        'angular-route': {deps: ['angular']},
        'angular-animate': {deps: ['angular']},
        'angular-cookies': {deps: ['angular']}
    }
});


// Application init
requirejs(
    [
        // Libs
        'jquery',
        'underscore',
        'less',
        'angular',
        'angular-resource',
        'angular-route',
        'angular-animate',
        'angular-cookies',

        // Application
        'app',
        'routes'
    ],
    function() {
    }
);
