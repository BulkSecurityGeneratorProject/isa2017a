(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('JelovnikDetailController', JelovnikDetailController);

    JelovnikDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Jelovnik', 'Jelo'];

    function JelovnikDetailController($scope, $rootScope, $stateParams, previousState, entity, Jelovnik, Jelo) {
        var vm = this;

        vm.jelovnik = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isaApp:jelovnikUpdate', function(event, result) {
            vm.jelovnik = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
