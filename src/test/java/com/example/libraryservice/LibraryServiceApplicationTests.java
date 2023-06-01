package com.example.libraryservice;

import com.example.libraryservice.libobjects.LibBD;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.xml.datatype.DatatypeConfigurationException;
import java.text.ParseException;
@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest(properties = {"kafka-requests-path=direct:requests"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
public class LibraryServiceApplicationTests {

	@Autowired
	ProducerTemplate producerTemplate;

	@EndpointInject("mock:jpa:com.example.libraryservice.libobjects.LibBD")
	public MockEndpoint saveToDb;

	@EndpointInject("mock:kafka:results")
	public MockEndpoint kafkaResults;

	@EndpointInject("mock:kafka:status_topic")
	public MockEndpoint kafkaStatusTopic;

	@Test
	public void saveToDBTest() throws InterruptedException, ParseException, DatatypeConfigurationException {
		LibBD book = new LibBD();
		book.setTitleBD("Masquerade");
		book.setAuthorBD("Lermontov");
		saveToDb.expectedBodiesReceived(book);

		producerTemplate.sendBody("direct:requests", "<book><title>Masquerade</title>" +
                "<year>1985</year><author>Lermontov</author></book>");

		saveToDb.assertIsSatisfied(5000);
	}

	@Test
	public void kafkaResultsTest() throws InterruptedException {
		kafkaResults.expectedBodiesReceived("{\"title\":\"Masquerade\",\"author\":\"Lermontov\"}");

		producerTemplate.sendBody("direct:requests", "<book><title>Masquerade</title>" +
				"<year>1985</year><author>Lermontov</author></book>");

		MockEndpoint.assertIsSatisfied(kafkaResults);
	}

	@Test
	public void sendOKStatusTest() throws InterruptedException {
		kafkaStatusTopic.expectedBodiesReceived("<status>ok</status>");

		producerTemplate.sendBody("direct:requests", "<book><title>Masquerade</title>" +
				"<year>1985</year><author>Lermontov</author></book>");

		kafkaStatusTopic.assertIsSatisfied(5000);
	}

	@Test
	public void sendErrorStatusTest() throws InterruptedException {
		kafkaStatusTopic.expectedBodiesReceived("<status>error</status><message>fail</message>");

		producerTemplate.sendBody("direct:requests", "<b00k><title>Masquerade</title>" +
				"<year>1985</year><author>Lermontov</author></b00k>");

		kafkaStatusTopic.assertIsSatisfied(5000);
	}
}

