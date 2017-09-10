(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PozivZaPrikupljanjeNDeleteController',PozivZaPrikupljanjeNDeleteController);

    PozivZaPrikupljanjeNDeleteController.$inject = ['$uibModalInstance', 'entity', 'PozivZaPrikupljanjeN'];

    function PozivZaPrikupljanjeNDeleteController($uibModalInstance, entity, PozivZaPrikupljanjeN) {
        var vm = this;

        vm.pozivZaPrikupljanjeN = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PozivZaPrikupljanjeN.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
