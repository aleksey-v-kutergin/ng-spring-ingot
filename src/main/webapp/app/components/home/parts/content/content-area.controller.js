(function () {
    'use strict';

    angular
        .module('homeModule')
        .controller('contentAreaController', function ($scope, appNavigationService) {

            appNavigationService.subscribeActiveModule(function (module) {
                $scope.moduleName = module.name;
            });

        });

})();