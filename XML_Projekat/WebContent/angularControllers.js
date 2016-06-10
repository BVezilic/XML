
var app = angular.module('XML_App', []).controller('mainControler', function($scope) {
    $scope.listaAkata = ["akt o oduzimanju radne dozvole", "akt2", "akt3"];
    console.log($scope.listaAkata);
});
