(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RezervacijaDetailController', RezervacijaDetailController);

    RezervacijaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rezervacija', 'Stol', 'Porudzbina', 'Poziv', 'Restoran', 'Gost'];

    function RezervacijaDetailController($scope, $rootScope, $stateParams, previousState, entity, Rezervacija, Stol, Porudzbina, Poziv, Restoran, Gost) {
        var vm = this;

        vm.rezervacija = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:rezervacijaUpdate', function(event, result) {
            vm.rezervacija = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
