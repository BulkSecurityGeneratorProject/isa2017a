(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('ZahtevZaPrijateljstvoDetailController', ZahtevZaPrijateljstvoDetailController);

    ZahtevZaPrijateljstvoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ZahtevZaPrijateljstvo', 'Gost'];

    function ZahtevZaPrijateljstvoDetailController($scope, $rootScope, $stateParams, previousState, entity, ZahtevZaPrijateljstvo, Gost) {
        var vm = this;

        vm.zahtevZaPrijateljstvo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:zahtevZaPrijateljstvoUpdate', function(event, result) {
            vm.zahtevZaPrijateljstvo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
