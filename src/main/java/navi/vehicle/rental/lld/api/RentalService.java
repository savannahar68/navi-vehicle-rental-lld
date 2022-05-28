package navi.vehicle.rental.lld.api;

import navi.vehicle.rental.lld.enums.VehicleType;

import java.util.List;

public interface RentalService {
    boolean addBranch(String branchName, List<VehicleType> vehicleTypes);
    boolean addVehicle(String branchName, VehicleType type, String vehicleId, Double price);
    double bookVehicle(String branchName, VehicleType type, Integer startingHour, Integer endingHour);
    List<String> displayVehicles(String branchName, Integer startingHour, Integer endingHour);
}
