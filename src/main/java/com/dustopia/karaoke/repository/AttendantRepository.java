package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Attendant;
import com.dustopia.karaoke.repository.projection.GetAllAttendantStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendantRepository extends JpaRepository<Attendant, Integer> {

    String queryAllAttendantStats = "SELECT e.id,\n" +
            "       e.username,\n" +
            "       e.password,\n" +
            "       e.name,\n" +
            "       e.salary,\n" +
            "       a.available,\n" +
            "       COALESCE(SUM(TIMESTAMPDIFF(MINUTE, ss.start_time, br.return_time)), 0) AS totalServingTime,\n" +
            "       GROUP_CONCAT(DISTINCT ss.note SEPARATOR ', ') AS note\n" +
            "FROM tbl_attendant a\n" +
            "         JOIN tbl_employee e ON a.id = e.id\n" +
            "         LEFT JOIN tbl_serving_shift ss ON a.id = ss.attendant_id\n" +
            "         LEFT JOIN tbl_booked_room br ON ss.booked_room_id = br.id\n" +
            "WHERE (:startTime IS NULL OR ss.start_time >= :startTime)\n" +
            "         AND (:endTime IS NULL OR br.return_time <= :endTime)\n" +
            "GROUP BY a.id, e.name\n" +
            "ORDER BY totalServingTime DESC;";

    List<Attendant> findAllByAvailable(boolean available);

    @Query(value = queryAllAttendantStats, nativeQuery = true)
    List<GetAllAttendantStats> findAllAttendantStats(LocalDateTime startTime, LocalDateTime endTime);

}
