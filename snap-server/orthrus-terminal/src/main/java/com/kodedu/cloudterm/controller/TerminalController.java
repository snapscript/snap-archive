package com.kodedu.cloudterm.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import lombok.SneakyThrows;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kodedu.cloudterm.helper.ClassPathReader;

@Controller
public class TerminalController {

   @SneakyThrows
   @ResponseBody
   @RequestMapping(path = "/{host}/status", produces = "text/html")
   public String getStatus(@PathVariable("host") String host, HttpServletRequest request) {
      return "Everything is ok";
   }
   
   @SneakyThrows
   @ResponseBody
   @RequestMapping(path = "/{host}/terminal", produces = "text/html")
   public String getTermimal(@PathVariable("host") String host, HttpServletRequest request) {
      InputStream stream = ClassPathReader.getInputStream("/terminal/html/terminal.html");
      return IOUtils.toString(stream, "UTF-8");
   }

   @SneakyThrows
   @ResponseBody
   @RequestMapping(path = "/{host}/js/{script}.js", produces = "application/javascript")
   public String getScript(@PathVariable("host") String host, @PathVariable("script") String script, HttpServletRequest request) {
      InputStream stream = ClassPathReader.getInputStream("/terminal/js/" + script + ".js");
      return IOUtils.toString(stream, "UTF-8");
   }

   @SneakyThrows
   @ResponseBody
   @RequestMapping(path = "/{host}/css/{style}.css", produces = "text/css")
   public String getStyle(@PathVariable("host") String host, @PathVariable("style") String style, HttpServletRequest request) {
      InputStream stream = ClassPathReader.getInputStream("/terminal/css/" + style + ".css");
      return IOUtils.toString(stream, "UTF-8");
   }
}
