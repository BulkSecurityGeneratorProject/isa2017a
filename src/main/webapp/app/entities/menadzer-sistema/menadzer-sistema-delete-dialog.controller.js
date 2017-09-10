(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerSistemaDeleteController',MenadzerSistemaDeleteController);

    MenadzerSistemaDeleteController.$inject = ['$uibModalInstance', 'entity', 'MenadzerSistema'];

    function MenadzerSistemaDeleteController($uibModalInstance, entity, MenadzerSistema) {
        var vm = this;

        vm.menadzerSistema = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MenadzerSistema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
