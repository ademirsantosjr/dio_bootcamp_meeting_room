package one.dio.meetingroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.dio.meetingroom.exception.ResourceNotFoundException;
import one.dio.meetingroom.model.Room;
import one.dio.meetingroom.repository.RoomRepository;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/rooms")
public class RoomController {
    
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Room> findAll() {
        return roomRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable long id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));

        return ResponseEntity.ok().body(room);
    }

    @PostMapping
    public Room create(@RequestBody @Valid Room room) {
        return roomRepository.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable long id, @RequestBody @Valid Room room) throws ResourceNotFoundException {
        Room roomToUpdate = roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID=" + room.getDate()));
        
        roomToUpdate.setName(room.getName());
        roomToUpdate.setDate(room.getDate());
        roomToUpdate.setStartHour(room.getStartHour());
        roomToUpdate.setEndHour(room.getEndHour());

        final Room updatedRoom = roomRepository.save(roomToUpdate);

        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable long id) throws ResourceNotFoundException {
       Room roomToDelete = roomRepository.findById(id)
                                         .orElseThrow(
                                             () -> new ResourceNotFoundException("Room not found with ID=" + id));
        
       roomRepository.delete(roomToDelete);
       
       Map<String, Boolean> response = new HashMap<>();
       response.put("deleted", Boolean.TRUE);

       return response;
    }
}
