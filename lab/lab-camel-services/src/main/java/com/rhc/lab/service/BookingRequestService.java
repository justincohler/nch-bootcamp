package com.rhc.lab.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rhc.lab.dao.BookingRepository;
import com.rhc.lab.dao.VenueRepository;
import com.rhc.lab.domain.Booking;
import com.rhc.lab.domain.BookingRequest;
import com.rhc.lab.domain.BookingResponse;
import com.rhc.lab.domain.BookingStatus;
import com.rhc.lab.domain.Venue;

/**
 * 
 * This service is used by Camel to execute rules and call saves in the various
 * DAO repositories.
 * 
 */
// XXX
@Service("requestSearvice")
public class BookingRequestService {

	private static final Logger logger = LoggerFactory
			.getLogger(BookingRequestService.class);
	@Resource(name = "bookingDao")
	BookingRepository bookingRepo;
	@Resource(name = "venueDao")
	VenueRepository venueRepo;

	public BookingRequestService() {

	}

	public BookingRequestService(BookingRepository bookingRepo,
			VenueRepository venueRepo) {
		this.bookingRepo = bookingRepo;
		this.venueRepo = venueRepo;
	}

	public List<Object> buildSession(@Body BookingRequest request)
			throws Exception {

		// collect all the relevent data for the ksession
		List<Object> facts = collectSingleVenueForSession(request);
		// post the facts to the body of the exchange
		// put on the in so we do not loose the headers
		return facts;
	}

	public boolean saveBooking(@Body BookingResponse response) {
		// TODO: logger not Sys out
		System.out.println("Session returned: " + response.toString());
		Booking booking = response.generateBooking();
		try {
			// attempting to save the bookings returned
			if (response.getBookingStatus().iterator().next() == BookingStatus.CONFIRMED) {
				// TODO: logger not Sys out
				System.out.println("attempting to save booking: "
						+ booking.toString());
				bookingRepo.save(booking);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * Returns the venue mentioned in the request and all bookings associated
	 * with that venue
	 * 
	 * @param request
	 * @return
	 */
	public List<Object> collectSingleVenueForSession(BookingRequest request) {
		// TODO: logger not sys out
		System.out.println("collect venue:" + request.getVenueName());
		List<Venue> venue = venueRepo.findByName(request.getVenueName());
		// TODO: logger not sys out
		System.out.println("venue found:" + venue);
		List<Booking> bookings = bookingRepo.findByVenueName(request
				.getVenueName());

		List<Object> facts = new ArrayList<Object>();
		facts.addAll(venue);
		facts.addAll(bookings);
		facts.add(request);
		return facts;
	}

}
