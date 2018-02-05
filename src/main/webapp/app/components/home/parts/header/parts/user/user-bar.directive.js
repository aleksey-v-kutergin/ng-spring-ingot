(function () {
    'use strict';

    angular
        .module('headerBarModule')
        .directive('userBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/header/parts/user/user-bar.template.html',
                replace: false
            }
        });

})();