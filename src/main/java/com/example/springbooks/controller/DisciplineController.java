package com.example.springbooks.controller;

import com.example.springbooks.forms.DisciplineForm;
import com.example.springbooks.logger.Log;
import com.example.springbooks.model.Discipline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return modelAndView;
    }
    @GetMapping(value = {"/alldisciplines"})
    public ModelAndView DisciplineList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("disciplinelist");
        model.addAttribute("disciplines", disciplines);
        Log.getLogger().info("All Disciplines is displayed!");
        return modelAndView;
    }

    @GetMapping(value = {"/newdiscipline"})
    public ModelAndView showAddDisciplinePage(Model model) {
        ModelAndView modelAndView = new ModelAndView("newdiscipline");
        DisciplineForm disciplineForm = new DisciplineForm();
        model.addAttribute("disciplineform", disciplineForm);
        Log.getLogger().info("New Discipline page is opened!");
        return modelAndView;
    }

    @PostMapping(value = {"/newdiscipline"})
    public ModelAndView saveDiscipline(Model model,  @ModelAttribute("disciplineform") DisciplineForm disciplineForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("disciplinelist");
        String name = disciplineForm.getName();
        String teacher = disciplineForm.getTeacher();
        String hours = disciplineForm.getHours();
        if (name != null && name.length() > 0  && teacher != null && teacher.length() > 0 && hours != null && hours.length() > 0) {
            Discipline newdiscipline = new Discipline(name,teacher,hours);
            disciplines.add(newdiscipline);
            Log.getLogger().info("New Discipline is successfully added!");
            model.addAttribute("disciplines", disciplines);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("newdiscipline");
        return modelAndView;
    }

    @GetMapping(value = {"/deletediscipline/{Name}"})
    public String DeleteDiscipline(@PathVariable(value = "Name") String Name){
        for (int i = 0; i < disciplines.size(); i++) {
            if(disciplines.get(i).getName().equals(Name)){
                disciplines.remove(i);
            }
        }
        Log.getLogger().info("Discipline is successfully deleted!");
        return "redirect:/alldisciplines";
    }

    @GetMapping(value = {"/editdiscipline/{Name}"})
    public ModelAndView showEditPage(Model model, @PathVariable(value = "Name")String Name){
        ModelAndView modelAndView = new ModelAndView("editdiscipline");
        DisciplineForm disciplineForm = new DisciplineForm();
        for (int i = 0; i < disciplines.size(); i++) {
            if (disciplines.get(i).getName().equals(Name)){
                disciplineForm.setName(disciplines.get(i).getName());
                disciplineForm.setTeacher(disciplines.get(i).getTeacher());
                disciplineForm.setHours(disciplines.get(i).getHours());
                break;
            }
        }
        model.addAttribute("disciplines", disciplineForm);
        Log.getLogger().info("Edit Discipline page is opened!");
        return modelAndView;
    }
    @PostMapping(value = {"/updatediscipline"})
    public ModelAndView updatePerson(Model model, @ModelAttribute("employeeForm") DisciplineForm disciplineForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("disciplinelist");
        for (int i = 0; i < disciplines.size(); i++) {
            if(disciplines.get(i).equal(disciplineForm)){
                if(disciplineForm.getName().length() > 0 && disciplineForm.getName() != null)
                    disciplines.get(i).setName(disciplineForm.getName());
                if(disciplineForm.getTeacher().length() > 0 && disciplineForm.getTeacher() != null)
                    disciplines.get(i).setTeacher(disciplineForm.getTeacher());
                if(disciplineForm.getHours().length() > 0 && disciplineForm.getHours() != null)
                    disciplines.get(i).setHours(disciplineForm.getHours());
                Log.getLogger().info("Discipline is successfully changed!");
                model.addAttribute("disciplines", disciplines);
                return modelAndView;
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("updatediscipline");
        return modelAndView;
    }
    }
