(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('RacunDeleteController',RacunDeleteController);

    RacunDeleteController.$inject = ['$uibModalInstance', 'entity', 'Racun'];

    function RacunDeleteController($uibModalInstance, entity, Racun) {
        var vm = this;

        vm.racun = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Racun.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
