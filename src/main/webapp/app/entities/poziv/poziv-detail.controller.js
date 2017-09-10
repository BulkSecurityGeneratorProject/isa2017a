(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PozivDetailController', PozivDetailController);

    PozivDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Poziv', 'Gost', 'Rezervacija'];

    function PozivDetailController($scope, $rootScope, $stateParams, previousState, entity, Poziv, Gost, Rezervacija) {
        var vm = this;

        vm.poziv = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:pozivUpdate', function(event, result) {
            vm.poziv = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
