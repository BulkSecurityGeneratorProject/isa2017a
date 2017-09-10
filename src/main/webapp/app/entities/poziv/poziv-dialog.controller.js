(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PozivDialogController', PozivDialogController);

    PozivDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Poziv', 'Gost', 'Rezervacija'];

    function PozivDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Poziv, Gost, Rezervacija) {
        var vm = this;

        vm.poziv = entity;
        vm.clear = clear;
        vm.save = save;
        vm.gosts = Gost.query();
        vm.rezervacijas = Rezervacija.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.poziv.id !== null) {
                Poziv.update(vm.poziv, onSaveSuccess, onSaveError);
            } else {
                Poziv.save(vm.poziv, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isaApp:pozivUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
