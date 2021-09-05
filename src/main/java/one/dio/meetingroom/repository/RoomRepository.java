package one.dio.meetingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.meetingroom.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
}
