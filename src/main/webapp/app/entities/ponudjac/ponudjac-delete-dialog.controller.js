(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PonudjacDeleteController',PonudjacDeleteController);

    PonudjacDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ponudjac'];

    function PonudjacDeleteController($uibModalInstance, entity, Ponudjac) {
        var vm = this;

        vm.ponudjac = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ponudjac.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
