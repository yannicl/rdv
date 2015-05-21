'use strict';

angular.module('rdvApp.person', ['ngRoute', 'rdvApp.api'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/persons/add', {
    templateUrl: 'person/person.html',
    controller: 'PersonCtrl'
  });
}])

.controller('PersonCtrl', ['$scope','$q','$location', 'rdvService', function($scope, $q, $location, rdvService) {
	
	$scope.formData = {};
	$scope.formData.relation = "CHILD";
	$scope.lastSubmittedCode = ''; 
	
	rdvService.verifyLogin().then(function() {
		
	}, function() {
		$location.path('/logout');
	});
	
	$scope.addPerson = function() {
		rdvService.addPerson($.param($scope.formData)).then(function() {
			$location.path('/summary');
		}, function(errMsg) {
			$scope.lastSubmittedCode = $scope.formData.code;
			$scope.errMsg = errMsg;
			$('#codeInput').select();
		});
	};
	
	$scope.cancel = function() {
		$location.path('/summary');
	};
	
	$scope.codeInputValidationClass = function() {
		if ($scope.formData.code === $scope.lastSubmittedCode) {
			return "has-error";
		} else {
			return "";
		}
	};
	
	$scope.isMsgErrDisplayed = function() {
		if ($scope.formData.code === $scope.lastSubmittedCode) {
			return true;
		} else {
			return false;
		}
	};
	
		
}]);