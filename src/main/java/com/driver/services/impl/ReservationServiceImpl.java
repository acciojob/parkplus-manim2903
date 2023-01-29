package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Reservation reservation=new Reservation();
        User user=userRepository3.findById(userId).get();
        ParkingLot parkingLot=parkingLotRepository3.findById(parkingLotId).get();
        List<Spot> spots=parkingLot.getSpotList();
        SpotType reserveSpotType=null;
        for(Spot spot : spots){
            if(numberOfWheels==2)
                reserveSpotType=SpotType.TWO_WHEELER;
            else if(numberOfWheels==4)
                reserveSpotType=SpotType.FOUR_WHEELER;
            else reserveSpotType=SpotType.OTHERS;
            if (spot.getSpotType()==reserveSpotType && spot.getOccupied()){
                reservation.setSpot(spot);
                reservation.setUser(user);
                reservation.setNumberOfHours(timeInHours);

            }
        }
        reservationRepository3.save(reservation);
        return reservation;
    }
}
