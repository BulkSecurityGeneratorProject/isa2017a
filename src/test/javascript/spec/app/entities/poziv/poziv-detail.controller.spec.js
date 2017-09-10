'use strict';

describe('Controller Tests', function() {

    describe('Poziv Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPoziv, MockGost, MockRezervacija;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPoziv = jasmine.createSpy('MockPoziv');
            MockGost = jasmine.createSpy('MockGost');
            MockRezervacija = jasmine.createSpy('MockRezervacija');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Poziv': MockPoziv,
                'Gost': MockGost,
                'Rezervacija': MockRezervacija
            };
            createController = function() {
                $injector.get('$controller')("PozivDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isaApp:pozivUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
