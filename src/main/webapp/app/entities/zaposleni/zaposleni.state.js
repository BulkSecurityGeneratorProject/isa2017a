(function() {
    'use strict';

    angular
        .module('isaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('zaposleni', {
            parent: 'entity',
            url: '/zaposleni',
            data: {
                authorities: ['ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA' ],
                pageTitle: 'Zaposlenis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zaposleni/zaposlenis.html',
                    controller: 'ZaposleniController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('zaposleni-detail', {
            parent: 'zaposleni',
            url: '/zaposleni/{id}',
            data: {
                authorities: ['ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA' ],
                pageTitle: 'Zaposleni'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zaposleni/zaposleni-detail.html',
                    controller: 'ZaposleniDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Zaposleni', function($stateParams, Zaposleni) {
                    return Zaposleni.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'zaposleni',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('zaposleni-detail.edit', {
            parent: 'zaposleni-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA' ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zaposleni.new', {
            parent: 'zaposleni',
            url: '/new',
            data: {
                authorities: ['ROLE_MENADZER_RESTORANA' ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
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
                                vrstaZaposlenja: null,
                                zaduzenjeZaSegment: null,
                                datumRodjenja: null,
                                konfekciskiBroj: null,
                                velicinaObuce: null,
                                firstLogin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: 'zaposleni' });
                }, function() {
                    $state.go('zaposleni');
                });
            }]
        })
        .state('zaposleni.edit', {
            parent: 'zaposleni',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA' ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: 'zaposleni' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zaposleni.delete', {
            parent: 'zaposleni',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ZAPOSLENI', 'ROLE_MENADZER_RESTORANA' ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-delete-dialog.html',
                    controller: 'ZaposleniDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: 'zaposleni' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
