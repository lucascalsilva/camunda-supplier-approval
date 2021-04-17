package br.com.poc.suppliers;

import com.camunda.consulting.simulator.PayloadGenerator;
import com.camunda.consulting.simulator.SimulationExecutor;
import com.camunda.consulting.simulator.SimulatorPlugin;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableProcessApplication("supplier-approval")
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
    //SimulationExecutor.execute(DateTime.now().minusMonths(1).toDate(), DateTime.now().toDate());
  }

  /*@Bean
  public SimulatorPlugin simulatorPlugin() {
    return new SimulatorPlugin();
  }

  @Bean
  public PayloadGenerator generator(){
    return new PayloadGenerator();
  }*/

}