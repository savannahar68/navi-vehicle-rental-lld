package navi.vehicle.rental.lld.Factory;

import navi.vehicle.rental.lld.platform.PricingStrategy;
import navi.vehicle.rental.lld.strategy.SimplePricingStrategy;

public class PricingStrategyFactory {
    public static PricingStrategy getPricingStrategy() {
        // Ideally this will be config driven
        // Read from config and return the appropriate strategy
        return new SimplePricingStrategy();
    }
}
