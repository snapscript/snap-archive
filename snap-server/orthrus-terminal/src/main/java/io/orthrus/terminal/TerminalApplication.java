package io.orthrus.terminal;

import io.orthrus.jmx.EnableManagement;
import io.orthrus.rest.EnableResourceServer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableManagement
@EnableResourceServer
@SpringBootApplication
public class TerminalApplication {

	public static void main(String[] list) {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(TerminalApplication.class);
      builder.web(WebApplicationType.NONE).run(list);
	}
}
