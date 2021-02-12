package com.academy.training.controller;

import com.academy.training.generated.Aanvraag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class XmlUploadController {

    @PostMapping("/xmluploadunsafe")
    public ResponseEntity<Aanvraag> xmlUploadUnSafe(@RequestParam("file") MultipartFile file) {

        Aanvraag aanvraag = parseAanvraagUnSafe( file);

        return ResponseEntity.ok(aanvraag) ;

    }

    private Aanvraag parseAanvraagUnSafe(MultipartFile file) {

        String SCHEMA_NS_URI = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory sf = SchemaFactory.newInstance(SCHEMA_NS_URI);

        Aanvraag aanvraag = null;

        try(InputStream inputStream = file.getInputStream()){

            JAXBContext jc = JAXBContext.newInstance("com.academy.training.generated");

            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, true);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);

            XMLStreamReader reader = xif.createXMLStreamReader(inputStream);

            Unmarshaller u = jc.createUnmarshaller();

            aanvraag = (Aanvraag)u.unmarshal(reader);

        }catch (Exception e) {

            System.out.println("Oh oh...");
        }

        return aanvraag;

    }

}