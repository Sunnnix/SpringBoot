package de.mmbbs.SpringBoot;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    @Query("{ 'firstName' : ?0 }")
    List<Student> findByFirstName(String firstName);

}
