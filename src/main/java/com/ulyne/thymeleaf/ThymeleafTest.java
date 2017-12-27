package com.ulyne.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Thymeleaf模板引擎 示例类
 * Created by fanwei_last on 2017/12/27.
 */
@RequestMapping("/thymeleaf")
@Controller
public class ThymeleafTest {

    /**
     * 欢迎界面
     * @param mv
     * @return
     */
    @RequestMapping(value = "/greeting")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("/greeting");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }
}
