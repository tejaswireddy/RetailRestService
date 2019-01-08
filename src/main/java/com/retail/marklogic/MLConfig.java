package com.retail.marklogic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "product.ml-config.marklogic")
public class MLConfig {

        private List<String> hosts;
        private String port;
        private String username;
        private String password;
        private String contentDatabase;


        public MLConfig() {

        }

        public List<String> getHosts() {
            return hosts;
        }

        public void setHosts(List<String> hosts) {
            this.hosts = hosts;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getContentDatabase() {
            return contentDatabase;
        }

        public void setContentDatabase(String contentDatabase) {
            this.contentDatabase = contentDatabase;
        }


}
