package fr.eseo.equipe2.pglback.consumeApi.siri;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class SiriStopMonitoringHttpClient implements SiriStopMonitoringClient {
    private static final String SIRI_URL = "https://ara-api.enroute.mobi/irigo/siri";
    private static final String REQUESTOR_REF = "opendata";
    private static final String SIRI_NAMESPACE = "http://www.siri.org.uk/siri";

    private static final String REQUEST_TEMPLATE = """
            <?xml version="1.0" encoding="UTF-8"?>
            <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
              <S:Body>
                <sw:GetStopMonitoring xmlns:sw="http://wsdl.siri.org.uk">
                  <ServiceRequestInfo>
                    <RequestTimestamp>%1$s</RequestTimestamp>
                    <RequestorRef>%2$s</RequestorRef>
                  </ServiceRequestInfo>
                  <Request>
                    <RequestTimestamp>%1$s</RequestTimestamp>
                    <siri:MonitoringRef xmlns:siri="http://www.siri.org.uk/siri">%3$s</siri:MonitoringRef>
                  </Request>
                </sw:GetStopMonitoring>
              </S:Body>
            </S:Envelope>
            """;

    private final RestTemplate restTemplate;

    public SiriStopMonitoringHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<SiriMonitoredStopVisit> fetchStopMonitoring(String stopId) {
        String requestBody = REQUEST_TEMPLATE.formatted(Instant.now().toString(), REQUESTOR_REF, escapeXml(stopId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        String responseBody = restTemplate.postForObject(SIRI_URL, new HttpEntity<>(requestBody, headers), String.class);
        if (responseBody == null) {
            throw new RestClientException("Empty SIRI StopMonitoring response for stop " + stopId);
        }

        return parseStopVisits(responseBody, stopId);
    }

    private List<SiriMonitoredStopVisit> parseStopVisits(String xml, String stopId) {
        Document document = parseXml(xml, stopId);

        NodeList errorTexts = document.getElementsByTagNameNS(SIRI_NAMESPACE, "ErrorText");
        if (errorTexts.getLength() > 0) {
            throw new RestClientException("SIRI StopMonitoring error for stop " + stopId + ": " + errorTexts.item(0).getTextContent());
        }

        NodeList visitNodes = document.getElementsByTagNameNS(SIRI_NAMESPACE, "MonitoredStopVisit");
        List<SiriMonitoredStopVisit> visits = new ArrayList<>();

        for (int i = 0; i < visitNodes.getLength(); i++) {
            Element visit = (Element) visitNodes.item(i);
            Element journey = firstChild(visit, "MonitoredVehicleJourney");
            Element call = firstChild(journey, "MonitoredCall");

            visits.add(new SiriMonitoredStopVisit(
                    textOf(journey, "LineRef"),
                    textOf(journey, "JourneyPatternName"),
                    textOf(journey, "DestinationName"),
                    instantOf(call, "AimedArrivalTime"),
                    instantOf(call, "ExpectedArrivalTime"),
                    instantOf(call, "AimedDepartureTime"),
                    instantOf(call, "ExpectedDepartureTime")
            ));
        }

        return visits;
    }

    private Document parseXml(String xml, String stopId) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RestClientException("Malformed SIRI StopMonitoring response for stop " + stopId, e);
        }
    }

    private Element firstChild(Element parent, String localName) {
        if (parent == null) return null;
        NodeList nodes = parent.getElementsByTagNameNS(SIRI_NAMESPACE, localName);
        return nodes.getLength() > 0 ? (Element) nodes.item(0) : null;
    }

    private String textOf(Element parent, String localName) {
        Element element = firstChild(parent, localName);
        if (element == null) return null;
        String text = element.getTextContent();
        return (text == null || text.isBlank()) ? null : text;
    }

    private Instant instantOf(Element parent, String localName) {
        String text = textOf(parent, localName);
        return text == null ? null : Instant.parse(text);
    }

    private String escapeXml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
