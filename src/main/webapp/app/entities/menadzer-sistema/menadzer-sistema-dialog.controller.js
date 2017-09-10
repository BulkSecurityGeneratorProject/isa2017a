(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('MenadzerSistemaDialogController', MenadzerSistemaDialogController);

    MenadzerSistemaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MenadzerSistema', 'User'];

    function MenadzerSistemaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MenadzerSistema, User) {
        var vm = this;

        vm.menadzerSistema = entity;
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
            if (vm.menadzerSistema.id !== null) {
                MenadzerSistema.update(vm.menadzerSistema, onSaveSuccess, onSaveError);
            } else {
                MenadzerSistema.save(vm.menadzerSistema, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isaApp:menadzerSistemaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
