(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PonudjacDetailController', PonudjacDetailController);

    PonudjacDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ponudjac', 'User'];

    function PonudjacDetailController($scope, $rootScope, $stateParams, previousState, entity, Ponudjac, User) {
        var vm = this;

        vm.ponudjac = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:ponudjacUpdate', function(event, result) {
            vm.ponudjac = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
