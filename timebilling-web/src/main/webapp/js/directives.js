'use strict';

/* Directives */

angular.module('myApp.directives', [])
  .directive('datapicker', function(utils) {
    return {
      restrict : 'A',
      link : function($scope, $element, $attrs, $projectCtrl) {
        var icon = $element.find('.icon-calendar');
        var datepickerHolder = $element.find('.calendar-holder');

        datepickerHolder.datepicker({
          dateFormat: 'dd/mm/yy',
          onSelect: function(dateText, inst){
            $scope.$apply( function() {
              $scope.day = inst.currentDay;
              $scope.month = utils.monthForField(inst.currentMonth);
              $scope.year = inst.currentYear;
            });
            datepickerHolder.hide();
          }
        });

        icon.on('click', function(e){
          if(datepickerHolder.is(':visible')){
            datepickerHolder.hide();
          } else{
            datepickerHolder.show();
          }

          e.preventDefault();
        });
      }
    };
  });
