package navi.vehicle.rental.lld.platform;

import navi.vehicle.rental.lld.commons.Pair;
import navi.vehicle.rental.lld.model.Vehicle;

import java.util.List;

public interface PricingStrategy {
    // TODO: add throw exception signature for all the internal Apis
    Pair<Vehicle, Double> getPrice(List<Pair<Vehicle, List<Pair<Integer, Integer>>>> listOfVehicleWithBookingDetails, Integer startHour, Integer endHour);
}
