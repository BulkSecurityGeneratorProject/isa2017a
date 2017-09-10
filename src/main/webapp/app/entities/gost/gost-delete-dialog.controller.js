(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('GostDeleteController',GostDeleteController);

    GostDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gost'];

    function GostDeleteController($uibModalInstance, entity, Gost) {
        var vm = this;

        vm.gost = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gost.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
