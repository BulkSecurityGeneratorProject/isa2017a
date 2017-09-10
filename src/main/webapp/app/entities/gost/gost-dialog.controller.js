(function() {
    'use strict';

    angular
        .module('isaApp')
        .controller('GostDialogController', GostDialogController);

    GostDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Gost', 'User', 'Rezervacija', 'ZahtevZaPrijateljstvo', 'Poziv'];

    function GostDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Gost, User, Rezervacija, ZahtevZaPrijateljstvo, Poziv) {
        var vm = this;

        vm.gost = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.rezervacijas = Rezervacija.query();
        vm.zahtevzaprijateljstvos = ZahtevZaPrijateljstvo.query();
        vm.pozivs = Poziv.query();
        vm.gosts = Gost.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gost.id !== null) {
                Gost.update(vm.gost, onSaveSuccess, onSaveError);
            } else {
                Gost.save(vm.gost, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isaApp:gostUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
