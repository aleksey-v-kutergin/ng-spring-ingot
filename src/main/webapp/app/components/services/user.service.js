(function () {
    'use strict';

    angular
        .module('coreModule')
        .factory('userService', function ($http, $q) {
            var service = {};
            service.apiUrlRoot = 'api';

            service.getAllUsers = function () {
                var defer = $q.defer();
                $http.get(this.apiUrlRoot + '/user/all').then(function (response) {
                    defer.resolve(response.data);
                }, function (error) {
                    defer.reject(error);
                });
                return defer.promise;
            };

            service.authenticateUser = function (credentials) {
                var that = this;
                var defer = $q.defer();
                $http.get(this.apiUrlRoot + '/auth/salt', {params: {login: credentials.username}}).then(function (response) {
                    var salt = response.data;
                    credentials.password += salt;
                    $http.post(that.apiUrlRoot + '/auth/login', credentials).then(function (response) {
                        defer.resolve(response.data);
                    }, function (error) {
                        defer.reject(error)
                    });
                }, function (error) {
                    defer.reject(error);
                });
                return defer.promise;
            };

            return service;
        });

})();