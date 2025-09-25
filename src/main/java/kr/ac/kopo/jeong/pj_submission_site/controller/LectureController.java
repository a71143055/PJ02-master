package kr.ac.kopo.jeong.pj_submission_site.controller;

import kr.ac.kopo.jeong.pj_submission_site.model.Lecture;
import kr.ac.kopo.jeong.pj_submission_site.model.User;
import kr.ac.kopo.jeong.pj_submission_site.repository.UserRepository;
import kr.ac.kopo.jeong.pj_submission_site.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("lecture", new Lecture());
        return "createLecture"; // templates/createLecture.html
    }

    @PostMapping("/create")
    public String createLecture(@ModelAttribute Lecture lecture, Principal principal) {
        User professor = userRepository.findByUsername(principal.getName()).orElseThrow();
        lecture.setProfessor(professor);
        lectureService.createLecture(lecture.getTitle(), lecture.getDescription(), professor);
        return "redirect:/home";
    }
}

