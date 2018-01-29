(function () {
    'use strict';

    angular
        .module('sideBarModule')
        .directive('sideBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/sidebar/side-bar.template.html',
                replace: false
            }
        });

})();