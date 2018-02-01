(function (angular) {
    'use strict';
    angular.module('ngvalueSelect', []).controller('ExampleController', ['$scope', function ($scope) {
            $scope.data = {
                model: null,
                availableOptions: [
                    {value: 'myString', name: 'string'},
                    {value: 1, name: 'integer'},
                    {value: true, name: 'boolean'},
                    {value: null, name: 'null'},
                    {value: {prop: 'value', id: 1}, name: 'object'},
                    {value: {prop: 'value2', id: 2}, name: 'object2'},
                    {value: ['a'], name: 'array'}
                ]
            };
        }]);
})(window.angular);

//angular.module("crudFichasApi").factory("api", function ($scope, $http) {
//    return{
//        animal: {
//            insert: function (animal) {
//                return $http.post("./animal/insert", animal);
//            },
//            update: function (animal) {
//                return $http.post("./animal/update", animal);
//            },
//            delete: function (animal) {
//                return $http.post("./animal/delete", animal);
//            },
//            getAll: function () {
//                return $http.post("./animal/getAll");
//            }
//        }
//    };
//});