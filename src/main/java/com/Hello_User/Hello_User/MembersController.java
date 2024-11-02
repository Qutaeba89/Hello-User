package com.Hello_User.Hello_User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



@Controller
public class MembersController {
   private final List<Member> members = new ArrayList<>(); // för att spara data in i backend
   private final AtomicLong counter = new AtomicLong(1); // det ge en uniqe number to varje ny member börjar med 1

    @GetMapping("/login") // login hemsida
    public String longinPage(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session){ // login som skafade functions för, änvde jag HttpSesstion för att ha koll på inloggning på admin.
        if("admin".equals(username) && "admin".equals(password)){
            session.setAttribute("isAdmin", true); // här änvde jag set för säga till server detta är admin  usename och lösenord ska sparas och ska jag kalla detta sen
            System.out.println("Admin has logged in.");
            return "redirect:/home"; // här när inmating var rätt ska skicka admin till hemsida
        }else{
            model.addAttribute("error", "Invalid username or password");
        }return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // här jag ropade httpsesstion för att säg när admin clicka på logout, data username och lösen ord ska raderas.
        return "redirect:home";//<<< home or start check it
    }

    @GetMapping("/home")
    public String homePage(HttpSession session , Model model){
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin"); // den här boolean för att ha koll om admin är forfarande inloggat detta data skickas till server
        model.addAttribute("isAdmin", isAdmin !=null && isAdmin); // detta här data inlogging ska skickas till Html att kolla om admin är fortfarande inloggat.
        return "home";
    }

    @GetMapping("/member")
    public String ShowMembers(HttpSession session, Model model){
        model.addAttribute("members", members); // model för att skicka data till HTML file
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        model.addAttribute("isAdmin", isAdmin !=null && isAdmin);
        model.addAttribute("members", members);
        return "member";
    }
    @GetMapping("/add-member")
    public String addMemberform(HttpSession session){
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin"); 
        if(isAdmin !=null && isAdmin){ // här om admin är inloggade ska skicka til add member sida där kan lägg till en ny member..
            return "/add-member";
        }else{
            System.out.println("You are not admin!");
        }return "redircet:login";
    }

    @PostMapping("/add-member")
    public String addMember(@RequestParam String name,@RequestParam  String email, @RequestParam int phoneNumber, HttpSession session ){
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if(isAdmin !=null && isAdmin){
            Member member =  new Member(counter.getAndIncrement(), name ,email, phoneNumber); // counter.getAndIncrement() Använder jag det för att det bli enkelt att ändra iframtiden  istallet av ändra på members name.
            members.add(member);
            return "redirect:/member";
        }else{
            System.out.println("You are not admin!");
        }return "redircet:login";
     
        }
    @PostMapping("/delete-member")
    public String deleteMember(@RequestParam Long id , HttpSession session){
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if(isAdmin !=null && isAdmin){
            members.removeIf(member -> member.getId().equals(id)); //getid here för att det är uniqe id för varje name så lättare att hitta istallet av name.
        return "redirect:/member";
        }else{
            System.out.println("You are not admin!");
        }return "redircet:login";
        }
}
