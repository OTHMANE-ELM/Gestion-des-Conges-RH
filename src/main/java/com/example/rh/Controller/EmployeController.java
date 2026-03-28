package com.example.rh.Controller;

import com.example.rh.Service.EmployeService;
import com.example.rh.entity.Employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping
    public String listeEmployes(@RequestParam(required = false) String nom, Model model) {
        model.addAttribute("employes", employeService.rechercher(nom));
        model.addAttribute("nom", nom);
        return "employe/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("employe", new Employe());
        return "employe/form";
    }

    @PostMapping("/save")
    public String saveEmploye(@ModelAttribute Employe employe) {
        employeService.save(employe);
        return "redirect:/employes";
    }

    @GetMapping("/edit/{id}")
    public String editEmploye(@PathVariable Long id, Model model) {
        model.addAttribute("employe", employeService.getById(id));
        return "employe/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmploye(@PathVariable Long id) {
        employeService.delete(id);
        return "redirect:/employes";
    }
}
