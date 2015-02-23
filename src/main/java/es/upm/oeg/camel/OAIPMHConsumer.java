package es.upm.oeg.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultScheduledPollConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The OAIPMH consumer.
 */
public class OAIPMHConsumer extends DefaultScheduledPollConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OAIPMHConsumer.class);

    private final OAIPMHEndpoint endpoint;

    private final OAIPMHHttpClient httpClient;

    public OAIPMHConsumer(OAIPMHEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        this.httpClient = new OAIPMHHttpClient();
    }

    @Override
    protected int poll() throws Exception {

        //Http-GET
        String response = httpClient.doRequest(endpoint);

        //TODO Handle resumptionToken

        Exchange exchange = endpoint.createExchange();

        // create a message body
        exchange.getIn().setBody(response);

        try {
            // send message to next processor in the route
            getProcessor().process(exchange);
            return 1; // number of messages polled
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
    }





}
