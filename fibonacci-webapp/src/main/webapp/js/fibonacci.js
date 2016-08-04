var myApp = angular.module('FibonacciApp', []);

myApp.controller('Fibonacci', function Fibonacci($scope, $http) {
	$scope.load = function() {
		$scope.exception = "";
		$http({
			url : 'http://localhost:8080/compute',
			method : "GET",
			params : {
				id : $scope.index
			}
		}).then(function successCallback(result) {
			$scope.result = result.data;
		}, function errorCallback(error) {
			$scope.exception = error.data.exception;
		})
	};
	$scope.load();
});