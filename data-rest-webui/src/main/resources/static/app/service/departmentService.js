(function(){
    var departmentService = function($http, config){
        var DEPARTMENT_ENDPOINT_URL = config.API_HOST + '/department';

        return {
            loadDepartments: function() {
                return $http.get(DEPARTMENT_ENDPOINT_URL).then(
                    function(response){
                        return response.data;
                    });
            }
        };
    }

    employeeApp.factory('DepartmentService', ['$http', 'config', departmentService]);
})();
