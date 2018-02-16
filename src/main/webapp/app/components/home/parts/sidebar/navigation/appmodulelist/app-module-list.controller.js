(function () {
    'use strict';

    angular
        .module('appNavigationModule')
        .controller('appModuleListController', function ($scope, appModuleService) {
            $scope.appModulesTree = [];
            
            $scope.hasSubmodules = function (mainModule) {
                debugger;
              return mainModule.subModules && mainModule.subModules.length > 0;
            };

            var fetchAppModules = function () {
                appModuleService.getAllAppModules().then(function (data) {
                    var mainModules = appModuleService.separateMainModules(data);
                    if(mainModules) {
                        mainModules.forEach(function (mainModule) {
                            var subModules = appModuleService.findSubmodules(mainModule.id, data);
                            if(subModules) {
                                mainModule.subModules = subModules;
                            } else {
                                mainModule.subModules = [];
                            }
                            $scope.appModulesTree.push(mainModule);
                        });
                    }
                    debugger;
                }, function (error) {
                    console.log(error);
                });
            };

            fetchAppModules();
        });

})();