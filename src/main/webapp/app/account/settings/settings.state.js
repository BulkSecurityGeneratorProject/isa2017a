(function() {
    'use strict';

    angular
        .module('isaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('settings', {
            parent: 'account',
            url: '/settings',
            data: {
                authorities: ['ROLE_USER', 'ROLE_GOST', 'ROLE_MENADZER_SISTEMA', 'ROLE_MENADZER_RESTORANA', 'ROLE_ZAPOSLENI'],
                pageTitle: 'Settings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/settings/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
