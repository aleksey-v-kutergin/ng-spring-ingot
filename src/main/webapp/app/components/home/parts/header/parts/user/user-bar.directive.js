(function () {
    'use strict';

    angular
        .module('homeModule')
        .directive('userBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/header/parts/user/user-bar.template.html',
                replace: false
            }
        });

})();