package kr.co.fastcampus.eatgo.application;

import org.springframework.web.bind.annotation.ResponseBody;

public class EmailNotExistedException extends RuntimeException {
    EmailNotExistedException(String email){
        super("Email is not registered:"+ email);
    }
}
