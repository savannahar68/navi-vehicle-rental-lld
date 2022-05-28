package navi.vehicle.rental.lld.factory;

import navi.vehicle.rental.lld.adapter.InMemoryStorageAdapterImpl;
import navi.vehicle.rental.lld.platform.StorageAdapter;

public class StorageAdapterFactory {
    public static StorageAdapter getStorageAdapter() {
        // Ideally this will be config driven
        // Read from config and return the appropriate adapter
        return new InMemoryStorageAdapterImpl();
    }
}
