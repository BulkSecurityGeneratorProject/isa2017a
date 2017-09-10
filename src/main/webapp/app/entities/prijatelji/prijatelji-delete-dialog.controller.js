(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PrijateljiDeleteController',PrijateljiDeleteController);

    PrijateljiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Prijatelji'];

    function PrijateljiDeleteController($uibModalInstance, entity, Prijatelji) {
        var vm = this;

        vm.prijatelji = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Prijatelji.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
