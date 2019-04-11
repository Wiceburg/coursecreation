package com.coderscampus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.domain.Course;

@Repository
public interface CourseRepoitory extends JpaRepository<Course, Long>{

}
