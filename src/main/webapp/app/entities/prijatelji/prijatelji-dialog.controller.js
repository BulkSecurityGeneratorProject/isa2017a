(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PrijateljiDialogController', PrijateljiDialogController);

    PrijateljiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Prijatelji'];

    function PrijateljiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Prijatelji) {
        var vm = this;

        vm.prijatelji = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.prijatelji.id !== null) {
                Prijatelji.update(vm.prijatelji, onSaveSuccess, onSaveError);
            } else {
                Prijatelji.save(vm.prijatelji, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isaApp:prijateljiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.postalanZahtev = false;
        vm.datePickerOpenStatus.prihvacenZahtev = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
