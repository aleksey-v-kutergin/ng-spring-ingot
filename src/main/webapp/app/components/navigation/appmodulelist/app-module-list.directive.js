(function () {
    'use strict';

    angular
        .module('appNavigationModule')
        .directive('appModuleList', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/navigation/appmodulelist/app-module-list.template.html',
                controller: 'appModuleListController',
                replace: false
            }
        });

})();