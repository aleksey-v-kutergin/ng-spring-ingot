(function () {
    'use strict';

    angular
        .module('authModule')
        .directive('loginForm', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/auth/login/form/login/login-form.template.html',
                controller: 'loginFormController',
                replace: false
            }
        });

})();