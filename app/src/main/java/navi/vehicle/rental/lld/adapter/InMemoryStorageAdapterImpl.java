package navi.vehicle.rental.lld.adapter;

import navi.vehicle.rental.lld.commons.Pair;
import navi.vehicle.rental.lld.enums.VehicleType;
import navi.vehicle.rental.lld.exceptions.VehicleNotFoundException;
import navi.vehicle.rental.lld.model.Branch;
import navi.vehicle.rental.lld.model.Vehicle;
import navi.vehicle.rental.lld.platform.StorageAdapter;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryStorageAdapterImpl implements StorageAdapter {
    Map<String, Branch> branchTable;
    Map<String, Vehicle> vehicleTable;
    Map<String, List<String>> branchToVehicleTable;
    Map<String, List<Pair<Integer, Integer>>> vehicleBookingTable;

    public InMemoryStorageAdapterImpl() {
        branchTable = new HashMap<>();
        vehicleTable = new HashMap<>();
        branchToVehicleTable = new HashMap<>();
        vehicleBookingTable = new HashMap<>();
    }

    @Override
    public Branch getBranch(String branchName) {
        return branchTable.get(branchName);
    }

    @Override
    public Vehicle getVehicle(String vehicleId) {
        return vehicleTable.get(vehicleId);
    }

    @Override
    public void addBranch(Branch branch) {
        if (branchTable.containsKey(branch.getName())) {
            throw new IllegalArgumentException("Branch already exists");
        }
        branchTable.put(branch.getName(), branch);
    }

    @Override
    public void addVehicle(String branchName, Vehicle vehicle) {
        if (vehicleTable.containsKey(vehicle.getVehicleId())) {
            throw new IllegalArgumentException("Vehicle already exists");
        }
        if (!branchTable.get(branchName).getVehicleTypes().contains(vehicle.getVehicleType())) {
            throw new IllegalArgumentException("Vehicle type not supported by branch");
        }
        vehicleTable.put(vehicle.getVehicleId(), vehicle);
    }

    @Override
    public void deleteBranch(Branch branch) {
        // TODO: Implement this method
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        // TODO: Implement this method
    }

    @Override
    public void addBranchVehicleMapping(String branchName, String vehicleId) {
        if (branchToVehicleTable.containsKey(branchName)) {
            branchToVehicleTable.get(branchName).add(vehicleId);
        } else {
            branchToVehicleTable.put(branchName, new ArrayList<>(Arrays.asList(vehicleId)));
        }
    }

    @Override
    public List<String> getBranchVehicleMapping(String branchName) {
        // TODO: throw BranchNotFoundException if branchName is not found
        return branchToVehicleTable.get(branchName);
    }

    @Override
    public void addVehicleBooking(String vehicleId, Integer startingHour, Integer endingHour) throws VehicleNotFoundException {
        if (!vehicleTable.containsKey(vehicleId)) throw new VehicleNotFoundException("Vehicle not found");
        if (vehicleBookingTable.containsKey(vehicleId)) {
            vehicleBookingTable.get(vehicleId).add(new Pair<>(startingHour, endingHour));
        } else {
            vehicleBookingTable.put(vehicleId, new ArrayList<>(List.of(new Pair<>(startingHour, endingHour))));
        }
    }

    @Override
    public List<Pair<Integer, Integer>> getVehicleBooking(String vehicleId) {
        return vehicleBookingTable.get(vehicleId);
    }

    @Override
    public List<Pair<Vehicle, List<Pair<Integer, Integer>>>> getVehicleTypeBookingForBranch(String branchName, VehicleType vehicleType) {
        // get list of vehicle ids for a particular branch having Vehicle type
        List<Vehicle> vehicleList = branchToVehicleTable.get(branchName)
                .stream().map(vehicleTable::get)
                .filter(vehicle -> vehicle.getVehicleType() == vehicleType)
                .collect(Collectors.toList());
        return vehicleList.stream().map(vehicle -> new Pair<>(vehicle, vehicleBookingTable.get(vehicle.getVehicleId()))).collect(Collectors.toList());
    }
}
