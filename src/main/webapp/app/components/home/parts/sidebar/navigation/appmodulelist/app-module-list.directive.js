(function () {
    'use strict';

    angular
        .module('appNavigationModule', ['coreModule'])
        .directive('appModuleList', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/sidebar/navigation/appmodulelist/app-module-list.template.html',
                controller: 'appModuleListController',
                replace: false
            }
        });

})();