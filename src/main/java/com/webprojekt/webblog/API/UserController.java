package com.webprojekt.webblog.API;

import com.webprojekt.webblog.BussinesLayer.AuthenticationService;
import com.webprojekt.webblog.BussinesLayer.WebBlogServices;
import com.webprojekt.webblog.Security.AuthenticationRequest;
import com.webprojekt.webblog.Security.AuthenticationResponse;
import com.webprojekt.webblog.Security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/WebBlog/User")
@Controller
public class UserController {


}
