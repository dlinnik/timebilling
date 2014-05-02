'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
	.controller('indexCtrl', function($scope, projectFactory, currentUserFactory){
		$scope.projects = 
		projectFactory.query(function(){
			$scope.totalProjects = $scope.projects.length;
		});

		$scope.user_details = currentUserFactory.query(function(){
			$scope.appName = $scope.user_details.appScreenName;
			$scope.user = $scope.user_details.username;
			if($scope.user_details.role == 1){
				$scope.role = 'Администратор';				
			}else{
				$scope.role = 'Пользователь';
			}
		});
		
		$scope.activePage = 'projects';
		
		$scope.onProjects = function(){
			$scope.activePage = 'projects';
		};
		
		$scope.onBilling = function(){
			$scope.activePage = 'billing';
		};
		    
	})
	.controller('projectListCtrl', function($scope, projectFactory) {
				$scope.projects = projectFactory.query();
	})
	.controller('projectCtrl', function($scope, $routeParams, recordFactory, projectFactory, utils) {
		$scope.projectId = $routeParams.projectId;
		
		$scope.mode = {
				adding: 0,  // Поле добавления новой записи
				editId: -1, // Запись, которая редактируется
				selected: 'spent' // Таб, который открыт
		};
		
		$scope.spentTab = function() {
			$scope.onCancel();
			$scope.records = recordFactory.getSpents();
			$scope.mode.selected = 'spent';
		};
	
		$scope.costTab = function() {
			$scope.onCancel();
			$scope.records = recordFactory.getCosts();
			$scope.mode.selected = 'cost';
		};
	
		$scope.onAdd = function() {
			var curDate = new Date();
			$scope.day = curDate.getDate();
			$scope.month = utils.monthForField(curDate.getMonth());
			$scope.year = curDate.getFullYear();
			
			$scope.mode.editId = -1;
			$scope.mode.adding = true;
		};
	
		$scope.onAddRecord = function() {
			var item;
			var id = $scope.records.length + 1;
			var m = utils.monthForField($scope.month);
			var date = new Date($scope.year, m, $scope.day);
			var name = "Дмитрий Линник";
			var comment = $scope.commentAdd;
			var value = $scope.valueAdd;
			item = {
				id : id,
				disable : 0,
				auth : 1,
				name : name,
				date : date,
				value : value,
				comment : comment
			};
			$scope.records.splice(0, 0, item);
			
			$scope.mode.adding = 0;
		};
	
		$scope.onCancel = function() {
			$scope.mode.adding = 0;
		};
	})
	.controller('recordCntl', function($scope, recordFactory, utils) {
		$scope.onEdit = function($record) {
			$scope.day = $record.date.getDate();
			$scope.month = utils.monthForField($record.date.getMonth());
			$scope.year = $record.date.getFullYear();
			$scope.valueEdit = $record.value;
			$scope.commentEdit = $record.comment;
			$scope.nameEdit = $record.name;
			
			$scope.mode.editId = $record.id;
		};
	
		$scope.onUpdateRecord = function($record) {
			$record.value = $scope.valueEdit;
			$record.comment = $scope.commentEdit;
			var m = utils.monthForDate($scope.month);
			$record.date = new Date($scope.year, m, $scope.day);
			
			$scope.mode.editId = -1;
		};
		
		$scope.onCancel = function($record) {
			$scope.mode.editId = -1;
		};
		
	})
	.controller('billingCtrl', [ function() {
	
	} ]);
