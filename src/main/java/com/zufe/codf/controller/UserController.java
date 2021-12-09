package com.zufe.codf.controller;
import org.jasig.cas.client.authentication.AttributePrincipal;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zufe.codf.model.User;
import com.zufe.codf.model.UserDto;
import com.zufe.codf.service.util.SessionContextUtils;
import com.zufe.codf.service.util.VertifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.zufe.codf.service.UserService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应涛
 * @date 2021/8/27
 * @function：用户控制
 */
@RestController
@CrossOrigin
@RequestMapping(value="/codf")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/insertside")
    public Map insert(@RequestBody User user)  {
        Map map = new HashMap();
        if(user.getUserName()==null||user.getUserPass()==null){
            map.put("code",0);
            map.put("msg","用户名或密码不能为空");
            return map;
        }
        userService.insert(user);
        map.put("code",1);
        map.put("msg","注册成功");
        return map;
    }

    @RequestMapping("/login")
    public Map loginUser(HttpServletRequest request,UserDto user) {
//        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
//        System.out.println("principal = " + principal.getAttributes());


        Map<String, Object> data = new HashMap<>();
        //获取请求头中的sessionId，然后根据sessionId来获取session
        String sessionId = request.getHeader("sessionId");
        System.out.println(sessionId);
        SessionContextUtils sessionContext= SessionContextUtils.getInstance();
        HttpSession session = sessionContext.getSession(sessionId);
        // 验证码
        Object verCode = session.getAttribute("verCode");
        if (null == verCode) {
            data.put("code", 0);
            data.put("msg", "验证码已失效，请重新输入");
            return data;
        }
        String verCodeStr = verCode.toString();
        LocalDateTime localDateTime = (LocalDateTime) session.getAttribute("codeTime");
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date codeDateTime = Date.from(zdt.toInstant());

        if (verCodeStr == null || StringUtils.isEmpty(user.getCode()) || !verCodeStr.equalsIgnoreCase(user.getCode())) {
            data.put("code", 0);
            data.put("msg", "验证码错误");
            return data;
        } else if (((System.currentTimeMillis()) - (codeDateTime.getTime())) > (1 * 60 * 1000)) {
            data.put("code", 0);
            data.put("msg", "验证码已过期,请重新输入");
            return data;
        }
        else if (userService.login(user)<=0) {
            data.put("code", 0);
            data.put("msg", "用户名或密码错误");
            return data;
        } else {
            //验证成功，删除存储的验证码
            session.removeAttribute("verCode");
            session.removeAttribute("codeTime");
        }


//        Map<String, Object> map = new HashMap<>();
//        map.put("code",1);
//        map.put("msg","登陆成功");
//        map.put("user",principal.toString());
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/code")
    public Map<String, Object> getCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            String verifyCode = VertifyCodeUtils.generateVerifyCode(4);
            // 存入会话session
            HttpSession session = request.getSession(true);
            // 删除以前的
            session.removeAttribute("verCode");
            session.removeAttribute("codeTime");
            session.setAttribute("verCode", verifyCode.toLowerCase());
            session.setAttribute("codeTime", LocalDateTime.now());
            // 生成图片
            int w = 200, h = 50;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //注意，这里是将生成的图片转为base64的格式将返回给前端，而不是直接给张图片的地址。
            //这样获取验证码就是一个请求，而不是一张图片的地址，因为是一个请求，所以可以返回sessionid给前端。
            VertifyCodeUtils.outputImage(w, h, stream, verifyCode);
            try{
                String encode = Base64.encode(stream.toByteArray());
                Map<String,Object> map = new HashMap<>();
                map.put("img","data:image/png;base64,"+ encode);
                //返回sessionid给前端，前端只需要在调用登录接口的时候将这个sessionid设置到请求头中即可
                map.put("sessionId",session.getId());
                return map;
            }catch (Exception e){

            }finally{
                stream.close();
            }
        } catch (Exception e) {
            System.out.println("验证码获取错误");
        }
        Map<String,Object> fmap = new HashMap<>();
        fmap.put("result","验证码获取错误");
        return fmap;
    }
}
