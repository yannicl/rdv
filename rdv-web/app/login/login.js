'use strict';

angular.module('rdvApp.login', ['ngRoute', 'rdvApp.api'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', ['$scope', '$location', '$routeParams', 'rdvService', function($scope, $location, $routeParams, rdvService) {
	$scope.formData = {};
	
	if ($routeParams.err) {
		$scope.errMsg = "Votre accès a été refusé. (" + $routeParams.err + ")";
	} else if ($routeParams.code) {
		rdvService.useApiKey($routeParams.code);
		$location.path('/summary');
	}
	// remove url parameters as they have been handled.
	$location.url($location.path());
	
	$scope.doLogin = function() {
		rdvService.login($.param($scope.formData)).then(function() {
			$location.path('/summary');
		}, function() {
			$scope.errMsg = "Identifiant ou mot de passe incorrect.";
		});
	};
	
}]);