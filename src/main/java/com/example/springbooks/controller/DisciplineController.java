package com.example.springbooks.controller;

import com.example.springbooks.forms.DisciplineForm;
import com.example.springbooks.logger.Log;
import com.example.springbooks.model.Discipline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class DisciplineController {
    private static List<Discipline> disciplines = new ArrayList<Discipline>();

    static {
        disciplines.add(new Discipline("Software program", "Patsei N.V.", "150"));
        disciplines.add(new Discipline("Programming and security", "Shiman D.V.", "169"));
    }
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        Log.getLogger().info("Welcome page is opened!");
        return modelAndView;
    }
    @GetMapping(value = {"/alldisciplines"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("disciplinelist");
        model.addAttribute("disciplines", disciplines);
        Log.getLogger().info("AllDisciplines page is opened!");
        return modelAndView;
    }

    @GetMapping(value = {"/newdiscipline"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("newdiscipline");
        DisciplineForm disciplineForm = new DisciplineForm();
        model.addAttribute("disciplineform", disciplineForm);
        return modelAndView;
    }

    @PostMapping(value = {"/newdiscipline"})
    public ModelAndView savePerson(Model model,  @ModelAttribute("disciplineform") DisciplineForm disciplineForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("disciplinelist");
        String name = disciplineForm.getName();
        String teacher = disciplineForm.getTeacher();
        String hours = disciplineForm.getHours();
        if (name != null && name.length() > 0  && teacher != null && teacher.length() > 0 && hours != null && hours.length() > 0) {
            Discipline newdiscipline = new Discipline(name,teacher,hours);
            disciplines.add(newdiscipline);
            model.addAttribute("disciplines", disciplines);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("newdiscipline");
        return modelAndView;
    }

}
