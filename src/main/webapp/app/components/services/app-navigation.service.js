(function () {
    'use strict';

    angular
        .module('coreModule')
        .factory('appNavigationService', function (rx) {
            var service = {};
            service._activeModuleObserver = new rx.Subject();

            service.changeActiveModule = function (module) {
                if(service._activeModuleObserver.observers.length) {
                    service._activeModuleObserver.onNext(module);
                } else {
                    // TODO: 1. Закешить модуль
                    // TODO: 2. Дождать первого подписчика
                    // TODO: 3. Вызывать onNext(...)
                    // TODO: 4. Очитстить кэш
                }
            };

            service.subscribeActiveModule = function (onNext) {
                service._activeModuleObserver.subscribe(onNext);
            };

            return service;
        });

})();