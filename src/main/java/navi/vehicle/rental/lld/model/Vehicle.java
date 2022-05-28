package navi.vehicle.rental.lld.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import navi.vehicle.rental.lld.enums.VehicleStatus;
import navi.vehicle.rental.lld.enums.VehicleType;

@Getter
@AllArgsConstructor
public class Vehicle {
    private String vehicleId;
    private VehicleType vehicleType;
    @Setter
    private VehicleStatus status;
    private Double price;

    // Vehicle can have other properties like type of vehicle, model etc...
    // limiting to above properties for this task


    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleType=" + vehicleType +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}
