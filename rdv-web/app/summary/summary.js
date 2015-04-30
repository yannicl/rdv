'use strict';

angular.module('rdvApp.summary', ['ngRoute', 'rdvApp.api'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/summary', {
    templateUrl: 'summary/summary.html',
    controller: 'SummaryCtrl'
  });
}])

.controller('SummaryCtrl', ['$scope','$q','$location', 'rdvService', function($scope, $q, $location, rdvService) {
	
	rdvService.verifyLogin().then(function() {
		$scope.getListManagedPeople();
	}, function() {
		$location.path('/login');
	});
	
	$scope.getListManagedPeople = function() {
		var serviceCalls = [];
		serviceCalls.push(rdvService.getListManagedPeople());
		serviceCalls.push(rdvService.getListManagedPeopleAppointment());
		$q.all(serviceCalls).then(function (dataArray) {
			$scope.managedPeople = JSPath.apply('.persons{.person}',dataArray);
			var i;
			for(i=0;i<$scope.managedPeople.length;i++) {
				$scope.managedPeople[i].events = JSPath.apply('.events{.event.attendee.personId=='+ $scope.managedPeople[i].person.personId + '}',dataArray);
			}
			} , function() {
				console.log('Could not obtain data');
			});
	};
	
}]);