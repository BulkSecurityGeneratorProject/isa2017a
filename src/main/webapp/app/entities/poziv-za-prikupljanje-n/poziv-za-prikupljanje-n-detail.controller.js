(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PozivZaPrikupljanjeNDetailController', PozivZaPrikupljanjeNDetailController);

    PozivZaPrikupljanjeNDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PozivZaPrikupljanjeN', 'Restoran', 'PorudzbinaZANabavku'];

    function PozivZaPrikupljanjeNDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PozivZaPrikupljanjeN, Restoran, PorudzbinaZANabavku) {
        var vm = this;

        vm.pozivZaPrikupljanjeN = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('isaApp:pozivZaPrikupljanjeNUpdate', function(event, result) {
            vm.pozivZaPrikupljanjeN = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
