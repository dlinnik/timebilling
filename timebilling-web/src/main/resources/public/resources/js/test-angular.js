function Projects($scope, $http) {
    $http.get('/api/projects').
        success(function(data) {
            $scope.projects = data;
        });	
}