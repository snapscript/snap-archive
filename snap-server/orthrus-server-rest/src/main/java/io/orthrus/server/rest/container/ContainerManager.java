package io.orthrus.server.rest.container;

import java.io.Closeable;

public interface ContainerManager extends Closeable{
   int getPort();
   boolean isDebug();
   void setDebug(boolean enable);
}
