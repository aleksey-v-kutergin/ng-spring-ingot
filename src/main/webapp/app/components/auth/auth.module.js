(function () {
    'use strict';

    /**
     *    Модуль аутентификации - группирует все физуальные комоненты,
     *    участвующие в процессе аунтификации пользователя: страница логина,
     *    форма логина и тд.
     **/
    angular
        .module('authModule', [
                'ui.router',
                'coreModule'
        ])
        .config(function ($httpProvider) {
            $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
            $httpProvider.defaults.xsrfHeaderName = 'X-XSRF-TOKEN';
        });

})();