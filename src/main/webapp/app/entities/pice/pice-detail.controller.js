(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PiceDetailController', PiceDetailController);

    PiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Pice', 'KartaPica', 'Porudzbina'];

    function PiceDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Pice, KartaPica, Porudzbina) {
        var vm = this;

        vm.pice = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('isaApp:piceUpdate', function(event, result) {
            vm.pice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
