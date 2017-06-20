(function() {
    employeeListController = function($scope, $uibModal, employeeService, departmentService) {
        $scope.pager = {page:1, totalPages:0};
        $scope.departments = [];
        $scope.department = null;
        $scope.employeeName = '';
        $scope.employees = {};
        $scope.alerts = [];

        function fetchData(){
            departmentService.loadDepartments().then(
                function(data) {
                    $scope.departments = data;
                });
        }

        function refresh(){
            employeeService.loadEmployees($scope.pager.page, $scope.department, $scope.employeeName).then(
                            function(data) {
                                $scope.employees = data;
                                $scope.pager.totalPages = data.totalPages;
                            });
        }

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        }

        $scope.search = function() {
            $scope.pager.page = 0;
            $scope.pager.totalPages = 0;

            refresh();
        }

        $scope.goNext = function() {
            if($scope.pager.page >= $scope.pager.totalPages-1) {
                return;
            }

            $scope.pager.page++;
            refresh();
        }

        $scope.goPrev = function() {
            if($scope.pager.page <= 0) {
                return;
            }

            $scope.pager.page--;
            refresh();
        }

        $scope.edit = function(employee) {
            $uibModal.open({
                templateUrl: 'app/views/employeeForm.html',
                controller: 'EmployeeFormController',
                resolve: {
                    'employee': angular.copy(employee)
                }
            }).result.then(function(employee) {
                employeeService.save(employee).then(function(data) {
                    refresh();
                    $scope.alerts.push({type: 'success', msg: 'Employee saved successfully'});
                }, function(error) {
                    $scope.alerts.push({type: 'danger', msg: 'Unable to save the employee. ' + error});
                });
            });
        }

        $scope.remove = function(employee) {
             employeeService.remove(employee).then(function(data) {
                refresh();
                $scope.alerts.push({type: 'success', msg: 'Employee deleted successfully'});
             }, function(error) {
                $scope.alerts.push({type: 'danger', msg: 'Unable to delete the employee. ' + error});
             });
        }


        fetchData();
    };

    employeeFormController = function($scope, $uibModalInstance, employee) {
        $scope.employee = employee;
        $scope.popup = {
            birthdate: false,
            hiredate: false
        }

        $scope.open = function(name) {
            $scope.popup[name] = true;
        }

        $scope.cancel = function() {
            $uibModalInstance.dismiss();
        }

        $scope.save = function() {
            $uibModalInstance.close($scope.employee);
        }
    };

    employeeApp.controller('EmployeeListController',
        ['$scope', '$uibModal', 'EmployeeService', 'DepartmentService', employeeListController]);

    employeeApp.controller('EmployeeFormController', employeeFormController);


})();
