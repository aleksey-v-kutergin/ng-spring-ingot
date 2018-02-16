(function () {
    'use strict';

    angular
        .module('coreModule')
        .factory('appModuleService', function ($http, $q) {
            var service = {};
            service.apiUrlRoot = 'api';

            service.getAllAppModules = function () {
              var defer = $q.defer();
              $http.get(this.apiUrlRoot + '/app/module/all').then(function (response) {
                  defer.resolve(response.data);
              }, function (error) {
                  defer.reject(error);
              });
              return defer.promise;
            };

            service.separateMainModules = function(modules) {
                return  modules.filter(function (module) {
                  return module.parent === 0;
                });
            };

            service.findSubmodules = function(parent, modules) {
              return modules.filter(function (module) {
                  return module.parent === parent;
              });
            };

            return service;
        });

})();