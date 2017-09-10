(function() {
    'use strict';

    angular
        .module('isaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('menadzer-sistema', {
            parent: 'entity',
            url: '/menadzer-sistema',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA'],
                pageTitle: 'MenadzerSistemas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/menadzer-sistema/menadzer-sistemas.html',
                    controller: 'MenadzerSistemaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('menadzer-sistema-detail', {
            parent: 'menadzer-sistema',
            url: '/menadzer-sistema/{id}',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA'],
                pageTitle: 'MenadzerSistema'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/menadzer-sistema/menadzer-sistema-detail.html',
                    controller: 'MenadzerSistemaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MenadzerSistema', function($stateParams, MenadzerSistema) {
                    return MenadzerSistema.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'menadzer-sistema',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('menadzer-sistema-detail.edit', {
            parent: 'menadzer-sistema-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-sistema/menadzer-sistema-dialog.html',
                    controller: 'MenadzerSistemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MenadzerSistema', function(MenadzerSistema) {
                            return MenadzerSistema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('menadzer-sistema.new', {
            parent: 'menadzer-sistema',
            url: '/new',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-sistema/menadzer-sistema-dialog.html',
                    controller: 'MenadzerSistemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                login: null,
                                password: null,
                                ime: null,
                                prezime: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('menadzer-sistema', null, { reload: 'menadzer-sistema' });
                }, function() {
                    $state.go('menadzer-sistema');
                });
            }]
        })
        .state('menadzer-sistema.edit', {
            parent: 'menadzer-sistema',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-sistema/menadzer-sistema-dialog.html',
                    controller: 'MenadzerSistemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MenadzerSistema', function(MenadzerSistema) {
                            return MenadzerSistema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('menadzer-sistema', null, { reload: 'menadzer-sistema' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('menadzer-sistema.delete', {
            parent: 'menadzer-sistema',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-sistema/menadzer-sistema-delete-dialog.html',
                    controller: 'MenadzerSistemaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MenadzerSistema', function(MenadzerSistema) {
                            return MenadzerSistema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('menadzer-sistema', null, { reload: 'menadzer-sistema' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
