(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PozivDeleteController',PozivDeleteController);

    PozivDeleteController.$inject = ['$uibModalInstance', 'entity', 'Poziv'];

    function PozivDeleteController($uibModalInstance, entity, Poziv) {
        var vm = this;

        vm.poziv = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Poziv.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
