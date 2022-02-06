package be.jimmyd.cm.controllers;

import be.jimmyd.cm.domain.service.SearchService;
import be.jimmyd.cm.dto.SearchResultDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/{term}")
    public List<SearchResultDto> globalSearch(@PathVariable("term") String searchTerm, UsernamePasswordAuthenticationToken user) {
        return searchService.globalSearch(searchTerm, user.getPrincipal().toString());
    }

}
