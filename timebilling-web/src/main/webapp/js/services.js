'use strict';

/* Services */

angular.module('myApp.services', ['ngResource'])
  .factory('currentUserFactory', function($resource) {
    return $resource('api/current_user', {}, {
      query: { method: 'GET', isArray: false }
    });
  })
  .factory('projectFactory', function($resource) {
    return $resource('api/project/:projectId', {}, {
      get: { method: 'GET', isArray: false }
    });
  })
  .factory('projectListFactory', function($resource) {
    return $resource('api/projects', {}, {
      query: { method: 'GET', isArray: true }
    });
  })
  .factory('billingFactory', function($resource) {
    return $resource('api/billing', {}, {
      query: { method: 'GET',	isArray: false }
    });
  })
  .factory('reportFactory', function($resource) {
    return $resource('api/billing/report/:id', { }, {
        create: { method: 'POST', params: { project: '@project', from: '@from', to: '@to' } },
        query: { method: 'GET', isArray: false }
    });
  })
  .factory('recordFactory', function($resource) {
    return {
      getSpents: function() {
        return $resource('api/services', { }, {
          query: { method: 'GET',	isArray: false }
        });
      },
      getCosts: function() {
        return $resource('api/expenses', { }, {
          query: { method: 'GET', isArray: false }
        });
      },
      spent: function() {
        return $resource('api/service', {}, {
          create: { method: 'POST' },
          update: { method: 'PUT' }
        });
      },
      cost: function() {
        return $resource('api/expense', {}, {
          create: { method : 'POST' },
          update: { method : 'PUT' }
        });
      }
    };
  })
  .factory('utils', function(){
    return {
      monthForField: function(month) {
        var m = month + 1;
        if(m < 10) {
          m = '0' + m;
        }
        return m;
      },

      monthForDate: function(month) {
        return month -1;
      },

      parseDate: function(strDate) {
        if(strDate){
          var dateParts = strDate.split('-');
          return new Date(dateParts[0], (dateParts[1] - 1), dateParts[2]);
        }
      }
    };
  });