package navi.vehicle.rental.lld.strategy;

import navi.vehicle.rental.lld.commons.Pair;
import navi.vehicle.rental.lld.enums.VehicleStatus;
import navi.vehicle.rental.lld.model.Vehicle;
import navi.vehicle.rental.lld.platform.PricingStrategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SimplePricingStrategy implements PricingStrategy {
    @Override
    public Pair<Vehicle, Double> getPrice(List<Pair<Vehicle, List<Pair<Integer, Integer>>>> listOfVehicleWithBookingDetails, Integer startHour, Integer endHour) {
        AtomicReference<Vehicle> vehicleWithLowestPrice = new AtomicReference<>();
        listOfVehicleWithBookingDetails.forEach(vehicleWithBookingDetails -> {
           if (vehicleWithBookingDetails.getFirst().getStatus().equals(VehicleStatus.AVAILABLE)
            && (vehicleWithBookingDetails.getSecond() == null || vehicleWithBookingDetails.getSecond().stream().noneMatch(bookingDetails -> bookingDetails.getFirst() <= endHour && bookingDetails.getSecond() >= startHour))
           ) {
               if (vehicleWithLowestPrice.get() == null || vehicleWithLowestPrice.get().getPrice() > vehicleWithBookingDetails.getFirst().getPrice()) {
                   vehicleWithLowestPrice.set(vehicleWithBookingDetails.getFirst());
               }
           }
        });
        return new Pair<>(vehicleWithLowestPrice.get(), vehicleWithLowestPrice.get().getPrice());
    }
}
