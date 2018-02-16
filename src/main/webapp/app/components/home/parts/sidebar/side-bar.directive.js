(function () {
    'use strict';

    angular
        .module('sideBarModule', ['appNavigationModule'])
        .directive('sideBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/sidebar/side-bar.template.html',
                controller: 'sideBarController',
                replace: false
            }
        });

})();