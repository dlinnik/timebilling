'use strict';

/* Filters */

angular.module('myApp.filters', []).
  filter('interpolate', ['version', function(version) {
    return function(text) {
      return String(text).replace(/\%VERSION\%/mg, version);
    };
  }])
// .filter('relativeUrl', ['contextPath', function(contextPath) {
//	 return function(url) {
//		 alert("contextPath + url = " + contextPath + url);
//		 return contextPath + url;
//	 };
// }])  
  ;
