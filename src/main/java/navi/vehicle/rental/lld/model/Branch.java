package navi.vehicle.rental.lld.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import navi.vehicle.rental.lld.enums.VehicleType;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Branch {
    private String name;
    private Set<VehicleType> vehicleTypes;

    public boolean addVehicleType(VehicleType vehicleType) {
        return vehicleTypes.add(vehicleType);
    }

    public boolean removeVehicleType(VehicleType vehicleType) {
        return vehicleTypes.remove(vehicleType);
    }
}
