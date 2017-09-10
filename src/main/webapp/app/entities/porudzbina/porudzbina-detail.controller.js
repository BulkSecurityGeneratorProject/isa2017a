(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PorudzbinaDetailController', PorudzbinaDetailController);

    PorudzbinaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Porudzbina', 'Jelo', 'Pice', 'Rezervacija'];

    function PorudzbinaDetailController($scope, $rootScope, $stateParams, previousState, entity, Porudzbina, Jelo, Pice, Rezervacija) {
        var vm = this;

        vm.porudzbina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:porudzbinaUpdate', function(event, result) {
            vm.porudzbina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
