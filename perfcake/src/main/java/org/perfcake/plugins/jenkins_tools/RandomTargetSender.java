package org.perfcake.plugins.jenkins_tools;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.perfcake.message.Message;
import org.perfcake.message.sender.HTTPSender;
import org.perfcake.reporting.MeasurementUnit;

/**
 * Ugly hack how to generate unique job name URL for each request in the thread without modifying perf-cake core and minimal coding effort:-)
 * 
 * @author vjuranek
 * 
 */
public class RandomTargetSender extends HTTPSender {

   @Override
   public void init() throws Exception {
      String origTarget = getTarget();
      setTarget(getTarget().replaceFirst("\\$RANDOM", UUID.randomUUID().toString()));
      super.init();
      setTarget(origTarget);
   }

   @Override
   public Serializable doSend(final Message message, final Map<String, String> properties, final MeasurementUnit mu) throws Exception {
      init();
      super.preSend(message, properties);
      Serializable result = super.doSend(message, properties, mu);
      super.postSend(message);
      return result;
   }

}
