(function () {
    'use strict';

    angular
        .module('headerBarModule')
        .directive('helpBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/header/parts/help/help-bar.template.html',
                replace: false
            }
        });

})();