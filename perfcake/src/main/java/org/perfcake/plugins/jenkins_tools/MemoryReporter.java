package org.perfcake.plugins.jenkins_tools;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.perfcake.common.PeriodType;
import org.perfcake.reporting.Measurement;
import org.perfcake.reporting.MeasurementUnit;
import org.perfcake.reporting.Quantity;
import org.perfcake.reporting.ReportingException;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.reporting.reporters.AbstractReporter;
import org.perfcake.reporting.reporters.accumulators.Accumulator;

public class MemoryReporter extends AbstractReporter {

   private String hostname;
   private String jmxPort;

   private MemoryMXBean memoryMbeanProxy;
   private MemoryPoolMXBean permgenMbeanProxy;

   @Override
   public void start() {
      super.start();
      try {
         JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + hostname + ":" + jmxPort + "/jmxrmi");
         JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
         MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

         memoryMbeanProxy = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
         permgenMbeanProxy = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",name=PS Perm Gen", MemoryPoolMXBean.class);
      } catch (IOException e) {
         // TODO handle exception
         e.printStackTrace();
      }

   }

   public void publishResult(PeriodType periodType, Destination d) throws ReportingException {
      MemoryUsage muHeap = memoryMbeanProxy.getHeapMemoryUsage();
      MemoryUsage muNonHeap = memoryMbeanProxy.getNonHeapMemoryUsage();
      MemoryUsage muPermGen = permgenMbeanProxy.getUsage();
      
      Measurement m = newMeasurement();
      m.set("Heap used", (new Quantity<Number>(muHeap.getUsed(), "B")));
      m.set("NonHeap used", (new Quantity<Number>(muNonHeap.getUsed(), "B")));
      m.set("PermGen used", (new Quantity<Number>(muPermGen.getUsed(), "B")));
      d.report(m);
   }

   @Override
   protected Accumulator getAccumulator(String key, Class clazz) {
      return null;
   }

   @Override
   protected void doReset() {
   }

   @Override
   protected void doReport(MeasurementUnit mu) throws ReportingException {
   }

   public void setHostname(String hostname) {
      this.hostname = hostname;
   }

   public void setJmxPort(String jmxPort) {
      this.jmxPort = jmxPort;
   }

}
