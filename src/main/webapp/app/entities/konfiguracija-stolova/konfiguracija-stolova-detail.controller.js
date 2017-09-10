(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('KonfiguracijaStolovaDetailController', KonfiguracijaStolovaDetailController);

    KonfiguracijaStolovaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'KonfiguracijaStolova', 'MenadzerRestorana', 'Stol'];

    function KonfiguracijaStolovaDetailController($scope, $rootScope, $stateParams, previousState, entity, KonfiguracijaStolova, MenadzerRestorana, Stol) {
        var vm = this;

        vm.konfiguracijaStolova = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:konfiguracijaStolovaUpdate', function(event, result) {
            vm.konfiguracijaStolova = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
