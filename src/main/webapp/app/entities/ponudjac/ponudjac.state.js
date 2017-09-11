(function() {
    'use strict';

    angular
        .module('isaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ponudjac', {
            parent: 'entity',
            url: '/ponudjac',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA'],
                pageTitle: 'Ponudjacs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ponudjac/ponudjacs.html',
                    controller: 'PonudjacController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ponudjac-detail', {
            parent: 'ponudjac',
            url: '/ponudjac/{id}',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA'],
                pageTitle: 'Ponudjac'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ponudjac/ponudjac-detail.html',
                    controller: 'PonudjacDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ponudjac', function($stateParams, Ponudjac) {
                    return Ponudjac.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ponudjac',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ponudjac-detail.edit', {
            parent: 'ponudjac-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ponudjac/ponudjac-dialog.html',
                    controller: 'PonudjacDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ponudjac', function(Ponudjac) {
                            return Ponudjac.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ponudjac.new', {
            parent: 'ponudjac',
            url: '/new',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ponudjac/ponudjac-dialog.html',
                    controller: 'PonudjacDialogController',
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
                    $state.go('ponudjac', null, { reload: 'ponudjac' });
                }, function() {
                    $state.go('ponudjac');
                });
            }]
        })
        .state('ponudjac.edit', {
            parent: 'ponudjac',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ponudjac/ponudjac-dialog.html',
                    controller: 'PonudjacDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ponudjac', function(Ponudjac) {
                            return Ponudjac.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ponudjac', null, { reload: 'ponudjac' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ponudjac.delete', {
            parent: 'ponudjac',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_GOST', 'ROLE_ADMIN', 'ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ponudjac/ponudjac-delete-dialog.html',
                    controller: 'PonudjacDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ponudjac', function(Ponudjac) {
                            return Ponudjac.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ponudjac', null, { reload: 'ponudjac' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
