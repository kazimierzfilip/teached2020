package com.teachedapp.controller;

import com.teachedapp.respository.SubjectRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/subjects", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SubjectController {

    static final String VIEW_SUBJECTS = "subject/subjects";

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public String getSubjects(Model model, String name) {
        if (name != null) {
            model.addAttribute("subjects", subjectRepository.findAllByNameContaining(name));

        } else {
            model.addAttribute("subjects", subjectRepository.findAll());

        }
        return VIEW_SUBJECTS;
    }

}
