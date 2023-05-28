package vincentlow.twittur.information.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"vincentlow.twittur.information.*"})
@EntityScan(basePackages = {"vincentlow.twittur.information.*"})
@EnableJpaRepositories(basePackages = {"vincentlow.twittur.information.*"})
@EnableFeignClients(basePackages = {"vincentlow.twittur.information.*"})
// if you don't use "basePackages", might not read the @FeignClient or other beans
public class KrakenInformationApplication {

  public static void main(String[] args) {

    SpringApplication.run(KrakenInformationApplication.class);
  }
}
