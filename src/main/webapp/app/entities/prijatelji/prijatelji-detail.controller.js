(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PrijateljiDetailController', PrijateljiDetailController);

    PrijateljiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Prijatelji'];

    function PrijateljiDetailController($scope, $rootScope, $stateParams, previousState, entity, Prijatelji) {
        var vm = this;

        vm.prijatelji = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:prijateljiUpdate', function(event, result) {
            vm.prijatelji = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
