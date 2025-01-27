package uk.co.sainsburys.interview.client.model;


public record UnifiedProduct(int productUid,
                      String productType,
                      String name,
                      String fullUrl,
                             double unitPrice,
                             String unitPriceMeasure,
                             int unitPriceMeasureAmount) {
}

