(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PorudzbinaZANabavkuDetailController', PorudzbinaZANabavkuDetailController);

    PorudzbinaZANabavkuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PorudzbinaZANabavku', 'PozivZaPrikupljanjeN'];

    function PorudzbinaZANabavkuDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PorudzbinaZANabavku, PozivZaPrikupljanjeN) {
        var vm = this;

        vm.porudzbinaZANabavku = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('isaApp:porudzbinaZANabavkuUpdate', function(event, result) {
            vm.porudzbinaZANabavku = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
