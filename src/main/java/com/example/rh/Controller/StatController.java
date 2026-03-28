package com.example.rh.Controller;

import com.example.rh.Service.DemandeCongeService;
import com.example.rh.Service.EmployeService;
import com.example.rh.Service.TypeCongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stats")
public class StatController {

    @Autowired
    private DemandeCongeService demandeCongeService;

    @Autowired
    private EmployeService employeService;

    @Autowired
    private TypeCongeService typeCongeService;

    @GetMapping
    public String statistiques(Model model) {
        long totalDemandes = demandeCongeService.countTotal();
        long enAttente = demandeCongeService.countByStatut("en attente");
        long acceptees = demandeCongeService.countByStatut("accepté");
        long refusees = demandeCongeService.countByStatut("refusé");
        double tauxAcceptation = demandeCongeService.tauxAcceptation();

        model.addAttribute("totalDemandes", totalDemandes);
        model.addAttribute("enAttente", enAttente);
        model.addAttribute("acceptees", acceptees);
        model.addAttribute("refusees", refusees);
        model.addAttribute("tauxAcceptation", String.format("%.1f", tauxAcceptation));
        model.addAttribute("totalEmployes", employeService.getAll().size());
        model.addAttribute("totalTypes", typeCongeService.getAll().size());
        model.addAttribute("joursParDepartement", demandeCongeService.joursConsommesParDepartement());

        return "stats/dashboard";
    }
}
