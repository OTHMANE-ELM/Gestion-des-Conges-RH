package com.example.rh.Controller;

import com.example.rh.Service.TypeCongeService;
import com.example.rh.entity.TypeConge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/types-conge")
public class TypeCongeController {

    @Autowired
    private TypeCongeService typeCongeService;

    @GetMapping
    public String listeTypes(Model model) {
        model.addAttribute("types", typeCongeService.getAll());
        return "typeconge/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("typeConge", new TypeConge());
        return "typeconge/form";
    }

    @PostMapping("/save")
    public String saveType(@ModelAttribute TypeConge typeConge) {
        typeCongeService.save(typeConge);
        return "redirect:/types-conge";
    }

    @GetMapping("/edit/{id}")
    public String editType(@PathVariable Long id, Model model) {
        model.addAttribute("typeConge", typeCongeService.getById(id));
        return "typeconge/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable Long id) {
        typeCongeService.delete(id);
        return "redirect:/types-conge";
    }
}
