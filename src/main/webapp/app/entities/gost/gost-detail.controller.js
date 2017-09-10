(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('GostDetailController', GostDetailController);

    GostDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gost', 'User', 'Rezervacija', 'ZahtevZaPrijateljstvo', 'Poziv'];

    function GostDetailController($scope, $rootScope, $stateParams, previousState, entity, Gost, User, Rezervacija, ZahtevZaPrijateljstvo, Poziv) {
        var vm = this;

        vm.gost = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:gostUpdate', function(event, result) {
            vm.gost = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
