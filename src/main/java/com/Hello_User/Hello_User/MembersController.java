package com.Hello_User.Hello_User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MembersController {
   private final List<Member> members = new ArrayList<>();
   private final AtomicLong counter = new AtomicLong(1); // det ge en uniqe number to varje ny member börjar med 1

    @GetMapping("/home")
    public String homePage(){
        return "start";
    }
    @GetMapping("/login")
    public String longinPage(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model){
        if("admin".equals(username) && "admin".equals(password)){
            model.addAttribute("isAdmin", true);
        }else{
            model.addAttribute("error", "Invalid username or password");
        }return "login";
    }

    @GetMapping("/member")
    public String ShowMembers(Model model){
        model.addAttribute("members", members); // model för att skicka data till HTML file
        return "member";
    }
    @GetMapping("/form")
    public String ShowMembersForm(){
        return "form";
    }
    @PostMapping("/add")
    public String AddMember(@RequestParam String name,@RequestParam  String email, @RequestParam int phoneNumber ){
        Member member =  new Member(counter.getAndIncrement(), name ,email, phoneNumber); // Använder jag det för att det bli enkelt att ändra iframtiden  istallet av ändra på members.
        members.add(member);
        return "redirect:/member";
        }
    @PostMapping("/delete")
    public String deleteMember(@RequestParam Long id){
        members.removeIf(member -> member.getId().equals(id)); // getid here för att det är uniqe id för varje name så lättare att hitta istallet av name.
        return "redirect:/member";
        }  
}
