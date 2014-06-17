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
  .filter('groupBy', ['$parse', function ($parse) {
    return function (list, group_by) {
      var filtered = [];
      var prev_item = null;
      var group_changed = false;
      var new_field = 'group_by_CHANGED';

      angular.forEach(list, function (item) {
        group_changed = false;

        if (prev_item !== null) {
          group_by = angular.isArray(group_by) ? group_by : [group_by];

          for (var i = 0, len = group_by.length; i < len; i++) {
            if ($parse(group_by[i])(prev_item) !== $parse(group_by[i])(item)) {
              group_changed = true;
            }
          }
        }
        else {
          group_changed = true;
        }

        if (group_changed) {
          item[new_field] = true;
        } else {
          item[new_field] = false;
        }

        filtered.push(item);
        prev_item = item;
      });

      return filtered;
    }
  }]);
// .filter('relativeUrl', ['contextPath', function(contextPath) {
//	 return function(url) {
//		 alert("contextPath + url = " + contextPath + url);
//		 return contextPath + url;
//	 };
// }])