(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('StolDetailController', StolDetailController);

    StolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stol', 'KonfiguracijaStolova', 'Racun', 'Rezervacija'];

    function StolDetailController($scope, $rootScope, $stateParams, previousState, entity, Stol, KonfiguracijaStolova, Racun, Rezervacija) {
        var vm = this;

        vm.stol = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:stolUpdate', function(event, result) {
            vm.stol = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
