package navi.vehicle.rental.lld.service;

import navi.vehicle.rental.lld.api.RentalService;
import navi.vehicle.rental.lld.commons.Pair;
import navi.vehicle.rental.lld.enums.VehicleStatus;
import navi.vehicle.rental.lld.enums.VehicleType;
import navi.vehicle.rental.lld.model.Branch;
import navi.vehicle.rental.lld.model.Vehicle;
import navi.vehicle.rental.lld.platform.PricingStrategy;
import navi.vehicle.rental.lld.platform.StorageAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class RentalServiceImpl implements RentalService {

    StorageAdapter storageAdapter;
    PricingStrategy pricingStrategy;

    public RentalServiceImpl(StorageAdapter storageAdapter, PricingStrategy pricingStrategy) {
        this.storageAdapter = storageAdapter;
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public boolean addBranch(String branchName, List<VehicleType> vehicleTypes) {
        try {
            storageAdapter.addBranch(new Branch(branchName, new HashSet<VehicleType>(vehicleTypes)));
            return true;
        } catch (Exception e) {
            System.out.println("Error while adding branch"); // Replace this by logger and do proper logging
            return false;
        }
    }

    @Override
    public boolean addVehicle(String branchName, VehicleType type, String vehicleId, Double price) {
        try {
            // TODO: ideally the below 2 statements will be a single transaction in database
            // TODO: transaction start
            storageAdapter.addVehicle(branchName, new Vehicle(vehicleId, type, VehicleStatus.AVAILABLE, price));
            storageAdapter.addBranchVehicleMapping(branchName, vehicleId);
            // TODO: transaction ends
            return true;
        } catch (Exception e) {
            System.out.println("Error while adding vehicle"); // Replace this by logger
            return false;
        }
    }

    @Override
    public double bookVehicle(String branchName, VehicleType type, Integer startingHour, Integer endingHour) {
        try {
            List<Pair<Vehicle, List<Pair<Integer, Integer>>>> vehicleWithBookingList = storageAdapter.getVehicleTypeBookingForBranch(branchName, type);
            if (vehicleWithBookingList.isEmpty()) return -1;

            Pair<Vehicle, Double> vehicleWithPrice = pricingStrategy.getPrice(vehicleWithBookingList, startingHour, endingHour);
            storageAdapter.addVehicleBooking(vehicleWithPrice.getFirst().getVehicleId(), startingHour, endingHour);

            return vehicleWithPrice.getSecond();
        } catch (Exception e) {
            System.out.println("Error while booking vehicle" + e); // Replace this by logger
            return -1;
        }
    }

    @Override
    public List<String> displayVehicles(String branchName, Integer startingHour, Integer endingHour) {
        List<String> vehicleIdList = storageAdapter.getBranchVehicleMapping(branchName);
        vehicleIdList = vehicleIdList.stream().filter(vehicleId ->
                storageAdapter.getVehicleBooking(vehicleId) == null || (
                storageAdapter.getVehicle(vehicleId).getStatus().equals(VehicleStatus.AVAILABLE)
                        && storageAdapter.getVehicleBooking(vehicleId).stream()
                        .noneMatch(booking -> startingHour >= booking.getFirst()  || booking.getSecond() <= endingHour)))
                .collect(Collectors.toList());
        return vehicleIdList;
    }
}
