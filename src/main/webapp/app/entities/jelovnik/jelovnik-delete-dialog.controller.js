(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('JelovnikDeleteController',JelovnikDeleteController);

    JelovnikDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jelovnik'];

    function JelovnikDeleteController($uibModalInstance, entity, Jelovnik) {
        var vm = this;

        vm.jelovnik = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jelovnik.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
