(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RasporedSmenaZaKonobareDetailController', RasporedSmenaZaKonobareDetailController);

    RasporedSmenaZaKonobareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RasporedSmenaZaKonobare', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaKonobareDetailController($scope, $rootScope, $stateParams, previousState, entity, RasporedSmenaZaKonobare, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaKonobare = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:rasporedSmenaZaKonobareUpdate', function(event, result) {
            vm.rasporedSmenaZaKonobare = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
