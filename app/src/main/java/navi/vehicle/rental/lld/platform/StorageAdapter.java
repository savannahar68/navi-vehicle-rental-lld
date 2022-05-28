package navi.vehicle.rental.lld.platform;

import navi.vehicle.rental.lld.commons.Pair;
import navi.vehicle.rental.lld.enums.VehicleType;
import navi.vehicle.rental.lld.exceptions.VehicleNotFoundException;
import navi.vehicle.rental.lld.model.Branch;
import navi.vehicle.rental.lld.model.Vehicle;

import java.util.List;

public interface StorageAdapter {
    // TODO: add throw exception signature for all the internal Apis
    Branch getBranch(String branchName);
    Vehicle getVehicle(String vehicleId);
    void addBranch(Branch branch);
    void addVehicle(String branchName, Vehicle vehicle);
    void deleteBranch(Branch branch);
    void deleteVehicle(Vehicle vehicle);
    void addBranchVehicleMapping(String branchName, String vehicleId);
    List<String> getBranchVehicleMapping(String branchName);
    void addVehicleBooking(String vehicleId, Integer startingHour, Integer endingHour) throws VehicleNotFoundException;
    List<Pair<Integer, Integer>> getVehicleBooking(String vehicleId);
    // TODO: Create class for return type List<Pair<Vehicle, List<Pair<Integer, Integer>>>> and use it at other places
    List<Pair<Vehicle, List<Pair<Integer, Integer>>>> getVehicleTypeBookingForBranch(String branchName, VehicleType vehicleType);
}
