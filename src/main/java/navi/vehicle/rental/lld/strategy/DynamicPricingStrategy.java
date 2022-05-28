package navi.vehicle.rental.lld.strategy;

import navi.vehicle.rental.lld.commons.Pair;
import navi.vehicle.rental.lld.enums.VehicleStatus;
import navi.vehicle.rental.lld.model.Vehicle;
import navi.vehicle.rental.lld.platform.PricingStrategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DynamicPricingStrategy implements PricingStrategy {
    @Override
    public Pair<Vehicle, Double> getPrice(List<Pair<Vehicle, List<Pair<Integer, Integer>>>> listOfVehicleWithBookingDetails, Integer startHour, Integer endHour) {
        // Dynamic pricing logic
        // If 80% of vehicles are booked in the between start hour and end hour, then price is increased by 10%
        AtomicReference<Vehicle> vehicleWithLowestPrice = new AtomicReference<>();
        AtomicReference<Double> price = new AtomicReference<>(0.0);
        listOfVehicleWithBookingDetails.forEach(vehicleWithBookingDetails -> {
            if (vehicleWithBookingDetails.getFirst().getStatus().equals(VehicleStatus.AVAILABLE)
                    && (vehicleWithBookingDetails.getSecond() == null ||
                    vehicleWithBookingDetails.getSecond().stream().noneMatch(bookingDetails -> bookingDetails.getFirst() <= endHour && bookingDetails.getSecond() >= startHour))
            ) {
                if (vehicleWithLowestPrice.get() == null || vehicleWithLowestPrice.get().getPrice() > vehicleWithBookingDetails.getFirst().getPrice()) {
                    Boolean is80PercentVehicleBooked = vehicleWithBookingDetails.getSecond().stream().filter(bookingDetails -> bookingDetails.getFirst() <= endHour && bookingDetails.getSecond() >= startHour).count() >= 0.8 * listOfVehicleWithBookingDetails.size();
                    price.set(vehicleWithBookingDetails.getFirst().getPrice() * (is80PercentVehicleBooked ? 1.1 : 1));
                    vehicleWithLowestPrice.set(vehicleWithBookingDetails.getFirst());
                }
            }
        });
        return new Pair<>(vehicleWithLowestPrice.get(), price.get());
    }
}
