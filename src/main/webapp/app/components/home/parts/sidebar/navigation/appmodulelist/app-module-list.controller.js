(function () {
    'use strict';

    angular
        .module('appNavigationModule')
        .controller('appModuleListController', function ($scope, appModuleService) {
            $scope.appModulesTree = [];
            $scope.selectedMainModule = null;
            $scope.selectedSubModule = null;
            $scope.expandMainModule = false;

            $scope.hasSubmodules = function (module) {
                return module.subModules && module.subModules.length > 0;
            };

            $scope.onMainModuleClick = function (module, $event) {
                $event.stopPropagation();
                $scope.selectedMainModule = module;
                if ($scope.expandMainModule) {
                    $scope.expandMainModule = false;
                } else {
                    $scope.expandMainModule = $scope.hasSubmodules(module);
                }
            };

            $scope.onSubModuleClick = function (module, $event) {
                $event.stopPropagation();
                $scope.selectedSubModule = module;
            };

            var fetchAppModules = function () {
                appModuleService.getAllAppModules().then(function (data) {
                    var mainModules = appModuleService.separateMainModules(data);
                    if (mainModules && mainModules.length > 0) {
                        mainModules.forEach(function (mainModule) {
                            var subModules = appModuleService.findSubmodules(mainModule.id, data);
                            if (subModules) {
                                mainModule.subModules = subModules;
                            } else {
                                mainModule.subModules = [];
                            }
                            $scope.appModulesTree.push(mainModule);
                        });
                        $scope.selectedMainModule = $scope.appModulesTree[0];
                        $scope.expandMainModule = $scope.hasSubmodules($scope.selectedMainModule);
                        if ($scope.expandMainModule) {
                            $scope.selectedSubModule = $scope.selectedMainModule.subModules[0];
                        }
                    }
                }, function (error) {
                    console.log(error);
                });
            };

            fetchAppModules();
        });

})();