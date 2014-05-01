'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.directives',
  'myApp.controllers'
])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider
  .when('/project', {templateUrl: 'partials/projectList.html', controller: 'projectListCtrl'})
  .when('/project/:projectId', {templateUrl: 'partials/project.html', controller: 'projectCtrl'})
  .when('/billing', {templateUrl: 'partials/billing.html', controller: 'billingCtrl'})
  .otherwise({redirectTo: '/project'});
}]);

// Initialize Date Picker
$.datepicker.regional['ru'] = {
	closeText : 'Закрыть',
	prevText : '&#x3C;Пред',
	nextText : 'След&#x3E;',
	currentText : 'Сегодня',
	monthNames : [ 'Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
			'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь' ],
	monthNamesShort : [ 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг',
			'Сен', 'Окт', 'Ноя', 'Дек' ],
	dayNames : [ 'воскресенье', 'понедельник', 'вторник', 'среда', 'четверг',
			'пятница', 'суббота' ],
	dayNamesShort : [ 'вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт' ],
	dayNamesMin : [ 'Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб' ],
	weekHeader : 'Нед',
	dateFormat : 'dd.mm.yy',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : ''
};
$.datepicker.setDefaults($.datepicker.regional['ru']);
