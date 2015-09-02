package com.rhc.lab.test.cucumber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.rhc.lab.domain.Booking;
import com.rhc.lab.domain.BookingRequest;
import com.rhc.lab.domain.BookingResponse;
import com.rhc.lab.domain.BookingStatus;
import com.rhc.lab.domain.PerformanceType;
import com.rhc.lab.domain.Performer;
import com.rhc.lab.domain.Venue;
import com.rhc.lab.kie.api.StatelessDecisionService;
import com.rhc.lab.service.BookingRequestService;
import com.rhc.lab.test.repository.BookingCucumberRepository;
import com.rhc.lab.test.repository.VenueCucumberRepository;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 * This class implements the steps described in the Cucumber feature file
 * located on the classpath, spelling out the requirements for the venue booking
 * project.
 * 
 */
@ContextConfiguration("classpath:cucumber.xml")
public class BaseSteps {
	@Resource(name = "localDecisionServiceBean")
	private StatelessDecisionService decisionService;

	private static final Logger logger = LoggerFactory
			.getLogger(BaseSteps.class);

	private VenueCucumberRepository venueRepo = new VenueCucumberRepository();
	private BookingCucumberRepository bookingRepo = new BookingCucumberRepository();
	private Venue venue = new Venue();
	private Booking booking = new Booking();
	private BookingRequest request = new BookingRequest();
	private BookingResponse response = new BookingResponse();
	private List<Object> facts;
	private BookingRequestService requestService;
	private static final String processId = "bookingProcess";

	@Given("^a venue \"(.*?)\" with an occupancy of \"(.*?)\"$")
	public void a_venue_with_an_occupancy_of(String venueName, String occupancy)
			throws Throwable {
		// Set properties regarding venueName/occupancy
		venue.setName(venueName);
		venue.setCapacity(Integer.parseInt(occupancy));

		// (Test repo-maps) Add venue to venueRepo
		if (venueRepo.findByName(venueName).isEmpty()) {
			venueRepo.save(venue);
		}

		request.setVenueName(venueName);
		
		logger.info("Given step: " + venueName + " " + occupancy);
	}

	@Given("^an existing \"(.*?)\" performance by \"(.*?)\" from \"(.*?)\" to \"(.*?)\"$")
	public void an_existing_performance_by_from_to(String performanceType,
			String performanceName, String open, String close) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		Performer performer = new Performer();
		performer.setName(performanceName);
		performer
				.setType(PerformanceType.valueOf(performanceType.toUpperCase()));

		booking = new Booking();
		booking.setPerformer(performer);
		Date dOpen = sdf.parse(open);
		Date dClose = sdf.parse(close);
		Assert.assertNotNull(dOpen);
		Assert.assertNotNull(dClose);
		booking.setOpen(dOpen);
		booking.setClose(dClose);
		booking.setVenueName(venue.getName());
		
		logger.info("saving previous booking " + booking.toString());
		bookingRepo.save(booking);

	}

	@Given("^the venue accomodates performances by a$")
	public void the_venue_accomodates_performances_by_a(List<String> artistTypes)
			throws Throwable {
		List<PerformanceType> accomodations = new ArrayList<PerformanceType>();
		for (String artistType : artistTypes) {
			accomodations.add(PerformanceType.valueOf(artistType));
		}

		venue.setAccomodations(accomodations);

		logger.info("And first step: " + artistTypes);

	}

	@And("^a request for a \"(.*?)\" performance by \"(.*?)\"$")
	public void a_request_for_a_performance_by(String type, String artistName)
			throws Throwable {
		// Set properties regarding performance requests
		Performer performer = new Performer();
		performer.setName(artistName);
		try {
			performer.setType(PerformanceType.valueOf(type.toUpperCase()));
		} catch (Exception e) {
			throw new Exception("Type '" + type + "' does not exist");
		}
		request.setPerformer(performer);

		logger.info("And second step: " + type + " " + artistName);
	}

	@And("^a dated request for a \"(.*?)\" performance by \"(.*?)\" from \"(.*?)\" to \"(.*?)\"$")
	public void a_request_for_a_performance_by_from(String type,
			String artistName, String open, String close) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		// Set properties regarding performance requests
		Performer performer = new Performer();
		performer.setName(artistName);
		try {
			performer.setType(PerformanceType.valueOf(type.toUpperCase()));
		} catch (Exception e) {
			throw new Exception("Type '" + type + "' does not exist");
		}
		request.setPerformer(performer);
		Date dOpen = sdf.parse(open);
		Date dClose = sdf.parse(close);
		Assert.assertNotNull(dOpen);
		Assert.assertNotNull(dClose);
		request.setOpen(dOpen);
		request.setClose(dClose);
		request.setVenueName(venue.getName());

		logger.info("And second step: " + request.toString());
	}

	@When("^validating the booking$")
	public void validating_the_booking() throws Throwable {
		// Run rules
		requestService = new BookingRequestService(bookingRepo, venueRepo);
		facts = requestService.buildSession(request);
		response = decisionService.execute(facts, processId,
				BookingResponse.class);
		requestService.saveBooking(response);

		logger.info("When step");
	}

	@Then("^the booking should be \"(.*?)\"$")
	public void the_booking_should_be(String bookingStatus) throws Throwable {

	  if (response.getBookingStatus() != null
				&& !response.getBookingStatus().isEmpty()) {
			BookingStatus status = response.getBookingStatus().iterator()
					.next();
			Assert.assertTrue(bookingStatus.equalsIgnoreCase(status.toString()));
		} else {
			Assert.fail("No booking status returned from the knowledge session.");
		}

	}

}
