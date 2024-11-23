package com.dustopia.karaoke.controller;

import com.dustopia.karaoke.common.aop.Auth;
import com.dustopia.karaoke.model.BookedRoom;
import com.dustopia.karaoke.model.Booking;
import com.dustopia.karaoke.model.Manager;
import com.dustopia.karaoke.model.Room;
import com.dustopia.karaoke.model.response.AssignRoom;
import com.dustopia.karaoke.service.BookingService;
import com.dustopia.karaoke.service.impl.RoomServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final RoomServiceImpl roomService;
    private final BookingService bookingService;

    @GetMapping("/manager-home")
    @Auth
    public ModelAndView managerHome(HttpSession session) {
        Manager manager = (Manager) session.getAttribute("manager");
        ModelAndView view = new ModelAndView("manager_home");
        view.addObject("manager", manager);
        return view;
    }

    @GetMapping("/manage-room")
    @Auth
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
    @Auth
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

    @GetMapping("/assign-attendant")
    public ModelAndView assignAttendant(
            @RequestParam(required = false) boolean back,
            HttpSession session
    ) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("assign_attendant");
        List<AssignRoom> assignRooms;
        if (back) {
            assignRooms = (List<AssignRoom>) session.getAttribute("assignRoom");
            view.addObject("assignRooms", assignRooms);
        } else {
            List<Booking> bookings = bookingService.getAllUncheckoutBookings();
            List<BookedRoom> bookedRooms = bookings.stream()
                    .sorted(Comparator.comparing(Booking::getBookingTime))
                    .flatMap(booking -> booking.getBookedRooms().stream())
                    .filter(bookedRoom -> bookedRoom.getReturnTime() == null)
                    .toList();
            assignRooms = bookedRooms.stream()
                    .map(bookedRoom ->
                            AssignRoom.builder()
                                    .bookedRoomId(bookedRoom.getId())
                                    .roomName(bookedRoom.getRoom().getName())
                                    .bookingTime(bookedRoom.getBooking().getBookingTime())
                                    .currentAttendants(bookedRoom.getCurrentAttendant())
                                    .build())
                    .toList();
            session.setAttribute("assignRooms", assignRooms);
            view.addObject("assignRooms", assignRooms);
        }
        return view;
    }

}
