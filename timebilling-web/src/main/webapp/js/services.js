'use strict';

/* Services */

angular.module('myApp.services', ['ngResource'])
	.factory('projectFactory', function($resource) {
		return $resource('/timebilling/api/projects', {}, {
			query : {method : 'GET',	isArray : true }	
		});
	})
	.factory('recordFactory', function($resource) {
		return {
			getSpents: function(){
				return SPENT_BASE;
				
			},
			getCosts: function(){
				return COST_BASE;					
			}
		};
	})
	.factory('utils', function(){
		return {
				monthForField: function(month){
					var m = month + 1;
					if(m < 10){
						m = '0' + m;
					};
					return m;
				},
				
				monthForDate: function(month){
					return month -1;
				}
		};			
	});


// Temporary storage - удалить, как подключим Rest
var SPENT_BASE = [
                  {id: 1, disable: 0, auth: 1, name: "Константин Стаканов", date: new Date(2014,5,23), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 2, disable: 0, auth: 0, name: "Константин Стаканов", date: new Date(2014,5,2), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 3, disable: 0, auth: 0, name: "Константин Стаканов", date: new Date(2014,12,1), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 4, disable: 0, auth: 1, name: "Константин Стаканов", date: new Date(2014,3,23), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 5, disable: 1, auth: 1, name: "Константин Стаканов", date: new Date(2014,5,23), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 6, disable: 1, auth: 0, name: "Константин Стаканов", date: new Date(2014,7,3), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 7, disable: 1, auth: 1, name: "Константин Стаканов", date: new Date(2014,7,3), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
                  {id: 8, disable: 1, auth: 1, name: "Константин Стаканов", date: new Date(2014,7,3), value: 45.6, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" }
];	

var COST_BASE = [
             {id: 1, disable: 0, auth: 1, name: "Константин Стаканов", date: new Date(2014,5,23),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 2, disable: 0, auth: 0, name: "Константин Стаканов", date: new Date(2014,5,2),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 3, disable: 0, auth: 0, name: "Константин Стаканов", date: new Date(2014,12,1),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 4, disable: 0, auth: 1, name: "Константин Стаканов", date: new Date(2014,3,23),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 5, disable: 1, auth: 1, name: "Константин Стаканов", date: new Date(2014,5,23),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 6, disable: 1, auth: 0, name: "Константин Стаканов", date: new Date(2014,7,3),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 7, disable: 1, auth: 1, name: "Константин Стаканов", date: new Date(2014,7,3),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" },
             {id: 8, disable: 1, auth: 1, name: "Константин Стаканов", date: new Date(2014,7,3),  value: 1300, comment: "Просидел в суде 4 часа, бился с этими кретинами из обвинения, гнали пургу. Доки на дропе: (тут ссылка)" }
];

 