(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RasporedSmenaZaKuvareDetailController', RasporedSmenaZaKuvareDetailController);

    RasporedSmenaZaKuvareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RasporedSmenaZaKuvare', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaKuvareDetailController($scope, $rootScope, $stateParams, previousState, entity, RasporedSmenaZaKuvare, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaKuvare = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:rasporedSmenaZaKuvareUpdate', function(event, result) {
            vm.rasporedSmenaZaKuvare = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
