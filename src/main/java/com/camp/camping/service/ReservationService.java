package com.camp.camping.service;

import com.camp.camping.DTO.BookDTO;
import com.camp.camping.DTO.ReservationDTO;
import com.camp.camping.frame.MyService;
import com.camp.camping.mapper.BookMapper;
import com.camp.camping.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService implements MyService<Integer, ReservationDTO> {
    @Autowired
    ReservationMapper mapper;
    @Autowired
    BookMapper bookMapper;

    @Override
    public void insert(ReservationDTO v) throws Exception {
        mapper.insert(v);
    }

    @Override
    public void delete(Integer k) throws Exception {
        mapper.delete(k);
    }

    @Override
    public void update(ReservationDTO v) throws Exception {
        mapper.update(v);
    }

    @Override
    public ReservationDTO select(Integer k) throws Exception {
        return mapper.select(k);
    }

    @Override
    public List<ReservationDTO> selectAll() throws Exception {
        return mapper.selectAll();
    }
    public List<ReservationDTO> selectDate(Date d) throws Exception {
        return mapper.selectDate(d);
    }
    public void deleteByBook(Integer k) throws Exception {
        mapper.deleteByBook(k);
    }
    public void insertReservationByBook(BookDTO book) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationDate = book.getBook_checkout();

        Calendar cal = Calendar.getInstance();
        cal.setTime(reservationDate);

        for (int i = BookDays(book); i > 0; i--) {
            cal.add(Calendar.DATE, -1);
            ReservationDTO reservation = new ReservationDTO(book.getBook_code(), "" + sdf.format(cal.getTime()));
            mapper.insert(reservation);
        }
    }

    private int BookDays(BookDTO book){
        long diff = (book.getBook_checkout().getTime() - book.getBook_checkin().getTime()) / 1000;
        long difDays = diff / (24 * 60 * 60);
        return (int)difDays;
    }
}
