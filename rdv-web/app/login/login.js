'use strict';

angular.module('rdvApp.login', ['ngRoute', 'rdvApp.api'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', ['$scope', '$location', 'rdvService', function($scope, $location, rdvService) {
	$scope.formData = {};
	$scope.doLogin = function() {
		rdvService.login($.param($scope.formData)).then(function() {
			$location.path('/summary');
		}, null);
	};
	
}]);