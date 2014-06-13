'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('indexCtrl', function($scope, projectListFactory, currentUserFactory) {
    $scope.projects =
      projectListFactory.query(function() {
        $scope.totalProjects = $scope.projects.length;
      });

    $scope.user_details = currentUserFactory.query(function() {
      $scope.appName = $scope.user_details.appScreenName;
      $scope.user = $scope.user_details.username;
      if($scope.user_details.role == 1) {
        $scope.role = 'Администратор';
      } else {
            $scope.role = 'Пользователь';
      }
    });
		
    $scope.activePage = 'projects';
		
    $scope.onProjects = function() {
      $scope.activePage = 'projects';
    };
		
		$scope.onBilling = function(){
			$scope.activePage = 'billing';
		};
		    
	})
  .controller('projectListCtrl', function($scope, projectListFactory) {
    $scope.projects = projectListFactory.query();
  })
  .controller('addProjectCtrl', function($scope, projectFactory) {
  })
  .controller('projectCtrl', function($scope, $routeParams, recordFactory, projectFactory, utils) {
    $scope.mode = {
      projectId: $routeParams.projectId,
      adding: 0,  // Поле добавления новой записи
      editId: -1, // Запись, которая редактируется
      selected: 'spent' // Таб, который открыт
    };

    $scope.project = projectFactory.get({projectId: $scope.mode.projectId});

    $scope.spentTab = function(page) {
      $scope.onCancel();
      $scope.loadRecords(recordFactory.getSpents(), page);
      $scope.mode.selected = 'spent';
    };

    $scope.costTab = function(page) {
      $scope.onCancel();
      $scope.loadRecords(recordFactory.getCosts(), page);
      $scope.mode.selected = 'cost';
    };

    $scope.loadRecords = function(resource, page) {
      $scope.page = resource.query({project : $scope.mode.projectId, page : page}, function(){
        if(page){
          //add to already loaded records
          $scope.records = $scope.records.concat($scope.page.content);
        }else{
          $scope.records = $scope.page.content;
        }
      });
    };

    $scope.onAdd = function() {
      var curDate = new Date();
      $scope.day = curDate.getDate();
      $scope.month = utils.monthForField(curDate.getMonth());
      $scope.year = curDate.getFullYear();
      $scope.valueAdd = null;
      $scope.commentAdd = '';

      $scope.mode.editId = -1;
      $scope.mode.adding = true;
    };

    $scope.onAddRecord = function() {
      var m = utils.monthForField($scope.month);
      var date = new Date($scope.year, m, $scope.day);
      var comment = $scope.commentAdd;
      var value = $scope.valueAdd;

      if($scope.mode.selected == 'spent'){
        $scope.newitem = recordFactory.spent().create({
          date : date,
          value : value,
          comment : comment,
          project : $scope.mode.projectId
        }, function(){
          $scope.records.splice(0, 0, $scope.newitem);
        });
      }else{
        $scope.newitem = recordFactory.cost().create({
          date : date,
          value : value,
          comment : comment,
          project : $scope.mode.projectId
        }, function(){
          $scope.records.splice(0, 0, $scope.newitem);
        });
      }
      $scope.mode.adding = 0;
    };

    $scope.onCancel = function() {
      $scope.mode.adding = 0;
      $scope.mode.editId = -1;
    };
  })
	.controller('recordCntl', function($scope, recordFactory, utils) {
		$scope.onEdit = function($record) {
			var date = utils.parseDate($record.date);
			if(date){
				$scope.day = date.getDate();
				$scope.month = utils.monthForField(date.getMonth());
				$scope.year = date.getFullYear();
			}
			
			$scope.valueEdit = $record.value;
			$scope.commentEdit = $record.comment;
			$scope.nameEdit = $record.userScreenName;
			
			$scope.mode.editId = $record.id;
			$scope.mode.adding = 0;
		};
	
		$scope.onUpdateRecord = function($record) {
			
			var value = $scope.valueEdit;
			var comment = $scope.commentEdit;
			var m = utils.monthForDate($scope.month);
			var date = new Date($scope.year, m, $scope.day);
			
			var r = {
					id : $record.id,
					date : date,
					value : value,
					comment : comment,
					project : $scope.mode.projectId
				};
			 
			
			if($scope.mode.selected == 'spent'){
				$scope.updateditem = recordFactory.spent().update(r, function(){
					$record.date = $scope.updateditem.date;
					$record.value = $scope.updateditem.value;
					$record.comment = $scope.updateditem.comment;
				});
			} else {
				$scope.updateditem = recordFactory.cost().update(r, function(){
					$record.date = $scope.updateditem.date;
					$record.value = $scope.updateditem.value;
					$record.comment = $scope.updateditem.comment;
				});
			}				
			$scope.mode.editId = -1;
		};
		
		$scope.onCancel = function($record) {
			$scope.mode.editId = -1;
		};
		
	})
	.controller('billingCtrl', function($scope, billingFactory) {
		$scope.billing = billingFactory.query();
	});
