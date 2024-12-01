package com.dustopia.karaoke.controller;

import com.dustopia.karaoke.model.Attendant;
import com.dustopia.karaoke.model.AttendantStat;
import com.dustopia.karaoke.model.BookedRoom;
import com.dustopia.karaoke.model.Client;
import com.dustopia.karaoke.model.Manager;
import com.dustopia.karaoke.model.Room;
import com.dustopia.karaoke.model.ServingShift;
import com.dustopia.karaoke.service.AttendantService;
import com.dustopia.karaoke.service.AttendantStatService;
import com.dustopia.karaoke.service.BookingService;
import com.dustopia.karaoke.service.ServingShiftService;
import com.dustopia.karaoke.service.impl.RoomServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final RoomServiceImpl roomService;

    private final BookingService bookingService;

    private final AttendantService attendantService;

    private final AttendantStatService attendantStatService;

    private final ServingShiftService servingShiftService;

    @GetMapping("/manager-home")
    public ModelAndView managerHome(HttpSession session) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("manager_home");
        view.addObject("manager", manager);
        return view;
    }

    // Module 1
    @GetMapping("/manage-room")
    public ModelAndView manageRoom(
            @RequestParam(required = false) boolean back,
            @RequestParam(required = false) boolean add,
            @RequestParam(required = false) boolean error,
            HttpSession session
    ) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("manage_room");
        view.addObject("manager", manager);
        List<Room> rooms;
        if (back) {
            rooms = (List<Room>) session.getAttribute("rooms");
        } else if (add) {
            Room room = (Room) session.getAttribute("newRoom");
            rooms = (List<Room>) session.getAttribute("rooms");
            rooms.add(room);
        } else if (error) {
            view.addObject("error", true);
            rooms = (List<Room>) session.getAttribute("rooms");
        } else {
            rooms = roomService.getAllRooms();
        }
        session.setAttribute("rooms", rooms);
        view.addObject("rooms", rooms);
        return view;
    }

    @GetMapping("/add-room")
    public ModelAndView addRoom(HttpSession session) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("add_room");
        view.addObject("manager", manager);
        return view;
    }

    @PostMapping("/add-room")
    public ModelAndView doAddRoom(
            @ModelAttribute Room room,
            HttpSession session
    ) {
        try {
            ModelAndView view = new ModelAndView("redirect:/manage-room?add=true");
            roomService.addRoom(room);
            session.setAttribute("newRoom", room);
            return view;
        } catch (Exception e) {
            return new ModelAndView("redirect:/manage-room?error=true");
        }
    }

    // Module 3
    @GetMapping("/view-stat-attendant")
    public ModelAndView viewStatAttendant(
            @RequestParam(required = false) boolean back,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpSession session
    ) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("view_stat_attendant");
        view.addObject("manager", manager);
        List<AttendantStat> attendantStats;
        if (back) {
            attendantStats = (List<AttendantStat>) session.getAttribute("attendantStats");
        } else {
            attendantStats = attendantStatService.getAllAttendantStats(startDate, endDate);
            session.setAttribute("attendantStats", attendantStats);
        }
        view.addObject("attendantStats", attendantStats);
        return view;
    }

    @GetMapping("/attendant-detail/{attendantId}")
    public ModelAndView attendantDetail(
            @PathVariable int attendantId,
            @RequestParam(required = false) boolean back,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpSession session
    ) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("attendant_detail");
        view.addObject("manager", manager);
        Attendant attendant;
        List<ServingShift> servingShifts;
        if (back) {
            attendant = (Attendant) session.getAttribute("attendant");
            servingShifts = (List<ServingShift>) session.getAttribute("servingShifts");
        } else {
            List<AttendantStat> attendantStats = (List<AttendantStat>) session.getAttribute("attendantStats");
            attendant = attendantStats.stream()
                    .filter(attendantStat -> Objects.equals(attendantStat.getId(), attendantId))
                    .map(attendantStat ->
                            Attendant.builder()
                                    .id(attendantStat.getId())
                                    .username(attendantStat.getUsername())
                                    .password(attendantStat.getPassword())
                                    .name(attendantStat.getName())
                                    .salary(attendantStat.getSalary())
                                    .available(attendantStat.isAvailable())
                                    .build()
                    )
                    .findFirst()
                    .orElse(null);
            servingShifts = servingShiftService.getAllServingShiftsOfAttendant(attendant, startDate, endDate);
            session.setAttribute("attendant", attendant);
            session.setAttribute("servingShifts", servingShifts);
        }
        view.addObject("attendant", attendant);
        view.addObject("servingShifts", servingShifts);
        return view;
    }

    @GetMapping("/serving-shift-detail/{servingShiftId}")
    public ModelAndView servingShiftDetail(
            @PathVariable int servingShiftId,
            HttpSession session
    ) {
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView view = new ModelAndView("serving_shift_detail");
        view.addObject("manager", manager);
        Attendant attendant = (Attendant) session.getAttribute("attendant");
        List<ServingShift> servingShifts = (List<ServingShift>) session.getAttribute("servingShifts");
        ServingShift servingShift = servingShifts.stream()
                .filter(item -> Objects.equals(item.getId(), servingShiftId))
                .findFirst()
                .orElse(null);
        BookedRoom bookedRoom = Objects.nonNull(servingShift) ? servingShift.getBookedRoom() : null;
        Client client = Objects.nonNull(bookedRoom) ? bookedRoom.getBooking().getClient() : null;
        Manager assignedManager =  Objects.nonNull(bookedRoom) ? bookedRoom.getBooking().getManager() : null;
        view.addObject("bookedRoom", bookedRoom);
        view.addObject("client", client);
        view.addObject("attendant", attendant);
        view.addObject("assignedManager", assignedManager);
        return view;
    }

}
