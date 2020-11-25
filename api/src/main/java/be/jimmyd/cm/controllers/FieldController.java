package be.jimmyd.cm.controllers;

import be.jimmyd.cm.dto.FieldDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/field")
public class FieldController {

    @GetMapping("/collection/{id}")
    public List<FieldDto> getByCollection(@PathVariable("id") long id) {
        //TODO return list

        return new ArrayList<>();
    }

    @GetMapping("/basic/collection/{id}")
    public List<FieldDto> getBasicByCollection(@PathVariable("id") long id) {
        //TODO return list

        return new ArrayList<>();
    }

    @GetMapping("/custom/collection/{id}")
    public List<FieldDto> getCustomByCollection(@PathVariable("id") long id) {
        //TODO return list

        return new ArrayList<>();
    }
}
