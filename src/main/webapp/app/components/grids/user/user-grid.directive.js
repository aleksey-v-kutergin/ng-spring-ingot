(function () {
    'use strict';

    angular
        .module('userGrid', ['ui.grid', 'ui.grid.selection', 'coreModule'])
        .directive('userGrid', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/grids/user/user-grid.template.html',
                controller: 'userGridController',
                replace: false
            }
        });

})();