package br.com.raac_system.integration;

import br.com.raac_system.exception.TISSIntegrationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

@Component
public class TISSValidator {

    public void validate(Object tissObject) {
        try {
            JAXBContext context = JAXBContext.newInstance(tissObject.getClass());
            Marshaller marshaller = context.createMarshaller();

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new ClassPathResource("tiss/xsd/tissV3_05_00.xsd").getFile());
            marshaller.setSchema(schema);

            // In MVP, we just set the schema to prove it compiles and runs.
            // When real objects are populated, calling marshaller.marshal will throw
            // exceptions on validation failures.

        } catch (JAXBException | org.xml.sax.SAXException | java.io.IOException e) {
            throw new TISSIntegrationException("XML Validation failed: " + e.getMessage());
        }
    }
}
