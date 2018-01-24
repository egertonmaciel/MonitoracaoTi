angular.module("crudFichas", []).controller("Controller", function ($scope, $http) {
    $scope.hoje = (1900 + new Date().getYear()) + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
    $scope.dataInicio;
    $scope.dataFim;
    $scope.id = '';
    $scope.nomeRotuloFormulario;
    $scope.nomeRotuloFormularioAnimal;
    $scope.listaFichas = [];
    $scope.listaAnimais = [];
    $scope.filtroPorId = false;
    $scope.formularioAtivo = false;
    $scope.formularioEditarAtivo = false;
    $scope.formularioAtivoAnimal = false;
    $scope.checkAtivo = true;
    $scope.dataAtiva = true;

    $scope.ativaAba = function (aba) {
        $scope.abaFicha = (aba === 'abaFicha');
        $scope.abaAnimal = (aba === 'abaAnimal');
    };

    $scope.zeraFrmInclusao = function () {
        $scope.frmInclusao = {
            "id": 0,
            "dataRegistro": "",
            "status": true,
            "observacao": "",
            "animais": [{
                    "nome": ""}
            ]
        };
    };

    $scope.carregarFichas = function () {
        $scope.inicio = '0';
        $scope.fim = '0';
        if ($scope.dataInicio) {
            $scope.inicio = $scope.dataInicio + ' 00:00:00';
        }
        if ($scope.dataFim) {
            $scope.fim = $scope.dataFim + ' 23:59:59';
        }

        console.log($scope.dataInicio);
        console.log($scope.dataFim);
        $scope.url = '/CrudFichas/webresources/CrudFichas/fichasPorData/' + $scope.inicio + '/' + $scope.fim;
        if ($scope.filtroPorId) {
            $scope.url = '/CrudFichas/webresources/CrudFichas/fichaPorId/' + $scope.id;
        }
//        console.log($scope.url);
        $http({
            url: $scope.url,
            method: 'GET'
        }).then(function (resposta) {
//            console.log(resposta);
            $scope.listaFichas = resposta.data;
        });
        $scope.formularioAtivo = false;
    };

    $scope.salvarFicha = function () {
        $scope.url = '/CrudFichas/webresources/CrudFichas/salvarFicha/';
        $http.post($scope.url, $scope.frmInclusao).then(function (response) {
            $scope.formularioAtivo = false;
            $scope.carregarFichas();
        }, function (respose) {
            $scope.formularioAtivo = false;
            $scope.carregarFichas();
        });
    };

    $scope.excluirFicha = function (ficha) {
        $scope.url = '/CrudFichas/webresources/CrudFichas/excluirFicha/' + ficha.id;
        $http({
            url: $scope.url,
            method: 'GET'
        }).then(function () {
            $scope.carregarFichas();
        });
    };

    $scope.editarFicha = function (ficha) {
        $scope.formularioAtivo = true;
        $scope.nomeRotuloFormulario = 'Editar Ficha';
        $scope.dataAtiva = true;
        $scope.formularioEditarAtivo = true;
        $scope.frmInclusao.id = ficha.id;
        $scope.frmInclusao.dataRegistro = ficha.dataRegistro;
        $scope.frmInclusao.status = ficha.status;
        $scope.frmInclusao.observacao = ficha.observacao;
        $scope.frmInclusao.animais = ficha.animais;
        console.log($scope.frmInclusao);
    };

    $scope.novaFicha = function () {
        $scope.zeraFrmInclusao();
        $scope.nomeRotuloFormulario = 'Cadastrar Ficha';
        $scope.formularioAtivo = true;
        $scope.formularioEditarAtivo = false;
        $scope.dataAtiva = false;
    };

    $scope.carregarAnimais = function () {
        $http({
            url: '/CrudFichas/webresources/CrudFichas/animais/',
            method: 'GET'
        }).then(function (resposta) {
//            console.log(resposta);
            $scope.listaAnimais = resposta.data;
        });
        $scope.formularioAtivoAnimal = false;
    };

    $scope.zeraFrmInclusaoAnimal = function () {
        $scope.frmInclusaoAnimal = {
            "id": 0,
            "nome": ""
        };
    };

    $scope.salvarAnimal = function () {
        $scope.url = '/CrudFichas/webresources/CrudFichas/salvarAnimal/';
        $http.post($scope.url, $scope.frmInclusaoAnimal, $scope.retorno).then(function (response) {
            $scope.carregarAnimais();
            console.log("sucesso");
        }, function (respose) {
            console.log("falha");
        });
    };

    $scope.editarAnimal = function (animal) {
        $scope.formularioAtivoAnimal = true;
        $scope.nomeRotuloFormularioAnimal = 'Editar Animal';
        $scope.frmInclusaoAnimal.id = animal.id;
        $scope.frmInclusaoAnimal.nome = animal.nome;
    };

    $scope.deletarAnimal = function (animal) {
        $scope.url = '/CrudFichas/webresources/CrudFichas/deletarAnimal/';
        $http.post($scope.url, animal, $scope.retorno).then(
                function (response) {
                    $scope.carregarAnimais();
                    console.log("sucesso");
                }, function (respose) {
            console.log("falha");
        });
    };

    $scope.novoAnimal = function () {
        $scope.zeraFrmInclusaoAnimal();
        $scope.nomeRotuloFormularioAnimal = 'Cadastrar Animal';
        $scope.formularioAtivoAnimal = true;
    };

    $scope.zeraFrmInclusao();
    $scope.zeraFrmInclusaoAnimal();
    $scope.carregarAnimais();
});
