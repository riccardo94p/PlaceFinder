package com.example.PlaceFinder.controller;

import com.example.PlaceFinder.BoardClient;
import com.example.PlaceFinder.DBManager;
import com.example.PlaceFinder.Message;
import com.example.PlaceFinder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;

import java.util.List;

//-Djava.security.manager -Djava.security.policy=/home/riccardo/Scrivania/PlaceFinder/WebServer/myprogram.policy -Djava.rmi.server.codebase=http://localhost:1099/RemoteServer
@Controller
public class MainController {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private DBManager service;
    @Autowired
    private BoardClient boardClient;

    @GetMapping("/testconcurrentreserve")
    public @ResponseBody void testconcurrentreserve(@RequestParam String username, @RequestParam int slotId,
                                                @RequestParam String room, @RequestParam String dateStr) {
        Date d = Date.valueOf(dateStr);
        boolean result = service.userReservation(username, slotId, room, d);
        if(result){
            System.out.println("Numero posti: " + service.getNumReservations(d, room, slotId));
        }
        else {
            System.out.println("Prenotazione fallita");
        }
    }

    @GetMapping("/browsereservation")
    public @ResponseBody void browsereservation() {
        List<Reservation> r = service.browseUserReservations("aaa1");
        for(Reservation x: r){
            System.out.println("Reservations: " + x.getId().getReservationDate() + " - " + x.getId().getRoomId() + " - " + x.getId().getSlotId());
        }
    }

    @GetMapping("/reserve")
    public @ResponseBody void reserve() {
        Date d = Date.valueOf("2021-01-27");
        boolean result = service.userReservation("aaa1", 0, "b12", d);
        if(result){
            List<Reservation> r = service.browseUserReservations("aaa1");
        }
    }

    @RequestMapping(value="/main", method = RequestMethod.GET)
    public String main(Model model, Principal principal) {
        String username = principal.getName();
        User u = service.getUser(username);
        List<Slot> slots = service.browseSlots();
        List<Room> rooms = service.getRooms();

        List<Message> messages = boardClient.readMessages(10);
        messages.sort((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()));

        model.addAttribute("rooms", rooms);
        model.addAttribute("slots", slots);
        model.addAttribute("username", username);//loggedUser.getUsername());
        model.addAttribute("messages", messages);
        model.addAttribute("notification", u.getCovidNotification());
        return "main";
    }

    @RequestMapping("/admin")
    public String admin(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);

        List<Room> rooms = service.getRooms();
        List<User> users = service.browseUsers();
        List<Message> messages = boardClient.readMessages(10);
        messages.sort((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()));

        model.addAttribute("rooms", rooms);
        model.addAttribute("users", users);
        model.addAttribute("messages", messages);
        return "admin";
    }

    @RequestMapping("/addRoom")
    public String addRoom(Model model, @RequestParam String id,
                                      @RequestParam int numseats,
                                      @RequestParam float capacity) {
        System.out.println("[DBG]: /addRoom parameters "+id+" "+numseats+" "+capacity);

        //Of course this is not ideal nor secure: there is no trace of which admin performed this operation.
        //This is only meant for demonstration purposes
        service.addRoom(id, numseats, capacity);

        return "admin";
    }

    @RequestMapping("/editCapacity")
    public String editCapacity(Model model, @RequestParam String id, @RequestParam float capacity) {

        //Of course this is not ideal nor secure: there is no trace of which admin performed this operation.
        //This is only meant for demonstration purposes
        service.changeCapacity(id, capacity);

        return "admin";
    }

    @RequestMapping("/notifyCovid")
    public String notifyCovid(Model m, @RequestParam String id) {
        //Of course this is not ideal nor secure: there is no trace of which admin performed this operation.
        //This is only meant for demonstration purposes
        service.notifyCovidContact(id);
        return "admin";
    }

    @RequestMapping(value = "/insertMessage")
    @ResponseBody
    public boolean insertMessage(Principal principal, @RequestParam String message) {
        String username = principal.getName();
        boolean success = boardClient.insertMessage(username, message);
        return success;
    }

    @RequestMapping(value = "/deleteMessage")
    @ResponseBody
    public boolean deleteMessage(@RequestParam long id) {
        boolean success = boardClient.deleteMessage(id);
        return success;
    }

    @RequestMapping("/checkRoomStatus")
    public String checkRoomStatus(Authentication auth, Model m, @RequestParam String id, @RequestParam Date date, @RequestParam int slot) {
        System.out.println("/checkRoomStatus called with params: "+id+" "+date+" "+slot);

        String username = auth.getName();
        int numReservations = service.getNumReservations(date,id,slot).intValue();
        int availableSeats = service.getAvailableSeats(id);
        int status = (service.checkProfessorReservation(slot,id,date))?1:0; //1 if there is lesson, 0 otherwise
        status = (availableSeats==0)?2:status; //if room is closed status = 2 otherwise do nothing

        m.addAttribute("status", status);
        m.addAttribute("username", username);
        m.addAttribute("selectedRoom", id);
        m.addAttribute("selectedDate", date);
        m.addAttribute("selectedSlots", service.findSlotById(slot));
        m.addAttribute("reservedSeats", numReservations);
        m.addAttribute("availableSeats", availableSeats);
        return "reservation";
    }

    @RequestMapping("/reservation")
    @ResponseBody
    public String reservation(Authentication auth, @RequestParam(name="selectedRoom") String id,
                              @RequestParam(name="selectedDate") Date date, @RequestParam(name="selectedSlot") int slot) {
        String username = auth.getName();//principal.getName();
        String role = auth.getAuthorities().toString().replaceAll("\\p{P}",""); //remove all brackets and unwanted chars

        System.out.println("[DBG]: /reservation of user "+username+", ROLE: "+role+" | "+id+" "+date+" "+slot);

        if(role.equals("PROF")) service.professorReservation(username,slot,id,date);
        else if(role.equals("STUDENT")) service.userReservation(username,slot,id,date);

        return "user";
    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("error");
        return model;
    }
}
