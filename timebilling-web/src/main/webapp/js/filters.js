'use strict';

/* Filters */

angular.module('myApp.filters', [])
  .filter('interpolate', ['version', function(version) {
    return function(text) {
      return String(text).replace(/\%VERSION\%/mg, version);
    };
  }])
  .filter('monthName', [ function() {
    return function(month) {
      var monthNames = [ 'Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август',
        'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь' ];
      return monthNames[month];
    };
  }])
// .filter('relativeUrl', ['contextPath', function(contextPath) {
//	 return function(url) {
//		 alert("contextPath + url = " + contextPath + url);
//		 return contextPath + url;
//	 };
// }])  
  ;
