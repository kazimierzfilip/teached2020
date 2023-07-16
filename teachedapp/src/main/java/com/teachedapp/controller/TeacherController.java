package com.teachedapp.controller;

import com.teachedapp.dao.*;
import com.teachedapp.respository.AccountRepository;
import com.teachedapp.respository.SubjectRepository;
import com.teachedapp.respository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/teachers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TeacherController {

    static final String VIEW_TEACHERS = "teacher/teachers";
    static final String VIEW_NEW_TEACHER_FORM = "teacher/new-teacher-form";


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    EntityManagerFactory emf;

    @GetMapping("/create")
    public String createTeacherForm(Model model) {

        model.addAttribute("teacher", new Teacher());

        return VIEW_NEW_TEACHER_FORM;
    }

    @PostMapping("/create")
    public String createTeacherAccount(@Valid Teacher teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "teacher/new-teacher-form";
        }

        Account teacherAccount = teacher.getAccount();

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(teacherAccount);
        entityManager.getTransaction().commit();

        teacher.setAccount(teacherAccount);

        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();

        entityManager.close();

        return "redirect:/";

    }

    @PostMapping
    public @ResponseBody Teacher addTeacher(@RequestBody Teacher teacher) {
        Account teacherAccount = teacher.getAccount();
        accountRepository.save(teacherAccount);
        teacher.setAccount(teacherAccount);

        List<Subject> subjectList = new ArrayList<>();
        for(Subject subject : teacher.getSubjects()){
            Optional<Subject> foundSubject = subjectRepository.findById(subject.getId());
            foundSubject.ifPresent(subjectList::add);
        }
        teacher.setSubjects(subjectList);
        teacherRepository.save(teacher);

        return teacher;
    }

    @GetMapping(produces = "application/json")
    public String getTeacherBySubject(Model model, @RequestParam(value="subjectId") Integer subjectId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
        Root<Teacher> teacherRoot = cq.from(Teacher.class);
        Join<Teacher, Subject> teacherSubject = teacherRoot.join("subjects", JoinType.LEFT);
        cq.select(teacherRoot).where(teacherSubject.get("id").in(subjectId)).distinct(true);
        TypedQuery<Teacher> query = em.createQuery(cq);
        model.addAttribute("teachers", query.getResultList());
        return VIEW_TEACHERS;
    }

    @GetMapping(value = "/{id}/salary", produces = "application/json")
    public @ResponseBody Double getSalary(
            @PathVariable("id") Integer id,
            @RequestParam(value="calculateDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date calculateDate) {

        Optional<Teacher> teacher = teacherRepository.findById(id);
        double salary = 0.0;

        if(teacher.isPresent()){
            List<Course> courses = teacher.get().getCourses();

            if(calculateDate != null){

                Calendar chosenDate = Calendar.getInstance();
                chosenDate.setTime(calculateDate);
                Calendar lessonDate = Calendar.getInstance();

                for(Course course : courses) {
                    Double coursePayRatePerHour = course.getTeacherPayRatePerHour().doubleValue();
                    for (Lesson lesson : course.getLessons()) {
                        if(lesson.getStatus() == LessonStatus.ENDED){

                            lessonDate.setTime(lesson.getDate());

                            if (chosenDate.get(Calendar.YEAR) == lessonDate.get(Calendar.YEAR)
                                    && chosenDate.get(Calendar.MONTH) == lessonDate.get(Calendar.MONTH)) {
                                    salary += (coursePayRatePerHour * lesson.getDuration());
                            }
                        }
                    }
                }
            } else {
                for(Course course : courses) {
                    Double coursePayRatePerHour = course.getTeacherPayRatePerHour().doubleValue();

                    for (Lesson lesson : course.getLessons()) {
                        if(lesson.getStatus() == LessonStatus.ENDED
                                && (lesson.getTeacherWasPaid() == null || !lesson.getTeacherWasPaid())) {
                            salary += (coursePayRatePerHour * lesson.getDuration());
                        }
                    }
                }
            }

        }
        return salary;
    }

}
