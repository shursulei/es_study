package com.shursulei.es.model;

/**
 * @auther shursulei
 * @Desciption
 * @Email 1368455669@qq.com
 * @Date: 2019/1/31 15:10
 */
public class Employee {
    private String name;
    private String age;
    private String position;
    private String joindate;
    private Double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
