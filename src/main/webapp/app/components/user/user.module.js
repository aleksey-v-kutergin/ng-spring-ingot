(function () {
    'use strict';

    /**
     *   Модуль группирует все визуальные комопненты,
     *   связанные с жизненным цилом пользователя системы
     **/
    angular.module('userModule', [
                    'ui.grid',
                    'ui.grid.selection',
                    'coreModule'
    ]);

})();