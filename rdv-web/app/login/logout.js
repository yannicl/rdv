'use strict';

angular.module('rdvApp.logout', ['ngRoute', 'rdvApp.api'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/logout', {
	templateUrl: 'login/login.html',
    controller: 'LogoutCtrl'
  });
}])

.controller('LogoutCtrl', ['$location', 'rdvService', function($location, rdvService) {
	rdvService.logout().then(function() {
		$location.path('/login');
	});	
}]);