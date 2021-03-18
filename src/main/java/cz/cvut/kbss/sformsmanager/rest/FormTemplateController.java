package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.service.model.local.FormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/formTemplate")
public class FormTemplateController {

    private final FormTemplateService formTemplateService;

    @Autowired
    public FormTemplateController(FormTemplateService formTemplateService) {
        this.formTemplateService = formTemplateService;
    }

}
