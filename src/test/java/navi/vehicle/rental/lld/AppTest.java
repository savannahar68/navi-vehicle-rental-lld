/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package navi.vehicle.rental.lld;

import navi.vehicle.rental.lld.factory.PricingStrategyFactory;
import navi.vehicle.rental.lld.factory.StorageAdapterFactory;
import navi.vehicle.rental.lld.api.RentalService;
import navi.vehicle.rental.lld.enums.VehicleType;
import navi.vehicle.rental.lld.platform.PricingStrategy;
import navi.vehicle.rental.lld.platform.StorageAdapter;
import navi.vehicle.rental.lld.service.RentalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AppTest {
    RentalService rentalService;

    @BeforeEach
    void setUp() {
        StorageAdapter storageAdapter = StorageAdapterFactory.getStorageAdapter();
        PricingStrategy pricingStrategy = PricingStrategyFactory.getPricingStrategy();
        rentalService = new RentalServiceImpl(storageAdapter, pricingStrategy);
    }

    @Test
    void testRentalService() {
        Assertions.assertTrue(rentalService.addBranch("B1", List.of(VehicleType.CAR, VehicleType.BIKE, VehicleType.VAN)));
        Assertions.assertTrue(rentalService.addVehicle("B1", VehicleType.CAR, "V1", 500.0));
        Assertions.assertTrue(rentalService.addVehicle("B1", VehicleType.CAR, "V2", 1000.0));
        Assertions.assertTrue(rentalService.addVehicle("B1", VehicleType.BIKE, "V3", 250.0));
        Assertions.assertTrue(rentalService.addVehicle("B1", VehicleType.BIKE, "V4", 300.0));
        Assertions.assertFalse(rentalService.addVehicle("B1", VehicleType.BUS, "V5", 2500.0));
        Assertions.assertEquals(-1.0, rentalService.bookVehicle("B1", VehicleType.VAN, 1, 5));
        Assertions.assertEquals(500.0, rentalService.bookVehicle("B1", VehicleType.CAR, 1, 3));
        Assertions.assertEquals(250.0, rentalService.bookVehicle("B1", VehicleType.BIKE, 2, 3));
        Assertions.assertEquals(300.0, rentalService.bookVehicle("B1", VehicleType.BIKE, 2, 5));
        Assertions.assertEquals(List.of("V2"), rentalService.displayVehicles("B1", 1, 5));
    }

    // TODO: Add more tests
}
