package com.masternaut;

public class DomainHelper {
    public static <T extends CustomerIdentifiedV2> String getCustomerIdFromList(Iterable<T> tlist) {
        if (!tlist.iterator().hasNext()) {
            throw new PaddingtonException("No items in list.");
        }

        String customerId = null;
        for (T t : tlist) {
            if (t.getCustomerId() == null) {
                throw new PaddingtonException("List contains object null customerId");
            }

            if (customerId == null) {
                customerId = t.getCustomerId();
            }

            if (!customerId.equals(t.getCustomerId())) {
                 throw new PaddingtonException("List contains object with different customerIds");
            }
        }

        return customerId;
    }
}
