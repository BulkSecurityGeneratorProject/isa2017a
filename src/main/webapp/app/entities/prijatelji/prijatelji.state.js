(function() {
    'use strict';

    angular
        .module('isaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prijatelji', {
            parent: 'entity',
            url: '/prijatelji',
            data: {
                authorities: ['ROLE_GOST'],
                pageTitle: 'Prijateljis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prijatelji/prijateljis.html',
                    controller: 'PrijateljiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('prijatelji-detail', {
            parent: 'prijatelji',
            url: '/prijatelji/{id}',
            data: {
                authorities: ['ROLE_GOST'],
                pageTitle: 'Prijatelji'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prijatelji/prijatelji-detail.html',
                    controller: 'PrijateljiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Prijatelji', function($stateParams, Prijatelji) {
                    return Prijatelji.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prijatelji',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prijatelji-detail.edit', {
            parent: 'prijatelji-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_GOST']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prijatelji/prijatelji-dialog.html',
                    controller: 'PrijateljiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Prijatelji', function(Prijatelji) {
                            return Prijatelji.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prijatelji.new', {
            parent: 'prijatelji',
            url: '/new',
            data: {
                authorities: ['ROLE_GOST']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prijatelji/prijatelji-dialog.html',
                    controller: 'PrijateljiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idGosta1: null,
                                idGosta2: null,
                                postalanZahtev: null,
                                prihvacenZahtev: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prijatelji', null, { reload: 'prijatelji' });
                }, function() {
                    $state.go('prijatelji');
                });
            }]
        })
        .state('prijatelji.edit', {
            parent: 'prijatelji',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_GOST']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prijatelji/prijatelji-dialog.html',
                    controller: 'PrijateljiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Prijatelji', function(Prijatelji) {
                            return Prijatelji.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prijatelji', null, { reload: 'prijatelji' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prijatelji.delete', {
            parent: 'prijatelji',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_GOST']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prijatelji/prijatelji-delete-dialog.html',
                    controller: 'PrijateljiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Prijatelji', function(Prijatelji) {
                            return Prijatelji.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prijatelji', null, { reload: 'prijatelji' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
