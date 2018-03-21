angular.module("crudFichas", []).controller("controller", function ($scope, $http) {
    // VARIAVEIS //
    // FICHA
    $scope.dataInicio;
    $scope.dataFim;
    $scope.id = '';
    $scope.nomeRotuloFormulario;
    $scope.listaFichas = [];
    $scope.filtroPorId = false;
    $scope.formularioAtivo = false;
    $scope.formularioEditarAtivo = false;
    $scope.dataAtiva = true;
    // ANIMAL
    $scope.nomeRotuloFormularioAnimal;
    $scope.listaAnimais = [];
    $scope.formularioAtivoAnimal = false;

    // SISTEMA
    $scope.ativaAba = function (aba) {
        $scope.abaFicha = (aba === 'abaFicha');
        $scope.abaAnimal = (aba === 'abaAnimal');
    };
    $scope.abaFicha = true;
//    toastr.error('Usuário sem permissão para Excluir');

    // FICHA
    $scope.zeraFicha = function () {
        $scope.ficha = {
            "id": 0,
            "dataRegistro": "",
            "status": true,
            "observacao": "",
            "animais": [
                {
                    "id": 0,
                    "nome": ""
                }
            ]
        };
    };

    $scope.carregarFichas = function () {
        $scope.idFiltro = 0;
        $scope.inicio = '0';
        $scope.fim = '0';
        if ($scope.dataInicio) {
            $scope.inicio = $scope.dataInicio + ' 00:00:00';
        }
        if ($scope.dataFim) {
            $scope.fim = $scope.dataFim + ' 23:59:59';
        }
        if ($scope.filtroPorId && $scope.id) {
            $scope.idFiltro = $scope.id;
        }
        $scope.url = '/CrudFichas/webresources/ficha/listar/' + $scope.idFiltro + '/' + $scope.inicio + '/' + $scope.fim;
        $http({
            url: $scope.url,
            method: 'GET'
        }).then(function (resposta) {
            console.log(resposta.data);
            $scope.listaFichas = resposta.data;
        });
        $scope.formularioAtivo = false;
    };

    $scope.salvarFicha = function () {
        $scope.url = '/CrudFichas/webresources/ficha/salvar/';
        console.log($scope.ficha);
        console.log($scope.ficha.animais);
        $http.post($scope.url, $scope.ficha).then(function (response) {
            $scope.formularioAtivo = false;
            $scope.carregarFichas();
        }, function (respose) {
        });
    };

    $scope.excluirFicha = function () {
        $scope.url = '/CrudFichas/webresources/ficha/excluir/' + $scope.ficha.id;
        $http({
            url: $scope.url,
            method: 'GET'
        }).then(function () {
            $scope.carregarFichas();
        });
    };
    
    $scope.confirmaExcluirFicha = function (ficha) {
        console.log(ficha)
        $scope.ficha = ficha;
    };
    

    $scope.novaFicha = function () {
        $scope.zeraFicha();
        $scope.nomeRotuloFormulario = 'Cadastrar Ficha';
        $scope.formularioAtivo = true;
        $scope.formularioEditarAtivo = false;
        $scope.dataAtiva = false;
    };

    $scope.editarFicha = function (ficha) {
        $scope.formularioAtivo = true;
        $scope.nomeRotuloFormulario = 'Editar Ficha';
        $scope.dataAtiva = true;
        $scope.formularioEditarAtivo = true;
        $scope.ficha.id = ficha.id;
        $scope.ficha.dataRegistro = ficha.dataRegistro;
        $scope.ficha.status = ficha.status;
        $scope.ficha.observacao = ficha.observacao;
        $scope.ficha.animais = ficha.animais;
    };
    $scope.zeraFicha();

    // ANIMAL
    $scope.zeraAnimal = function () {
        $scope.animal = {
            "id": 0,
            "nome": ""
        };
    };

    $scope.carregarAnimais = function () {
        $http({
            url: '/CrudFichas/webresources/animal/listar/',
            method: 'GET'
        }).then(function (resposta) {
            $scope.listaAnimais = resposta.data;
        });
        $scope.formularioAtivoAnimal = false;
    };

    $scope.salvarAnimal = function () {
        $scope.url = '/CrudFichas/webresources/animal/salvar/';
        $http.post($scope.url, $scope.animal, $scope.retorno).then(function (response) {
            $scope.carregarAnimais();
            console.log("sucesso");
        }, function (respose) {
            console.log("falha");
        });
    };

    $scope.excluirAnimal = function () {
        $scope.url = '/CrudFichas/webresources/animal/excluir/';
        $http.post($scope.url, $scope.animal, $scope.retorno).then(
                function (response) {
                    $scope.carregarAnimais();
//                    toastr.error('Usuário sem permissão para Excluir');
                    console.log("sucesso");
                }, function (respose) {
            console.log("falha");
        });
    };

    $scope.novoAnimal = function () {
        $scope.zeraAnimal();
        $scope.nomeRotuloFormularioAnimal = 'Cadastrar Animal';
        $scope.formularioAtivoAnimal = true;
    };

    $scope.editarAnimal = function (animal) {
        $scope.formularioAtivoAnimal = true;
        $scope.nomeRotuloFormularioAnimal = 'Editar Animal';
        $scope.animal.id = animal.id;
        $scope.animal.nome = animal.nome;
    };
    
    $scope.confirmaExcluirAnimal = function (animal) {
//        $scope.animal.id = animal.id;
//        $scope.animal.nome = animal.nome;
        $scope.animal = animal;
    };
    $scope.zeraAnimal();
    $scope.carregarAnimais();
    $scope.carregarFichas();

});
