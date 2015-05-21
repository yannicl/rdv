'use strict';

angular.module('rdvApp.api', ['ngStorage'])

.config(function () {
	
})

.factory('rdvCache', ['$cacheFactory', function($cacheFactory) {
    return $cacheFactory('rdvCache');
  }])

.factory('rdvService', ['$http', '$q', '$location', '$localStorage','$rootScope','rdvCache', function($http, $q, $location, $localStorage, $rootScope, rdvCache) {
	
	function _getAccount() {
		var deferred = $q.defer();
		var account = rdvCache.get('account');
		if (account) {
			deferred.resolve(account);
			return deferred.promise;
		}
		$http.get('/rdv-rest/api/account')
			.success(function (data) {
				_storeAccount(data);
				deferred.resolve(data);
			}).error(function() {deferred.reject('no data');});
		return deferred.promise;
	};
	
	function _storeAccount(data) {
		rdvCache.put('account', data);
		$rootScope.username = data.account.username;
		console.log("setting rootscope.username to " + data.account.username);
	}
	
	function _useApiKey(apiKey) {
		console.log('Api key: ' + apiKey);
		$http.defaults.headers.common['X-API-KEY'] = apiKey;
		$localStorage.apiKey = apiKey;
	}
	
	return {
		managedPeople: null,
		
		useApiKey: _useApiKey,
		
		login: function(dataParam) {
			var deferred = $q.defer();
			$http({
				  method  : 'POST',
				  url     : '/rdv-rest/login/login',
				  data    : dataParam, 
				  headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
				 })
			.success(function (data) {
				_useApiKey(data.account.apiKey);
				_storeAccount(data);
				deferred.resolve(data);
			}).error(function() {
				deferred.reject('no data');
			});
		return deferred.promise;
		},
		
		logout: function() {
			rdvCache.removeAll();
			$localStorage.$reset();
			$http.defaults.headers.common['X-API-KEY'] = '';
			$rootScope.username = "";
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
		},
		
		addPerson : function(dataParam) {
			var deferred = $q.defer();
			$http({
				  method  : 'POST',
				  url     : '/rdv-rest/api/persons/add',
				  data    : dataParam, 
				  headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
				 })
			.success(function (data) {
				deferred.resolve(data);
			}).error(function(data, status) {
				if (status === 404) {
					deferred.reject('Code incorrect. Modifier le code et ajouter à nouveau.');
				}
				if (status === 409) {
					deferred.reject('Ce code est correct mais vous avez déjà ajouté cette personne.');
				}
			});
			return deferred.promise;
		}
	};
}]);