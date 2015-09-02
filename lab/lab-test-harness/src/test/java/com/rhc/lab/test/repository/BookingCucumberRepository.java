package com.rhc.lab.test.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rhc.lab.dao.BookingRepository;
import com.rhc.lab.domain.Booking;
import com.rhc.lab.service.BookingRequestService;

public class BookingCucumberRepository implements BookingRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(BookingCucumberRepository.class);

	private Map<String, Booking> bookingRepo = new HashMap<String, Booking>();

	@Override
	public Iterable<Booking> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Booking> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {

		return bookingRepo.size();
	}

	@Override
	public void delete(String id) {
		bookingRepo.remove(id);

	}

	@Override
	public void delete(Booking booking) {
		if (bookingRepo.containsValue(booking)) {
			bookingRepo.remove(booking.getId());
		}

	}

	@Override
	public void delete(Iterable<? extends Booking> bookings) {
		for (Booking booking : bookings) {
			bookingRepo.remove(booking);
		}

	}

	@Override
	public void deleteAll() {
		bookingRepo.clear();
	}

	@Override
	public boolean exists(String arg0) {
		return bookingRepo.containsKey(arg0);
	}

	@Override
	public Iterable<Booking> findAll() {

		return bookingRepo.values();
	}

	@Override
	public Iterable<Booking> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Booking findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Booking> S save(S booking) {
		bookingRepo.put(booking.getId(), booking);
		return booking;
	}

	@Override
	public <S extends Booking> Iterable<S> save(Iterable<S> bookings) {
		for (Booking booking : bookings) {
			bookingRepo.put(booking.getId(), booking);
		}
		return null;
	}

	@Override
	public List<Booking> findByVenueName(String name) {
		logger.info("looking in dao");

		// TODO Auto-generated method stub
		List<Booking> list = new ArrayList<Booking>();
		for (Booking booking : bookingRepo.values()) {
			if (booking.getVenueName().equals(name)) {
				list.add(booking);
			}
		}
		return list;
	}

	public void clear() {
		bookingRepo.clear();

	}

}
