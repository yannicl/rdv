'use strict';

angular.module('rdvApp.personal_events', ['ngRoute', 'rdvApp.api'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/personal_events/:id', {
    templateUrl: 'personal_events/personal_events.html',
    controller: 'PersonalEventsCtrl'
  });
}])

.controller('PersonalEventsCtrl', ['$scope','$q', '$routeParams','$http', '$location', 'rdvService',  function($scope, $q, $routeParams, $http, $location, rdvService) {
	
	rdvService.verifyLogin().then(function() {
		$scope.getListEvents();
	}, function() {
		$location.path('/logout');
	});
	
	$scope.getListEvents = function() {
		rdvService.getListManagedPeople().then(function (data) {
			var person = JSPath.apply('.persons{.person.personId === ' + $routeParams.id + '}',data);
			rdvService.getListEvents(person).then(function (data) {
				$scope.fullEvents = JSPath.apply('.events{.event.status === "FULL"}', data);
				$scope.availableEvents = JSPath.apply('.events{.event.status === "AVAILABLE"}', data);
				}, function() {
					console.log('Could not obtain data');
				});
			}, function() {
					console.log('Could not obtain data');
			});
	};
	
	$scope.bookEvent = function(event) {
		var url = event.links[1].href;
		$http.post(url)
			.success(function (data) {
				$scope.getListEvents();
			}).error(function() {
				// handle error
			});
	};
	$scope.cancelEvent = function(event) {
		var url = event.links[1].href;
		$http.post(url)
			.success(function (data) {
				$scope.getListEvents();
			}).error(function() {
				// handle error
			});
	};
	$scope.backToSummary = function() {
		$location.path('/summary');
	};
}]);