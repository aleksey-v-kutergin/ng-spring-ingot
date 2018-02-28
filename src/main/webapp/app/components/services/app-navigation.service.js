(function () {
    'use strict';

    angular
        .module('coreModule')
        .factory('appNavigationService', function (rx) {
            var service = {};

            service._activeModuleObserver = new rx.Subject();
            service._cachedActiveModule = null;

            service.changeActiveModule = function (module) {
                if(service._activeModuleObserver.observers.length) {
                    service._activeModuleObserver.onNext(module);
                } else {
                    service._cachedActiveModule = module;
                }
            };

            service.subscribeActiveModule = function (onNext) {
                service._activeModuleObserver.subscribe(onNext);
                if(service._cachedActiveModule) {
                    service._activeModuleObserver.onNext(service._cachedActiveModule);
                    service._cachedActiveModule = null;
                }
            };

            return service;
        });

})();