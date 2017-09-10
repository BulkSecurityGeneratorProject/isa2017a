(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerSistemaDetailController', MenadzerSistemaDetailController);

    MenadzerSistemaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MenadzerSistema', 'User'];

    function MenadzerSistemaDetailController($scope, $rootScope, $stateParams, previousState, entity, MenadzerSistema, User) {
        var vm = this;

        vm.menadzerSistema = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:menadzerSistemaUpdate', function(event, result) {
            vm.menadzerSistema = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
