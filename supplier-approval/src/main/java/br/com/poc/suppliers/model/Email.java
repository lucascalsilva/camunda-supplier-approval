package br.com.poc.suppliers.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Email {

    private String to;
    private String from;
    private String subject;
    private HtmlTemplate htmlTemplate;

    @Data
    @Builder
    public static class HtmlTemplate {
        private String template;
        private Map<String, Object> props;

        public HtmlTemplate(String template, Map<String, Object> props) {
            this.template = template;
            this.props = props;
        }
    }
}
