package com.example.demo.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.domain.Course;
import com.example.demo.service.*;

import java.util.Scanner;

@Controller
public class CourseController {
    public static final Scanner scanner = new Scanner(System.in);
    private final CourseService courseService;
    private Role role;


    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
       
    }

    @Deprecated
    static int getSubMenu(String... items) {
        System.out.println();
        System.out.println("0. Log Out.");
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + 1 + ". " + items[i]);
        }
        return CourseController.scanner.nextInt();
    }

    @Deprecated
    static void newPage() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }


    @Deprecated
    private int getMainMenu(String... items) {
        System.out.println();
        System.out.println("0. Exit.");
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + 1 + ". " + items[i]);
        }
        int cmd = scanner.nextInt();
        if (cmd == 0) {
            try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return cmd;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam(name = "userId") String userId,
                              @RequestParam(name = "pwd") String pwd,
                              @RequestParam(name = "role") String role) {
        ModelAndView mav = new ModelAndView(role);
        try {
            User user = null, findUser = null;
           
            if (user == null || !user.equals(findUser)) {
                mav.setViewName("/");
                mav.addObject("error", "noUser");
                return mav;
            }
            mav.addObject("user", findUser);
            return mav;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/signUp")
    @ResponseBody
    public boolean signUp(@RequestParam(name = "userId") String userId,
                          @RequestParam(name = "pwd") String pwd,
                          @RequestParam(name = "role") String role) {
        try {
            User user = null, findUser = null;
            
            if (user == null || findUser == null) {
                return false;
            }
            return user.equals(findUser);
        } catch (Exception e) {
            return false;
        }
    }

    @Deprecated
    private void forTest() {
        Course ue = new Course();
        ue.setCode("c1002");
        ue.setName("UE");
        ue.setMainTopic("UE Main Topic");
        Course it = new Course();
        it.setCode("c1004");
        it.setName("ITIS");
        it.setMainTopic("ITIS Main Topic");
        Course pf = new Course();
        pf.setName("PF");
        pf.setCode("c0001");
        pf.setMainTopic("PF Main Topic");
        Course ap = new Course();
        ap.setCode("c0002");
        ap.setName("AP");
        ap.setMainTopic("AP Main Topic");
        ap.addPrerequisite(pf);
        Course sef = new Course();
        sef.setCode("c1001");
        sef.setName("SEF");
        sef.setMainTopic("SEF Main Topic");
        sef.addPrerequisite(pf, ap);
        Course aa = new Course();
        aa.setCode("c1003");
        aa.setName("AA");
        aa.setMainTopic("AA Main Topic");
        aa.addPrerequisite(pf);
        courseService.save(ue);
        courseService.save(it);
        courseService.save(pf);
        courseService.save(ap);
        courseService.save(sef);
        courseService.save(aa);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test")
    
    @Deprecated
    public void initial() {
        while (true) {
            try {
                Thread.sleep(500);
                newPage();
                System.out.println("Welcome to High 5 Course Management System.");
                System.out.println("Please Select:");
                int item = getMainMenu("Login.", "Sign Up.");

                switch (item) {
                    case 1:
                        login();
                        break;
                    case 2:
                        signUp();
                        break;
                }
            } catch (Exception ex) {
                System.err.println("Oops! We have detected an issue.");
                System.err.println(ex.getMessage());
                System.err.println("Please try again.");
            }
        }
    }

    @Deprecated
    private String[] getProfile(boolean signUp) {
        String[] items = new String[4];
        System.out.println("Please enter your User ID:");
        System.out.print("User ID: ");
        items[0] = scanner.next().trim();

        if (signUp) {
            System.out.println("Please enter your User Name: ");
            items[3] = scanner.next().trim();
        }
        System.out.print("Please enter your Password:");
        items[1] = scanner.next().trim();

        System.out.println("Please select your role:");
        items[2] = String.valueOf(getMainMenu("Student", "Admin", "Coordinator", "Lecturer"));
        return items;
    }

    @Deprecated
    private void login() throws Exception {
        String[] items = getProfile(false);
        String userId = items[0];
        String pwd = items[1];
        int item = Integer.parseInt(items[2]);
        User user = null, findUser = null;
        
        if (user == null || !user.equals(findUser)) {
            throw new Exception("Incorrect username or password.");
        }

        System.out.println("Login successfully! Welcome, " + findUser.getName());
        newPage();
        
    }

    @Deprecated
    private void signUp() {
        String[] profile = getProfile(true);
        String userId = profile[0];
        String name = profile[3];
        String pwd = profile[1];
        int item = Integer.parseInt(profile[2]);
        User user;
        
        System.out.println("Sign up successfully! Welcome, " + name);
    }


    private enum Role {
        Student,
        Admin,
        Coordinator,
        Lecturer
    }
}