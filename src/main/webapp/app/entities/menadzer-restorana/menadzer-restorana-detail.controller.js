(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerRestoranaDetailController', MenadzerRestoranaDetailController);

    MenadzerRestoranaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MenadzerRestorana', 'User', 'KonfiguracijaStolova', 'Restoran'];

    function MenadzerRestoranaDetailController($scope, $rootScope, $stateParams, previousState, entity, MenadzerRestorana, User, KonfiguracijaStolova, Restoran) {
        var vm = this;

        vm.menadzerRestorana = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:menadzerRestoranaUpdate', function(event, result) {
            vm.menadzerRestorana = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
