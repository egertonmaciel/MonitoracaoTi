angular.module("crudFichasApi").factory("api", function ($scope, $http) {
    return{
        animal: {
            insert: function (animal) {
                return $http.post("./animal/insert", animal);
            },
            update: function (animal) {
                return $http.post("./animal/update", animal);
            },
            delete: function (animal) {
                return $http.post("./animal/delete", animal);
            },
            getAll: function () {
                return $http.post("./animal/getAll");
            }
        }
    };
});

//2
