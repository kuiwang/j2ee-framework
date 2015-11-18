package com.fengjing.framework.spring.remoting;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "hello," + name + " from Spring RMI";
    }

    @Override
    public User modify(User user) {
        System.out.println("ԭ�û���:" + user.getName() + "������:" + user.getPassword());
        user.setName("admin");
        user.setPassword("111111");
        System.out.println("�޸ĺ���û���:" + user.getName() + "������:" + user.getPassword());
        return user;
    }

}
