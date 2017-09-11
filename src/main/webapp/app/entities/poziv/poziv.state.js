(function() {
    'use strict';

    angular
        .module('isaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('poziv', {
            parent: 'entity',
            url: '/poziv',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN'],
                pageTitle: 'Pozivs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/poziv/pozivs.html',
                    controller: 'PozivController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('poziv-detail', {
            parent: 'poziv',
            url: '/poziv/{id}',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN'],
                pageTitle: 'Poziv'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/poziv/poziv-detail.html',
                    controller: 'PozivDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Poziv', function($stateParams, Poziv) {
                    return Poziv.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'poziv',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('poziv-detail.edit', {
            parent: 'poziv-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv/poziv-dialog.html',
                    controller: 'PozivDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Poziv', function(Poziv) {
                            return Poziv.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('poziv.new', {
            parent: 'poziv',
            url: '/new',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv/poziv-dialog.html',
                    controller: 'PozivDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                potvrdjeno: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('poziv', null, { reload: 'poziv' });
                }, function() {
                    $state.go('poziv');
                });
            }]
        })
        .state('poziv.edit', {
            parent: 'poziv',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv/poziv-dialog.html',
                    controller: 'PozivDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Poziv', function(Poziv) {
                            return Poziv.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('poziv', null, { reload: 'poziv' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('poziv.delete', {
            parent: 'poziv',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv/poziv-delete-dialog.html',
                    controller: 'PozivDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Poziv', function(Poziv) {
                            return Poziv.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('poziv', null, { reload: 'poziv' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
