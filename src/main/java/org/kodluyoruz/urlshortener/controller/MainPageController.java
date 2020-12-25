package org.kodluyoruz.urlshortener.controller;

import org.kodluyoruz.urlshortener.repository.UserRepository;
import org.kodluyoruz.urlshortener.service.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController extends AbstractController
{

    private final UserRepository userRepository;

    @Autowired
    public MainPageController(LoginTokenService loginTokenService,UserRepository userRepository)
    {
        super(loginTokenService);
        this.userRepository = userRepository;
    }


    /*@RequestMapping(method = RequestMethod.GET,value = "/",
    produces = {MediaType.APPLICATION_JSON_VALUE})
    * Asagida kullanacagim annotasyonla ayni isi yapiyor
    * Asagidaki daha belirleyici
    * */
    @GetMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Object index()
    {
        getLoginToken();
        return userRepository.findAll();
    }
}
