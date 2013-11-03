package org.perfcake.plugins.jenkins_tools;

import java.io.IOException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
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

import com.sun.management.OperatingSystemMXBean;

public class CpuLoadReporter extends AbstractReporter {
   
   private String hostname;
   private String jmxPort;

   @SuppressWarnings("restriction")
   private OperatingSystemMXBean osMbeanProxy;
   
   @Override
   public void start() {
      super.start();
      try {
         JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + hostname + ":" + jmxPort + "/jmxrmi");
         JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
         MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
         
         ObjectName mbeanName = new ObjectName("java.lang:type=OperatingSystem");
         osMbeanProxy = (com.sun.management.OperatingSystemMXBean)JMX.newMBeanProxy(mbsc, mbeanName, OperatingSystemMXBean.class, true);
      } catch(MalformedObjectNameException | IOException e) {
         //TODO handle exception
         e.printStackTrace();
      }
      
   }

   public void publishResult(PeriodType periodType, Destination d) throws ReportingException {
      Measurement m = newMeasurement();
      m.set("Process CPU load", (new Quantity<Number>(osMbeanProxy.getProcessCpuLoad(), "none")));
      m.set("System CPU load", (new Quantity<Number>(osMbeanProxy.getSystemCpuLoad(), "none")));
      m.set("System CPU load avg.", (new Quantity<Number>((double)osMbeanProxy.getSystemLoadAverage(), "none")));
      d.report(m);
   }

   @Override
   protected Accumulator getAccumulator(String key, Class clazz) {
      return null;
   }

   @Override
   protected void doReset() {}

   @Override
   protected void doReport(MeasurementUnit mu) throws ReportingException {}

   public void setHostname(String hostname) {
      this.hostname = hostname;
   }

   public void setJmxPort(String jmxPort) {
      this.jmxPort = jmxPort;
   }
   
}
