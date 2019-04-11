package com.coderscampus.web;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.domain.Course;
import com.coderscampus.domain.Lesson;
import com.coderscampus.domain.Section;
import com.coderscampus.repository.CourseRepoitory;
import com.coderscampus.repository.SectionRepository;

@Controller
public class CourseController {

	
	private CourseRepoitory courseRepo;
	private SectionRepository sectionRepo;
	
	@GetMapping("/")
	public String rootPath()
	{
		return "redirect:/courses";
	}
	
	@GetMapping(value="courses")
	public String courses(ModelMap model)
	{
		Course course = new Course();
		Page<Course> courses = courseRepo.findAll(PageRequest.of(0,9));
		model.put("courses", courses);
		model.put("course", course);
		
		return "courses";
	}
	
	@PostMapping(value="courses")
	public String coursesPost(@ModelAttribute Course course, ModelMap model)
	{
	    courseRepo.save(course);
		return "redirect:/";
	}

	
	@GetMapping("editCourse/{courseId}")
	public String editCourseGet(@PathVariable Long courseId, ModelMap model)
	{
		Course course = courseRepo.findById(courseId).orElse(null);
		if(course == null) 
		{
			return "redirect:/";
		}
		model.put("course", course);
		model.put("sections", course.getSections());
		
		return "editCourse";
	}
	
	@PostMapping("editCourse/createSection")
	public @ResponseBody Course createSection(
			@RequestParam Long courseId, 
			@RequestParam String sectionName, 
			ModelMap model)
	{
		Course course = courseRepo.findById(courseId).orElse(null);
		Section section = new Section();
		section.setName(sectionName);
		section.setCourse(course);
		course.getSections().add(section);
		Course savedCourse = courseRepo.save(course);
		return savedCourse;
	}
	
	@PostMapping("editCourse/createLesson")
	public @ResponseBody Course createLesson(
			@RequestParam Long courseId, 
			@RequestParam Long sectionId, 
			String lessonTitle, 
			Integer lessonNumber, 
			ModelMap model)
	{
		Course course = courseRepo.findById(courseId).orElse(null);
		
		for (Section section : course.getSections())
		{
			if (section.getId().equals(sectionId)) 
			{
			Lesson lesson = new Lesson();
			lesson.setTitle(lessonTitle);
			lesson.setNumber(lessonNumber);
			lesson.setSection(section);
			section.getLessons().add(lesson);
			sectionRepo.save(section);
			break;
			}
		}
		return course;
	}
	
	
	@Autowired
	public void setCourseRepo(CourseRepoitory courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Autowired
	public void setSectionRepo(SectionRepository sectionRepo) {
		this.sectionRepo = sectionRepo;
	}
	
	
}
