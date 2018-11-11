package org.kjsce.khabar.controller;

import org.kjsce.khabar.service.TextClassifierServiceMultilayerPerceptron;
import org.kjsce.khabar.service.TextClassifierServiceNaiveBayes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.xml.ws.Response;

/*@RestController
@RequestMapping("")*/
public class DemoController {
    @Autowired
    TextClassifierServiceNaiveBayes naiveBayesService;
    @Autowired
    TextClassifierServiceMultilayerPerceptron multilayerPerceptronService;
    @GetMapping("/init/{modal}")
    public ResponseEntity<?> index(@PathVariable(name = "modal")String modalName) throws Exception{
        if(modalName.equals("nb")){
            naiveBayesService.init();
        }
        else if(modalName.equals("mlp")){
            multilayerPerceptronService.init();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("requested classifier is not suported");
        }
        return ResponseEntity.ok("initialization successful");
    }
    @GetMapping("/modalSummary/{modal}")
    public ResponseEntity<?> getModalSummary(@PathVariable(name = "modal") String modalName) throws Exception{
        String summary  = "";
        if (modalName.equals("nb")){
            summary= naiveBayesService.modalSummary();
        }
        else if(modalName.equals("mlp")){
            summary = multilayerPerceptronService.modalSummary();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("requested classifier is not suported");
        }
        return ResponseEntity.ok(summary);
    }
    @GetMapping("/classify/{modal}")
    public ResponseEntity classify(@PathVariable("modal") String modalName,
                                   @RequestParam String text) throws Exception{
        if(modalName.equals("nb")){
            return ResponseEntity.ok(naiveBayesService.classify(text));
        }
        else if(modalName.equals("mlp")){
            return ResponseEntity.ok(multilayerPerceptronService.classify(text));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("requested classifier is not suported");
    }
}
