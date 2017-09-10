(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RacunDetailController', RacunDetailController);

    RacunDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Racun', 'Zaposleni', 'Stol'];

    function RacunDetailController($scope, $rootScope, $stateParams, previousState, entity, Racun, Zaposleni, Stol) {
        var vm = this;

        vm.racun = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:racunUpdate', function(event, result) {
            vm.racun = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
