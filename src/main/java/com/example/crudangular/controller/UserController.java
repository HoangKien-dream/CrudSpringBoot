package com.example.crudangular.controller;

import com.example.crudangular.entity.User;
import com.example.crudangular.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/user")
public class UserController {
    @Autowired
     UserRepository userRepository;
    private static List<User> userList;
    static {
        userList = new ArrayList<>();
        userList.add(new User("Hoàng","Kiên","0964866701"));
        userList.add(new User("Hoàng","Nguyên","0964866702"));
        userList.add(new User("Ngô","Quang","0964866703"));
        userList.add(new User("Chu","Quân","0964866704"));
        userList.add(new User("Phan","Tùng","0964866705"));
    }
    @RequestMapping(method = RequestMethod.POST)
    public List<User> save(){
        return userRepository.saveAll(userList);
    }
}
