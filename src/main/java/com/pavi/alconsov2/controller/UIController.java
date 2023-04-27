package com.pavi.alconsov2.controller;

import com.pavi.alconsov2.entity.ResultInfo;
import com.pavi.alconsov2.service.GenomeAnaliseService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class UIController {
  private final GenomeAnaliseService genomeAnaliseService;
  public UIController(GenomeAnaliseService genomeAnaliseService) {
    this.genomeAnaliseService = genomeAnaliseService;
  }

//  @MessageMapping("/start") TODO сделать дополнительный проброс инфы по результатам при запросе
//  @SendTo("/topic/status")
//  public Greeting answerToRequest(InputData message) {
//    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getFileName()) + "!");
//  }

  @GetMapping("/")
  public String mainPage(Model model){  //TODO вывод ошибок в клиент
    return "index";
  }

  @PostMapping("/results")
  public String startProcess(@RequestParam(name = "destination") String destination,
                             @RequestParam(name = "scatter") Integer scatter,
                             @RequestParam(name = "useN",required = false) Boolean useN,
                             Model model, RedirectAttributes redirectAttributes){
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

  //TODO организовать подтягивание данных при гет запросе на results либо переброс на индекс с ошибкой
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