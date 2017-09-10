(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerRestoranaDeleteController',MenadzerRestoranaDeleteController);

    MenadzerRestoranaDeleteController.$inject = ['$uibModalInstance', 'entity', 'MenadzerRestorana'];

    function MenadzerRestoranaDeleteController($uibModalInstance, entity, MenadzerRestorana) {
        var vm = this;

        vm.menadzerRestorana = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MenadzerRestorana.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
