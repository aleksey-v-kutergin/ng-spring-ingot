(function () {
    'use strict';

    angular
        .module('homeModule')
        .directive('logoutBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/header/parts/logout/logout-bar.template.html',
                replace: false
            }
        });

})();