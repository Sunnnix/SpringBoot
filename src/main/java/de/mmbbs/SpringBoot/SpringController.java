package de.mmbbs.SpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class SpringController {

    private final StudentRepository repo;

    @Autowired
    public SpringController(StudentRepository repo){
        this.repo = repo;
    }

    @GetMapping("/count")
    public long getStudentCount(){
        var c = repo.count();
        System.out.println("Student-Count: " + c);
        return c;
    }

    @GetMapping("/show-create")
    public String getCreateHTML() throws IOException {
        try(var stream = SpringController.class.getResourceAsStream("/templates/main.html")) {
            return new String(stream.readAllBytes());
        }
    }

    @PostMapping("/create")
    public Student createStudent(@RequestParam String name1, @RequestParam String name2, @RequestParam int age){
        var student = new Student(UUID.randomUUID().toString(), name1, name2, age);
        repo.save(student);
        return student;
    }

    @GetMapping("/with-firstname/{name}")
    public List<Student> getWithFirstName(@PathVariable String name){
        return repo.findByFirstName(name);
    }

    @DeleteMapping("/remove/{id}")
    public void removeStudent(@PathVariable String id){
        repo.deleteById(id);
    }

    @PatchMapping("/change/{id}")
    public void changeStudent(@PathVariable String id, @RequestParam(required = false) String name1, @RequestParam(required = false) String name2, @RequestParam(required = false) Integer age){
        var student = repo.findById(id).orElse(null);
        if(student == null)
            return;
        if(name1 != null)
            student.setFirstName(name1);
        if(name2 != null)
            student.setLastName(name2);
        if(age != null)
            student.setAge(age);
        repo.save(student);
    }

}
