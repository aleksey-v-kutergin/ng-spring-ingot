(function () {
    'use strict';

    angular
        .module('userModule')
        .directive('userGrid', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/user/grids/users/user-grid.template.html',
                controller: 'userGridController',
                replace: false
            }
        });

})();