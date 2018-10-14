package io.orthrus.terminal.service;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import org.simpleframework.http.socket.Frame;
import org.simpleframework.http.socket.FrameChannel;
import org.simpleframework.http.socket.FrameListener;
import org.simpleframework.http.socket.FrameType;
import org.simpleframework.http.socket.Reason;
import org.simpleframework.http.socket.Session;
import org.simpleframework.http.socket.service.Service;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class TerminalService implements Service {

   private final Executor executor;
   
   public TerminalService() {
      this.executor = new ScheduledThreadPoolExecutor(3);
   }
   
   @Override
   public void connect(Session session) {
      FrameChannel channel = session.getChannel();
      
      try {
         SessionController client = new SessionController(channel, executor);
         channel.register(client);
      } catch(Exception e) {
         log.warn("Error creating session", e);
      }
   }

   private static class SessionController implements FrameListener {

      private final TypeReference<Map<String, String>> reference;
      private final TerminalProcess process;
      private final ObjectMapper mapper;

      public SessionController(FrameChannel channel, Executor executor) {
         this.reference = new TypeReference<Map<String, String>>() {};
         this.process = new TerminalProcess(channel, executor);
         this.mapper = new ObjectMapper();
      }

      @Override
      public void onFrame(Session session, Frame frame) {
         FrameType type = frame.getType();

         if (type.isText()) {
            try {
               String text = frame.getText();
               Map<String, String> message = mapper.readValue(text, reference);
               String value = message.get("type");

               log.info("Terminal command: {}", text);
               
               if ("TERMINAL_INIT".equals(value)) {
                  process.onTerminalInit();
               } else if ("TERMINAL_READY".equals(value)) {
                  process.onTerminalReady();
               } else if ("TERMINAL_COMMAND".equals(value)) {
                  String command = message.get("command");
                  process.onTerminalCommand(command);
               } else if ("TERMINAL_RESIZE".equals(value)) {
                  String columns = message.get("columns");
                  String rows = message.get("rows");
                  process.onTerminalResize(columns, rows);
               }

            } catch (Exception e) {
               log.info("Could not process frame", e);
            }
         }
      }

      @Override
      public void onError(Session session, Exception cause) {
         log.info("Error occured", cause);
         process.onTerminalClose();
      }

      @Override
      public void onClose(Session session, Reason reason) {
         log.info("Session closed");
         process.onTerminalClose();
      }
   }
}