package com.example.rh.Controller;

import com.example.rh.Service.DemandeCongeService;
import com.example.rh.Service.EmployeService;
import com.example.rh.Service.TypeCongeService;
import com.example.rh.entity.DemandeConge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/demandes")
public class DemandeCongeController {

    @Autowired
    private DemandeCongeService demandeCongeService;

    @Autowired
    private EmployeService employeService;

    @Autowired
    private TypeCongeService typeCongeService;

    @GetMapping
    public String listeDemandes(
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String departement,
            @RequestParam(required = false) Long typeCongeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            Model model) {
        model.addAttribute("demandes", demandeCongeService.filtrer(statut, departement, typeCongeId, debut, fin));
        model.addAttribute("statut", statut);
        model.addAttribute("departement", departement);
        model.addAttribute("typeCongeId", typeCongeId);
        model.addAttribute("debut", debut);
        model.addAttribute("fin", fin);
        model.addAttribute("types", typeCongeService.getAll());
        return "demande/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("demande", new DemandeConge());
        model.addAttribute("employes", employeService.getAll());
        model.addAttribute("types", typeCongeService.getAll());
        return "demande/form";
    }

    @PostMapping("/save")
    public String saveDemande(@ModelAttribute DemandeConge demande,
                              @RequestParam Long employeId,
                              @RequestParam Long typeCongeId) {
        demande.setEmploye(employeService.getById(employeId));
        demande.setTypeConge(typeCongeService.getById(typeCongeId));
        demandeCongeService.save(demande);
        return "redirect:/demandes";
    }

    @GetMapping("/edit/{id}")
    public String editDemande(@PathVariable Long id, Model model) {
        model.addAttribute("demande", demandeCongeService.getById(id));
        model.addAttribute("employes", employeService.getAll());
        model.addAttribute("types", typeCongeService.getAll());
        return "demande/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteDemande(@PathVariable Long id) {
        demandeCongeService.delete(id);
        return "redirect:/demandes";
    }

    @GetMapping("/statut/{id}")
    public String mettreAJourStatut(@PathVariable Long id, @RequestParam String statut) {
        demandeCongeService.mettreAJourStatut(id, statut);
        return "redirect:/demandes";
    }
}
