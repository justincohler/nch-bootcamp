package com.rhc.lab.test.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhc.lab.domain.BookingRequest;
import com.rhc.lab.domain.PerformanceType;
import com.rhc.lab.domain.Performer;
import com.rhc.lab.domain.Venue;

/**
 * 
 * This JUnit test class tests Jackson mapping of JSON to and from the Java
 * domain model
 * 
 */
public class JsonPayloadTest {

	private static final Logger logger = LoggerFactory
			.getLogger(JsonPayloadTest.class);

	private ObjectMapper om;

	@Before
	public void init() {
		om = new ObjectMapper();
	}

	@Test
	public void shouldMarshalAndUnmarshalJson() throws IOException {

		BookingRequest request = populateBookingRequest();

		String requestString = om.writer().writeValueAsString(request);

		assertNotNull(requestString);

		logger.info(om.writerWithDefaultPrettyPrinter().writeValueAsString(
				request));

		BookingRequest r = om.readValue(requestString, BookingRequest.class);
		assertNotNull(r.getOpen());
		assertNotNull(r.getClose());
		assertNotNull(r.getPerformer().getName(), r.getPerformer().getType());
		if (r.getPerformer().getName().equals("The Velvet Underground")) {
			assertEquals(PerformanceType.BAND, r.getPerformer().getType());
		} else if (r.getPerformer().getName()
				.equals("Brooklyn Symphony Orchestra")) {
			assertEquals(PerformanceType.ORCHESTRA, r.getPerformer().getType());
		}
	}

	public BookingRequest populateBookingRequest() {
		BookingRequest request = new BookingRequest();
		Performer performance = new Performer();
		Venue venue = new Venue();

		venue.setName("Brooklyn Bowl");
		venue.setCity("Brooklyn");
		venue.setCapacity(8000);
		venue.setAccomodations(new ArrayList<PerformanceType>(Arrays.asList(
				PerformanceType.BAND, PerformanceType.ORCHESTRA,
				PerformanceType.RALLY)));
		performance.setName("The Velvet Underground");
		performance.setType(PerformanceType.BAND);

		request.setVenueName(venue.getName());
		DateTime d1 = new DateTime();
		DateTime d2 = new DateTime().plusHours(4);
		request.setOpen(d1.toDate());
		request.setClose(d2.toDate());
		request.setPerformer(performance);

		return request;
	}

}
