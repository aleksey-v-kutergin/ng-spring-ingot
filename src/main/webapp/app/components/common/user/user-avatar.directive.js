(function () {
    'use strict';

    angular
        .module('appCommons', [])
        .directive('userAvatar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/common/user/user-avatar.template.html',
                replace: true

            }
         });

})();