(function () {
    'use strict';

    angular
        .module('userModule')
        .directive('usersContent', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/user/content/users/users-content.template.html',
                controller: 'usersContentController',
                replace: false
            }
        });

})();