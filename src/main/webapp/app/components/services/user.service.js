(function () {
    'use strict';

    angular
        .module('coreModule', [])
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

            return service;
        });

})();