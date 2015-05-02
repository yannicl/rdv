'use strict';

angular.module('rdvApp.api', ['ngStorage'])

.config(function () {
	
})

.factory('rdvCache', ['$cacheFactory', function($cacheFactory) {
    return $cacheFactory('rdvCache');
  }])

.factory('rdvService', ['$http', '$q', '$location', '$localStorage', 'rdvCache', function($http, $q, $location, $localStorage, rdvCache) {
	
	function _getAccount() {
		var deferred = $q.defer();
		var account = rdvCache.get('account');
		if (account) {
			deferred.resolve(account);
			return deferred.promise;
		}
		$http.get('/rdv-rest/api/account')
			.success(function (data) {
				rdvCache.put('account', data);
				deferred.resolve(data);
			}).error(function() {deferred.reject('no data');});
		return deferred.promise;
	};
	
	return {
		managedPeople: null,
		
		login: function(dataParam) {
			var deferred = $q.defer();
			$http({
				  method  : 'POST',
				  url     : '/rdv-rest/login/login',
				  data    : dataParam, 
				  headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
				 })
			.success(function (data) {
				$http.defaults.headers.common['X-API-KEY'] = data.account.apiKey;
				$localStorage.apiKey = data.account.apiKey;
				console.log('Api key: ' + data.account.apiKey);
				rdvCache.put('account', data);
				deferred.resolve(data);
			}).error(function() {deferred.reject('no data');});
		return deferred.promise;
		},
		
		logout: function() {
			rdvCache.removeAll();
			$localStorage.$reset();
			$http.defaults.headers.common['X-API-KEY'] = '';
			
			var deferred = $q.defer();
			deferred.resolve();
			return deferred.promise;
			
		},
		
		verifyLogin: function() {
			if ($localStorage.apiKey) {
				$http.defaults.headers.common['X-API-KEY']= $localStorage.apiKey;
			}
			return _getAccount();
		},
		
		getAccount : _getAccount,
		
		getListManagedPeople : function() {
			var deferred = $q.defer();
			var account = rdvCache.get('account');
			var url = JSPath.apply('.links{.rel === "rdv:searchPersonByAccount"}.href', account);
			$http.get(url)
				.success(function (data) {
					deferred.resolve(data);
				}).error(function() {deferred.reject('no data');});
			return deferred.promise;
		},
		getListManagedPeopleAppointment : function() {
			var deferred = $q.defer();
			var account = rdvCache.get('account');
			var url = JSPath.apply('.links{.rel === "rdv:searchEventByAccount"}.href', account);
			$http.get(url)
				.success(function (data) {
					deferred.resolve(data);
				}).error(function() {deferred.reject('no data');});
			return deferred.promise;
		},
		getListEvents : function(person) {
			var deferred = $q.defer();
			var url = JSPath.apply('.links{.rel === "rdv:searchEventByPerson"}.href', person);
			if (! url.length) {
				deferred.reject('illegal person');
				return deferred.promise;
			}
			$http.get(url[0])
				.success(function (data) {
					deferred.resolve(data);
				}).error(function() {deferred.reject('no data');});
			return deferred.promise;
		}
	};
}]);