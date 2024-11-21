package com.dustopia.karaoke.controller;

import com.dustopia.karaoke.model.Manager;
import com.dustopia.karaoke.model.Room;
import com.dustopia.karaoke.service.impl.RoomServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final RoomServiceImpl roomService;

    @GetMapping("/manager-home")
    public ModelAndView managerHome(HttpSession session) {
        ModelAndView view = new ModelAndView("manager_home");
        Manager manager = (Manager) session.getAttribute("manager");
        view.addObject("manager", manager);
        return view;
    }

    @GetMapping("/manage-room")
    public ModelAndView manageRoom(
            @RequestParam(required = false) boolean back,
            @RequestParam(required = false) boolean add,
            HttpSession session
    ) {
        ModelAndView view = new ModelAndView("manage_room");
        List<Room> rooms;
        if (back) {
            rooms = (List<Room>) session.getAttribute("rooms");
        } else if (add) {
            Room room = (Room) session.getAttribute("newRoom");
            rooms = (List<Room>) session.getAttribute("rooms");
            rooms.add(room);
        } else {
            rooms = roomService.getAllRooms();
        }
        session.setAttribute("rooms", rooms);
        view.addObject("rooms", rooms);
        return view;
    }

    @GetMapping("/add-room")
    public ModelAndView addRoom() {
        return new ModelAndView("add_room");
    }

    @PostMapping("/add-room")
    public ModelAndView doAddRoom(
            @ModelAttribute Room room,
            HttpSession session
    ) {
        ModelAndView view = new ModelAndView("redirect:/manage-room?add=true");
        roomService.addRoom(room);
        session.setAttribute("newRoom", room);
        return view;
    }

}
