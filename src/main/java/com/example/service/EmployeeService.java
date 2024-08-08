package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

    /**
     * 従業員一覧を全件検索する業務処理
     */
@Service
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 従業員情報を全権取得する
     * @return 従業員情報
     */
    public List<Employee> showList(){
        return employeeRepository.findAll();
    }
}
