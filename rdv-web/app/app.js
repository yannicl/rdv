'use strict';

// Declare app level module which depends on views, and components
angular.module('rdvApp', [
  'ngRoute',
  'rdvApp.login',
  'rdvApp.logout',
  'rdvApp.summary',
  'rdvApp.person',
  'rdvApp.personal_events',
  'rdvApp.version',
  'ngMessageFormat'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/login'});
}])

.controller('RdvAppCtrl', ['$rootScope', function($rootScope) {
	
	$rootScope.username = "";
	
}]);
