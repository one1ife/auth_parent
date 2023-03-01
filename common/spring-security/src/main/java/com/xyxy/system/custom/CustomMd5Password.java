package com.xyxy.system.custom;

import com.xyxy.common.utils.MD5;
import org.apache.tomcat.jni.PasswordCallback;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//自定义密码组件
@Component
public class CustomMd5Password implements PasswordEncoder {
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
