(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RestoranDetailController', RestoranDetailController);

    RestoranDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Restoran', 'KonfiguracijaStolova', 'Jelovnik', 'KartaPica', 'Ocena', 'PozivZaPrikupljanjeN', 'Rezervacija', 'MenadzerRestorana', 'Zaposleni', 'RasporedSmenaZaSankere', 'RasporedSmenaZaKonobare', 'RasporedSmenaZaKuvare'];

    function RestoranDetailController($scope, $rootScope, $stateParams, previousState, entity, Restoran, KonfiguracijaStolova, Jelovnik, KartaPica, Ocena, PozivZaPrikupljanjeN, Rezervacija, MenadzerRestorana, Zaposleni, RasporedSmenaZaSankere, RasporedSmenaZaKonobare, RasporedSmenaZaKuvare) {
        var vm = this;

        vm.restoran = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:restoranUpdate', function(event, result) {
            vm.restoran = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
