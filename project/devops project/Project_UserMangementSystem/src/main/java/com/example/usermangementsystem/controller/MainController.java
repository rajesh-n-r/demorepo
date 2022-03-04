package com.example.usermangementsystem.controller;

import com.example.usermangementsystem.model.User;
import com.example.usermangementsystem.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    Repository repo;


    public boolean isAUser = false;

    @GetMapping({"/","/home"})
    public String home(){
        return "index";
    }


    //Login A User
    @PostMapping("/dashboard")
    public String loginPage(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        System.out.println(username);
        System.out.println(password);
        List<User> list = repo.findAll();
        for (int i=0 ; i<list.size(); i++){
            User usr = list.get(i);
            System.out.println(usr);
            if (username.equalsIgnoreCase(usr.getUsername()) && password.equalsIgnoreCase(usr.getPassword())){
////            isAUser = tru

                model.addAttribute("isAUser" , true);
                model.addAttribute("users", list);
                return "home";
            }
        }

        model.addAttribute("isAUser" , isAUser);
        return "index";
    }
    // Dashboard or Admin Page
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<User> list = repo.findAll();
        System.out.println(list);
        model.addAttribute("users", list);
        return "home";
    }
//    @GetMapping("/delete/{user_id}")
//    public String deleteUser(@PathVariable int user_id, Model model){
//        Optional<User> usr = repo.findById(user_id);
//        if(repo.existsById(user_id)){
//            repo.deleteById(user_id);
//        }
//        List<User> list = repo.findAll();
//        model.addAttribute("users", list);
//        System.out.println("user deleted");
//        return "home";
//    }
    @GetMapping("/delete/{uid}")
    public String deleteUsr(@PathVariable(name = "uid") int uid, Model model){
        if(repo.existsById(uid)){
            repo.deleteById(uid);
        }
        List<User> list = repo.findAll();
        model.addAttribute("users", list);
        return "redirect:/dashboard";
    }

    @GetMapping("/edit/{uid}")
    public String editUser(@PathVariable(name = "uid") int uid, Model model){
        Optional<User> usr = repo.findById(uid);
        if (usr.isPresent()){
            User user = usr.get();
            model.addAttribute("userDetails", user);
            return "editUser";
        }
        return "redirect:/dashboard";
    }


    // Add User Endpoint
    @GetMapping("/addUser")
    public String addUserView(){
        return "addUser";
    }

    //Saving user details into the database
    @PostMapping("/addingUser")
    public String saveUserToDB(Model model, @ModelAttribute("user") User user){

        System.out.println(user);

        repo.save(user);



        List<User> list = repo.findAll();
        model.addAttribute("users", list);
        return "home";
    }


    @GetMapping("/logout")
    public String logoutUser(){
        return "redirect:/home";
    }

}
