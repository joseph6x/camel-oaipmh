package es.upm.oeg.camel.oaipmh.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultPollingEndpoint;
import org.apache.camel.spi.UriParam;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Represents a OAIPMH endpoint.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OAIPMHEndpoint extends DefaultPollingEndpoint {

    private final String url;

    @UriParam
    private String from;
    @UriParam
    private String until;
    @UriParam
    private String set;
    @UriParam
    private String verb = "ListRecords";
    @UriParam
    private String metadataPrefix = "oai_dc";


    public OAIPMHEndpoint(String uri, String url, OAIPMHComponent component) {
        super(uri, component);
        this.url = url;
    }

    public Producer createProducer() throws Exception {
        return new OAIPMHProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        OAIPMHConsumer consumer = new OAIPMHConsumer(this, processor);
        validateParameters();
        configureConsumer(consumer);
        return consumer;
    }


    private void validateParameters(){
        // From parameter in ISO 8601 format
        if (from != null) ISODateTimeFormat.dateTimeNoMillis().parseDateTime(from);
        if (until != null) ISODateTimeFormat.dateTimeNoMillis().parseDateTime(until);
    }

    public boolean isSingleton() {
        return true;
    }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getUntil() {
    return until;
  }

  public void setUntil(String until) {
    this.until = until;
  }

  public String getSet() {
    return set;
  }

  public void setSet(String set) {
    this.set = set;
  }

  public String getVerb() {
    return verb;
  }

  public void setVerb(String verb) {
    this.verb = verb;
  }

  public String getMetadataPrefix() {
    return metadataPrefix;
  }

  public void setMetadataPrefix(String metadataPrefix) {
    this.metadataPrefix = metadataPrefix;
  }
    
  public String getUrl() {
    return this.url;
  }  
    
}
