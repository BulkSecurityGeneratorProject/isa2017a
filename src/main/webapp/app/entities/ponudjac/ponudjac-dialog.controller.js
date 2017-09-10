(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('PonudjacDialogController', PonudjacDialogController);

    PonudjacDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Ponudjac', 'User'];

    function PonudjacDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Ponudjac, User) {
        var vm = this;

        vm.ponudjac = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ponudjac.id !== null) {
                Ponudjac.update(vm.ponudjac, onSaveSuccess, onSaveError);
            } else {
                Ponudjac.save(vm.ponudjac, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isaApp:ponudjacUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
