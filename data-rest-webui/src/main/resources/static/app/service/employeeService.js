(function(){
    var employeeService = function($http, $q, config){
        var FIND_EMPLOYEE_ENDPOINT = config.API_HOST + "/employee?departmentNumber=#depNo#&employeeName=#name#&page=#page#",
            SAVE_EMPLOYEE_ENDPOINT = config.API_HOST + "/employee",
            EMPLOYEE_ENDPOINT = config.API_HOST + "/employee/#id#";

        function reject(error) {
            return $q.reject(error.data ? error.data.message : error.statusText);
        }

        return {
            loadEmployees: function(page, department, employeeName, sort) {
                var url = FIND_EMPLOYEE_ENDPOINT
                        .replace("#depNo#", department.number)
                        .replace("#name#", employeeName)
                        .replace("#page#", page);

                return $http.get(url).then(function(response) {
                    angular.forEach(response.data.content, function(employee) {
                        employee.birthDate = new Date(employee.birthDate);
                        employee.hireDate = new Date(employee.hireDate);
                    });
                    return response.data;
                });
            },

            save: function(employee) {
                return $http.post(SAVE_EMPLOYEE_ENDPOINT, angular.toJson(employee))
                    .then(function(response) {
                        return response.data;
                    }, function(error) {
                        return reject(error);
                    });
            },

            remove: function(employee) {
                return $http.delete(EMPLOYEE_ENDPOINT.replace("#id#", employee.id)).then(function(response) {
                   return response.data;
               }, function(error) {
                    return reject(error);
                });
            }
        };
    }

    employeeApp.factory('EmployeeService', employeeService);
})();
