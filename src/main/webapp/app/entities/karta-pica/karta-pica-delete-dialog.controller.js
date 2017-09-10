(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('KartaPicaDeleteController',KartaPicaDeleteController);

    KartaPicaDeleteController.$inject = ['$uibModalInstance', 'entity', 'KartaPica'];

    function KartaPicaDeleteController($uibModalInstance, entity, KartaPica) {
        var vm = this;

        vm.kartaPica = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            KartaPica.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
