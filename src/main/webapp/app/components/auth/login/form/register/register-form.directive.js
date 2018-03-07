(function () {
    'use strict';

    angular
        .module('authModule')
        .directive('registerForm', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/auth/login/form/register/register-form.template.html',
                controller: 'registerFormController',
                replace: false
            }
        });

})();