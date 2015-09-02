package com.rhc.lab.service.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhc.lab.dao.BookingRepository;
import com.rhc.lab.dao.VenueRepository;
import com.rhc.lab.domain.Booking;
import com.rhc.lab.domain.BookingRequest;
import com.rhc.lab.domain.PerformanceType;
import com.rhc.lab.domain.Performer;
import com.rhc.lab.domain.Venue;

/**
 * 
 * This class is an example Camel test showing mock endpoints and a Java DSL
 * mock route to process a message
 * 
 */
// FIXME - this test fails in Jenkins
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:camel-context.xml"})
public class RulesServiceTest {
	// Camel final endpoint
	@EndpointInject(uri = "mock:end")
	protected MockEndpoint resultEndpoint;
	// Camel start endpoint
	@Produce(uri = "direct:start")
	protected ProducerTemplate template;
	// TODO: make sure we can use NOT mongo repos
	@Resource(name = "bookingDao")
	BookingRepository bookingRepo;
	@Resource(name = "venueDao")
	VenueRepository venueRepo;

	// shared performer
	private Performer performer;

	@Before
	public void setUp() {
		// clearing venue repo
		venueRepo.deleteAll();
		// set up mock venue
		Venue venue = new Venue();
		venue.setId("1");
		venue.setName("boo");
		// save venue
		List<PerformanceType> types = new ArrayList<PerformanceType>();
		types.add(PerformanceType.COMIC);
		venue.setAccomodations(types);
		venueRepo.save(venue);
		// clear booking repo
		bookingRepo.deleteAll();
		// set up shared performer
		performer = new Performer();
		performer.setName("Bob");
		performer.setType(PerformanceType.COMIC);
	}

	@Test
	public void shouldSaveValidBooking() throws InterruptedException {
		// building booking request
		BookingRequest request = new BookingRequest();
		request.setVenueName("boo");
		request.setPerformer(performer);
		request.setOpen(new Date());
		request.setClose(new Date());
		// sending request
		template.sendBody(request);
		// check that booking is in DB
		Iterable<Booking> bookings = bookingRepo.findAll();
		// makes sure booking from request is part of bookings in repo
		assertTrue(compareBookings(bookings, request));
	}

	/**
	 * fuzzy equals to compare a booking request and what is generated
	 * 
	 * @param bookings
	 * @param booking
	 * @return
	 */
	private boolean compareBookings(Iterable<Booking> bookings,
			BookingRequest booking) {
		for (Booking book : bookings) {
			if (book.getPerformer().equals(booking.getPerformer())
					&& book.getVenueName().equals(booking.getVenueName())
					&& booking.getOpen().equals(book.getOpen())
					&& booking.getClose().equals(book.getClose())) {
				return true;
			}
		}
		return false;
	}
}
