package com.pavi.alconsov2.controller;

import com.pavi.alconsov2.service.GenomeAnaliseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UIController {
  private final GenomeAnaliseService genomeAnaliseService;
  public UIController(GenomeAnaliseService genomeAnaliseService) {
    this.genomeAnaliseService = genomeAnaliseService;
  }


  @GetMapping("/")
  public String mainPage(){
    return "index";
  }

  @PostMapping("/results")
  public String startProcess(@RequestParam(name = "destination") String destination,
                             @RequestParam(name = "scatter") Integer scatter,
                             @RequestParam(name = "useN",required = false) Boolean useN
          , RedirectAttributes redirectAttributes){
    if (useN==null)useN=false;
    System.out.println();
    if (genomeAnaliseService.isAnalysing()) return "redirect:/results";

    try {
      genomeAnaliseService.startAnaliseGenome(destination,scatter,useN);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error","Ошибка ввода данных");
      return "redirect:/";
    }
    return "redirect:/results";
  }


  @GetMapping("/results")
  public String showResults(Model model, RedirectAttributes redirectAttributes){
    if (!genomeAnaliseService.isAnalysing()){
      redirectAttributes.addFlashAttribute("error","Геном не анализируется");
      return "redirect:/";
    }
    System.out.println(genomeAnaliseService.getResultInfo());
    model.addAttribute("info",genomeAnaliseService.getResultInfo());
    return "results";
  }


}