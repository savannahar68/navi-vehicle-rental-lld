package navi.vehicle.rental.lld.factory;

import navi.vehicle.rental.lld.platform.PricingStrategy;
import navi.vehicle.rental.lld.strategy.SimplePricingStrategy;

public class PricingStrategyFactory {
    public static PricingStrategy getPricingStrategy() {
        // Ideally this will be config driven
        // Read from config and return the appropriate strategy

        // Change this to new DynamicPricingStrategy() if you want to use dynamic pricing.
        return new SimplePricingStrategy();
    }
}
